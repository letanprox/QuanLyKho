package com.example.qlkhov2.QuanLyNhaCungCap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlkhov2.R;

import java.util.List;

public class NhaCungCapAdapter extends BaseAdapter {


    List<NhaSanXuat> nhaSanXuats;
    Context context;
    IClickDeleteNSX iClickDeleteNSX;
    IClickItemNSX iClickItemNSX;

    public NhaCungCapAdapter(List<NhaSanXuat> nhaSanXuats, Context context, IClickDeleteNSX iClickDeleteNSX, IClickItemNSX iClickItemNSX) {
        this.nhaSanXuats = nhaSanXuats;
        this.context = context;
        this.iClickDeleteNSX = iClickDeleteNSX;
        this.iClickItemNSX = iClickItemNSX;
    }

    @Override
    public int getCount() {
        return nhaSanXuats.size();
    }

    @Override
    public Object getItem(int i) {
        return nhaSanXuats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return nhaSanXuats.get(i).getMa();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.item_nsx, null);
        } else viewProduct = view;


        LinearLayout item_nsx;
        Button btn_delete_nsx;
        TextView tv_tennsx_nsx, tv_mansx_nsx, tv_diachi_nsx;

        item_nsx = viewProduct.findViewById(R.id.item_nsx);
        btn_delete_nsx = viewProduct.findViewById(R.id.btn_delete_nsx);
        tv_tennsx_nsx = viewProduct.findViewById(R.id.tv_tennsx_nsx);
        tv_mansx_nsx = viewProduct.findViewById(R.id.tv_mansx_nsx);
        tv_diachi_nsx = viewProduct.findViewById(R.id.tv_diachi_nsx);

        NhaSanXuat nhaSanXuat = nhaSanXuats.get(i);


        tv_tennsx_nsx.setText("Tên: "+nhaSanXuat.getTen());
        tv_mansx_nsx.setText("Mã: "+nhaSanXuat.getMa());
        tv_diachi_nsx.setText("Địa chỉ: "+nhaSanXuat.getDiaChi());

        btn_delete_nsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteNSX.clickItem(nhaSanXuat,  i);
            }
        });

        item_nsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemNSX.clickItem(nhaSanXuat, i);
            }
        });

        return viewProduct;
    }
}
