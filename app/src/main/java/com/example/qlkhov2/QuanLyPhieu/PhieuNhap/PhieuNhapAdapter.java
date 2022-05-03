package com.example.qlkhov2.QuanLyPhieu.PhieuNhap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.R;

import java.util.List;

public class PhieuNhapAdapter extends RecyclerView.Adapter<PhieuNhapAdapter.PhieuNhapViewHolder>{

    List<PhieuNhap> phieuNhapList;
    Context context;
    IClickItemPhieuNhap iClickItemPhieuNhap;
    boolean chose_adp = true;

    public PhieuNhapAdapter(List<PhieuNhap> phieuNhapList, Context context, IClickItemPhieuNhap iClickItemPhieuNhap, boolean chose_adp) {
        this.phieuNhapList = phieuNhapList;
        this.context = context;
        this.iClickItemPhieuNhap = iClickItemPhieuNhap;
        this.chose_adp = chose_adp;
    }

    public PhieuNhapAdapter(List<PhieuNhap> phieuNhapList, Context context, IClickItemPhieuNhap iClickItemPhieuNhap) {
        this.phieuNhapList = phieuNhapList;
        this.context = context;
        this.iClickItemPhieuNhap = iClickItemPhieuNhap;
    }

    @NonNull
    @Override
    public PhieuNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label,parent,false);
        return new PhieuNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuNhapViewHolder holder, int position) {
        PhieuNhap phieuNhap = phieuNhapList.get(position);
        if(phieuNhap == null){
            return;
        }

        if(chose_adp == false){
            holder.text_sample_1.setText("Tên sản phẩm: "+phieuNhap.getNgayNhapKho());
            holder.text_sample_2.setText("Đơn giá: "+phieuNhap.getTenKho());
            holder.text_sample_3.setText("Số lượng: "+phieuNhap.getTenNhanVien());
            holder.text_sample_4.setText("Thành tiền: "+phieuNhap.getTenNhaCungCap());
            holder.text_sample_5.setVisibility(View.GONE);
            holder.text_sample_6.setVisibility(View.GONE);
            holder.text_sample_7.setVisibility(View.GONE);
        }else{
            holder.text_sample_1.setText("Mã phiếu: "+phieuNhap.getMaPhieu());
            holder.text_sample_2.setText("Ngày nhập kho: "+phieuNhap.getNgayNhapKho());
            holder.text_sample_3.setText("Tên kho: "+phieuNhap.getTenKho());
            holder.text_sample_4.setText("Nhân viên: "+phieuNhap.getTenNhanVien());
            holder.text_sample_5.setText("Tên nhà cung cấp: "+phieuNhap.getTenNhaCungCap());
            holder.text_sample_6.setText("Số sản phẩm: "+phieuNhap.getSoSanPham());
            holder.text_sample_7.setText("Tổng tiền: "+phieuNhap.getTongTien());
        }

        holder.contain_text_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemPhieuNhap.clickItem(phieuNhap);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(phieuNhapList != null){
            return phieuNhapList.size();
        }
        return 0;
    }

    class PhieuNhapViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout contain_text_sample;
        private TextView text_sample_1, text_sample_2, text_sample_3,text_sample_4,text_sample_5,text_sample_6,text_sample_7;
        public PhieuNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            contain_text_sample = itemView.findViewById(R.id.contain_text_sample);
            text_sample_1 = itemView.findViewById(R.id.text_sample_1);
            text_sample_2 = itemView.findViewById(R.id.text_sample_2);
            text_sample_3 = itemView.findViewById(R.id.text_sample_3);
            text_sample_4 = itemView.findViewById(R.id.text_sample_4);
            text_sample_5 = itemView.findViewById(R.id.text_sample_5);
            text_sample_6 = itemView.findViewById(R.id.text_sample_6);
            text_sample_7 = itemView.findViewById(R.id.text_sample_7);

        }
    }
}
