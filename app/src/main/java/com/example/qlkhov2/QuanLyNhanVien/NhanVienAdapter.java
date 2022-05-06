package com.example.qlkhov2.QuanLyNhanVien;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qlkhov2.R;

import java.util.List;

public class NhanVienAdapter extends BaseAdapter {

    List<NhanVien> nhanVienList;
    Context context;
    IClickDeleteNhanVien iClickDeleteNhanVien;
    IClickItemNhanVien iClickItemNhanVien;

    public NhanVienAdapter(List<NhanVien> nhanVienList, Context context, IClickDeleteNhanVien iClickDeleteNhanVien, IClickItemNhanVien iClickItemNhanVien) {
        this.nhanVienList = nhanVienList;
        this.context = context;
        this.iClickDeleteNhanVien = iClickDeleteNhanVien;
        this.iClickItemNhanVien = iClickItemNhanVien;
    }

    @Override
    public int getCount() {
        return nhanVienList.size();
    }

    @Override
    public Object getItem(int i) {
        return nhanVienList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return nhanVienList.get(i).getMa();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.item_nhanvien, null);
        } else viewProduct = view;

         LinearLayout item_nhanvien;
         Button btn_delete_nhanvien;
         TextView tv_tennhanvien_nhanvien, tv_manhanvien_nhanvien, tv_chucvu_nhanvien;


            item_nhanvien = viewProduct.findViewById(R.id.item_nhanvien);
            btn_delete_nhanvien = viewProduct.findViewById(R.id.btn_delete_nhanvien);
            tv_tennhanvien_nhanvien = viewProduct.findViewById(R.id.tv_tennhanvien_nhanvien);
            tv_manhanvien_nhanvien = viewProduct.findViewById(R.id.tv_manhanvien_nhanvien);
            tv_chucvu_nhanvien = viewProduct.findViewById(R.id.tv_chucvu_nhanvien);


        NhanVien nhanVien = nhanVienList.get(i);

        tv_tennhanvien_nhanvien.setText("Tên: "+nhanVien.getTen());
        tv_manhanvien_nhanvien.setText("Mã: "+Integer.toString(nhanVien.getMa()));
        tv_chucvu_nhanvien.setText("SDT: "+nhanVien.getSDT());

        btn_delete_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteNhanVien.clickItem(nhanVien);
            }
        });

        item_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemNhanVien.clickItem(nhanVien, i);
            }
        });

        return viewProduct;
    }
}
