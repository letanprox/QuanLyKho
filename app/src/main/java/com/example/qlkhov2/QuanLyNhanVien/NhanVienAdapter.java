package com.example.qlkhov2.QuanLyNhanVien;

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

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder>{
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

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhanvien,parent,false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = nhanVienList.get(position);
        if(nhanVien == null){
            return;
        }

        holder.tv_tennhanvien_nhanvien.setText("Tên: "+nhanVien.getTen());
        holder.tv_manhanvien_nhanvien.setText("Mã: "+Integer.toString(nhanVien.getMa()));
        holder.tv_chucvu_nhanvien.setText("SDT: "+nhanVien.getSDT());

        holder.btn_delete_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteNhanVien.clickItem(nhanVien);
            }
        });

        holder.item_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemNhanVien.clickItem(nhanVien, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(nhanVienList != null){
            return nhanVienList.size();
        }
        return 0;
    }


    class NhanVienViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item_nhanvien;
        private Button btn_delete_nhanvien;
        private TextView tv_tennhanvien_nhanvien, tv_manhanvien_nhanvien, tv_chucvu_nhanvien;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);

            item_nhanvien = itemView.findViewById(R.id.item_nhanvien);
            btn_delete_nhanvien = itemView.findViewById(R.id.btn_delete_nhanvien);
            tv_tennhanvien_nhanvien = itemView.findViewById(R.id.tv_tennhanvien_nhanvien);
            tv_manhanvien_nhanvien = itemView.findViewById(R.id.tv_manhanvien_nhanvien);
            tv_chucvu_nhanvien = itemView.findViewById(R.id.tv_chucvu_nhanvien);

        }
    }
}
