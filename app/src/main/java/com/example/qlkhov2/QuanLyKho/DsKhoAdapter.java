package com.example.qlkhov2.QuanLyKho;

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

public class DsKhoAdapter extends RecyclerView.Adapter<DsKhoAdapter.DsKhoViewHolder>{

    public List<DsKho> dsKhoList;
    public IClickItemDsKho iClickItemDsKho;
    public IClickDeleteDsKho iClickDeleteDsKho;

    public DsKhoAdapter(List<DsKho> dsKhoList) {
        this.dsKhoList = dsKhoList;
    }

    public DsKhoAdapter(List<DsKho> dsKhoList, IClickItemDsKho iClickItemDsKho) {
        this.dsKhoList = dsKhoList;
        this.iClickItemDsKho = iClickItemDsKho;
    }

    public DsKhoAdapter(List<DsKho> dsKhoList, IClickItemDsKho iClickItemDsKho, IClickDeleteDsKho iClickDeleteDsKho) {
        this.dsKhoList = dsKhoList;
        this.iClickItemDsKho = iClickItemDsKho;
        this.iClickDeleteDsKho = iClickDeleteDsKho;
    }

    @NonNull
    @Override
    public DsKhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ds_kho,parent,false);
        return new DsKhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DsKhoViewHolder holder, int position) {
        DsKho dsKho = dsKhoList.get(position);
        if(dsKho == null){
            return;
        }
        holder.tv_tenkho_dskho.setText("Tên kho: "+dsKho.getTenKho());
        holder.tv_makho_dskho.setText("Mã kho: "+dsKho.getMaKho());
        holder.tv_diachi_dskho.setText("Địa chỉ: "+dsKho.getDiaChi());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemDsKho.clickItem(dsKho, position);
            }
        });

        holder.btn_delete_dskho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteDsKho.clickItem(dsKho, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dsKhoList != null){
            return dsKhoList.size();
        }
        return 0;
    }

    class DsKhoViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tenkho_dskho, tv_makho_dskho, tv_diachi_dskho;
        LinearLayout linearLayout;
        Button btn_delete_dskho;
        public DsKhoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenkho_dskho = itemView.findViewById(R.id.tv_tenkho_dskho);
            tv_makho_dskho = itemView.findViewById(R.id.tv_makho_dskho);
            tv_diachi_dskho = itemView.findViewById(R.id.tv_diachi_dskho);

            linearLayout = itemView.findViewById(R.id.item_dskho);
            btn_delete_dskho = itemView.findViewById(R.id.btn_delete_dskho);
        }
    }
}
