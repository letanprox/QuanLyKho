package com.example.qlkhov2.QuanLyKhachHang;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qlkhov2.R;

import java.util.List;

public class KhachHangAdapter extends BaseAdapter {

    List<KhachHang> khachHangList;
    Context context;
    IClickDeleteKhachHang iClickDeleteKhachHang;
    IClickItemKhachHang iClickItemKhachHang;

    public KhachHangAdapter(List<KhachHang> khachHangList, Context context, IClickDeleteKhachHang iClickDeleteKhachHang, IClickItemKhachHang iClickItemKhachHang) {
        this.khachHangList = khachHangList;
        this.context = context;
        this.iClickDeleteKhachHang = iClickDeleteKhachHang;
        this.iClickItemKhachHang = iClickItemKhachHang;
    }

    @Override
    public int getCount() {
        return khachHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return khachHangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return khachHangList.get(i).getMa();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.item_khachhang, null);
        } else viewProduct = view;


         LinearLayout item_khachhang;
         Button btn_delete_khachhang;
         TextView tv_tenkhachhang_khachhang, tv_makhachhang_khachhang, tv_diachi_khachhang;

        item_khachhang = viewProduct.findViewById(R.id.item_khachhang);
        btn_delete_khachhang = viewProduct.findViewById(R.id.btn_delete_khachhang);
        tv_tenkhachhang_khachhang = viewProduct.findViewById(R.id.tv_tenkhachhang_khachhang);
        tv_makhachhang_khachhang = viewProduct.findViewById(R.id.tv_makhachhang_khachhang);
        tv_diachi_khachhang = viewProduct.findViewById(R.id.tv_diachi_khachhang);

        KhachHang khachHang = khachHangList.get(i);

        tv_tenkhachhang_khachhang.setText("Tên: "+khachHang.getTen());
        tv_makhachhang_khachhang.setText("Mã: "+khachHang.getMa());
        tv_diachi_khachhang.setText("Địa chỉ: "+khachHang.getDiachi());

        btn_delete_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteKhachHang.clickItem(khachHang, i);
            }
        });

        item_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemKhachHang.clickItem(khachHang, i);
            }
        });



        return  viewProduct;
    }
}
