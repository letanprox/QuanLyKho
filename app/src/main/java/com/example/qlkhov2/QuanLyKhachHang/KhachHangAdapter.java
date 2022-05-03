package com.example.qlkhov2.QuanLyKhachHang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.R;

import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder>{

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

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachhang,parent,false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang khachHang = khachHangList.get(position);
        if(khachHang == null){
            return;
        }

        holder.tv_tenkhachhang_khachhang.setText("Tên: "+khachHang.getTen());
        holder.tv_makhachhang_khachhang.setText("Mã: "+khachHang.getMa());
        holder.tv_diachi_khachhang.setText("Địa chỉ: "+khachHang.getDiachi());

        holder.btn_delete_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteKhachHang.clickItem(khachHang, position);
            }
        });

        holder.item_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemKhachHang.clickItem(khachHang, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(khachHangList != null){
            return khachHangList.size();
        }
        return 0;
    }


    class KhachHangViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item_khachhang;
        private Button btn_delete_khachhang;
        private TextView tv_tenkhachhang_khachhang, tv_makhachhang_khachhang, tv_diachi_khachhang;
        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            item_khachhang = itemView.findViewById(R.id.item_khachhang);
            btn_delete_khachhang = itemView.findViewById(R.id.btn_delete_khachhang);
            tv_tenkhachhang_khachhang = itemView.findViewById(R.id.tv_tenkhachhang_khachhang);
            tv_makhachhang_khachhang = itemView.findViewById(R.id.tv_makhachhang_khachhang);
            tv_diachi_khachhang = itemView.findViewById(R.id.tv_diachi_khachhang);
        }
    }
}
