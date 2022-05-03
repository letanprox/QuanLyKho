package com.example.qlkhov2.QuanLyNhaCungCap;

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

public class NhaSanXuatAdapter extends RecyclerView.Adapter<NhaSanXuatAdapter.NhaSanXuatViewHolder>{

    List<NhaSanXuat> nhaSanXuats;
    Context context;
    IClickDeleteNSX iClickDeleteNSX;
    IClickItemNSX iClickItemNSX;


    public NhaSanXuatAdapter(List<NhaSanXuat> nhaSanXuats, Context context, IClickDeleteNSX iClickDeleteNSX, IClickItemNSX iClickItemNSX) {
        this.nhaSanXuats = nhaSanXuats;
        this.context = context;
        this.iClickDeleteNSX = iClickDeleteNSX;
        this.iClickItemNSX = iClickItemNSX;
    }

    @NonNull
    @Override
    public NhaSanXuatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nsx,parent,false);
        return new NhaSanXuatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhaSanXuatViewHolder holder, int position) {
        NhaSanXuat nhaSanXuat = nhaSanXuats.get(position);
        if(nhaSanXuat == null){
            return;
        }

        holder.tv_tennsx_nsx.setText("Tên: "+nhaSanXuat.getTen());
        holder.tv_mansx_nsx.setText("Mã: "+nhaSanXuat.getMa());
        holder.tv_diachi_nsx.setText("Địa chỉ: "+nhaSanXuat.getDiaChi());

        holder.btn_delete_nsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteNSX.clickItem(nhaSanXuat,  position);
            }
        });

        holder.item_nsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemNSX.clickItem(nhaSanXuat, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(nhaSanXuats != null){
            return nhaSanXuats.size();
        }
        return 0;
    }


    class NhaSanXuatViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item_nsx;
        private Button btn_delete_nsx;
        private TextView tv_tennsx_nsx, tv_mansx_nsx, tv_diachi_nsx;
        public NhaSanXuatViewHolder(@NonNull View itemView) {
            super(itemView);

            item_nsx = itemView.findViewById(R.id.item_nsx);
            btn_delete_nsx = itemView.findViewById(R.id.btn_delete_nsx);
            tv_tennsx_nsx = itemView.findViewById(R.id.tv_tennsx_nsx);
            tv_mansx_nsx = itemView.findViewById(R.id.tv_mansx_nsx);
            tv_diachi_nsx = itemView.findViewById(R.id.tv_diachi_nsx);

        }
    }



}
