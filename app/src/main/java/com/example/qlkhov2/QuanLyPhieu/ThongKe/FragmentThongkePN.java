package com.example.qlkhov2.QuanLyPhieu.ThongKe;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qlkhov2.MainActivity;
import com.example.qlkhov2.QuanLyPhieu.PhieuXuat.PhieuXuat;
import com.example.qlkhov2.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class FragmentThongkePN extends Fragment {

    LineChart lineChart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke_pn, container, false);


        lineChart = view.findViewById(R.id.line_chart_phieunhap);

        LineDataSet lineDataSet = new LineDataSet(dataValuePhieuNhap(), "Số phiếu trên nhập ngày");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();

        ArrayList<String> xAxisLabel = new ArrayList<>();
        Cursor cursor = MainActivity.database.GetData("SELECT NgayTaoPhieu, COUNT(NgayTaoPhieu) AS tong FROM PhieuNhap GROUP BY NgayTaoPhieu ");
        while (cursor.moveToNext()){
            xAxisLabel.add(cursor.getString(0));
        }
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineDataSet.setLineWidth(5);
        lineDataSet.setCircleColors(Color.BLACK);
        lineDataSet.setColor(Color.RED);

        return view;
    }




    private ArrayList<Entry> dataValuePhieuNhap(){
        ArrayList<Entry> data = new ArrayList<Entry>();
        Cursor cursor = MainActivity.database.GetData("SELECT NgayTaoPhieu, COUNT(NgayTaoPhieu) AS tong FROM PhieuNhap GROUP BY NgayTaoPhieu ");
        int i = 0;
        while (cursor.moveToNext()){
            data.add(new Entry(i,cursor.getInt(1)));
            i = i + 1;
            Log.e(String.valueOf(cursor.getString(0)), String.valueOf(cursor.getInt(1)));
        }
        return  data;
    }


}
