package com.example.qlkhov2.QuanLyKho;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qlkhov2.R;

import java.util.List;

public class NhaKhoAdapter extends BaseAdapter {

    public List<DsKho> dsKhoList;
    public IClickItemDsKho iClickItemDsKho;
    public IClickDeleteDsKho iClickDeleteDsKho;

    public NhaKhoAdapter(List<DsKho> dsKhoList, IClickItemDsKho iClickItemDsKho, IClickDeleteDsKho iClickDeleteDsKho) {
        this.dsKhoList = dsKhoList;
        this.iClickItemDsKho = iClickItemDsKho;
        this.iClickDeleteDsKho = iClickDeleteDsKho;
    }

    @Override
    public int getCount() {
        return dsKhoList.size();
    }

    @Override
    public Object getItem(int i) {
        return dsKhoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return dsKhoList.get(i).getMaKho();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.item_ds_kho, null);
        } else viewProduct = view;

        TextView tv_tenkho_dskho, tv_makho_dskho, tv_diachi_dskho;
        LinearLayout linearLayout;
        Button btn_delete_dskho;

        tv_tenkho_dskho = viewProduct.findViewById(R.id.tv_tenkho_dskho);
        tv_makho_dskho = viewProduct.findViewById(R.id.tv_makho_dskho);
        tv_diachi_dskho = viewProduct.findViewById(R.id.tv_diachi_dskho);
        linearLayout = viewProduct.findViewById(R.id.item_dskho);
        btn_delete_dskho = viewProduct.findViewById(R.id.btn_delete_dskho);

        DsKho dsKho = dsKhoList.get(i);

        tv_tenkho_dskho.setText("Tên kho: "+dsKho.getTenKho());
        tv_makho_dskho.setText("Mã kho: "+dsKho.getMaKho());
        tv_diachi_dskho.setText("Địa chỉ: "+dsKho.getDiaChi());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemDsKho.clickItem(dsKho, i);
            }
        });

        btn_delete_dskho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteDsKho.clickItem(dsKho, i);
            }
        });


        return viewProduct;
    }
}
