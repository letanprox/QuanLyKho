package com.example.qlkhov2.QuanLyPhieu.PhieuXuat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.IClickItemPhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhapAdapter;
import com.example.qlkhov2.R;

import java.util.List;

public class PhieuXuatAdapter extends RecyclerView.Adapter<PhieuXuatAdapter.PhieuXuatViewHolder>{

    List<PhieuXuat> phieuXuatList;
    Context context;
    IClickItemPhieuXuat iClickItemPhieuXuat;
    boolean chose_adp = true;

    public PhieuXuatAdapter(List<PhieuXuat> phieuXuatList, Context context, IClickItemPhieuXuat iClickItemPhieuXuat) {
        this.phieuXuatList = phieuXuatList;
        this.context = context;
        this.iClickItemPhieuXuat = iClickItemPhieuXuat;
    }

    public PhieuXuatAdapter(List<PhieuXuat> phieuXuatList, Context context, IClickItemPhieuXuat iClickItemPhieuXuat, boolean chose_adp) {
        this.phieuXuatList = phieuXuatList;
        this.context = context;
        this.iClickItemPhieuXuat = iClickItemPhieuXuat;
        this.chose_adp = chose_adp;
    }

    @NonNull
    @Override
    public PhieuXuatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label,parent,false);
        return new PhieuXuatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuXuatViewHolder holder, int position) {
        PhieuXuat phieuXuat = phieuXuatList.get(position);
        if(phieuXuat == null){
            return;
        }

        if(chose_adp == false){
            holder.text_sample_1.setText("Tên sản phẩm: "+phieuXuat.getNgayXuatKho());
            holder.text_sample_2.setText("Đơn giá: "+phieuXuat.getTenKho());
            holder.text_sample_3.setText("Số lượng: "+phieuXuat.getTenNhanVien());
            holder.text_sample_4.setText("Thành tiền: "+phieuXuat.getTenKhachHang());
            holder.text_sample_5.setVisibility(View.GONE);
            holder.text_sample_6.setVisibility(View.GONE);
            holder.text_sample_7.setVisibility(View.GONE);
        }else{
            holder.text_sample_1.setText("Mã phiếu: "+phieuXuat.getMaPhieu());
            holder.text_sample_2.setText("Ngày xuất kho: "+phieuXuat.getNgayXuatKho());
            holder.text_sample_3.setText("Tên kho: "+phieuXuat.getTenKho());
            holder.text_sample_4.setText("Nhân viên: "+phieuXuat.getTenNhanVien());
            holder.text_sample_5.setText("Tên khách hàng: "+phieuXuat.getTenKhachHang());
            holder.text_sample_6.setText("Số sản phẩm: "+phieuXuat.getSoSanPham());
            holder.text_sample_7.setText("Tổng tiền: "+phieuXuat.getTongTien());
        }

        holder.contain_text_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemPhieuXuat.clickItem(phieuXuat);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(phieuXuatList != null){
            return phieuXuatList.size();
        }
        return 0;
    }

    class PhieuXuatViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout contain_text_sample;
        private TextView text_sample_1, text_sample_2, text_sample_3,text_sample_4,text_sample_5,text_sample_6,text_sample_7;
        public PhieuXuatViewHolder(@NonNull View itemView) {
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
