package com.example.qlkhov2.QuanLySanPham;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder>{

    List<SanPham> sanPhamList;
    Context context;

    IClickItemSanPham iClickItemSanPham;
    IClickDeleteSanPham iClickDeleteSanPham;
    int chose_button = 1;

    public SanPhamAdapter(List<SanPham> sanPhamList, Context context, IClickItemSanPham iClickItemSanPham, IClickDeleteSanPham iClickDeleteSanPham) {
        this.sanPhamList = sanPhamList;
        this.context = context;
        this.iClickItemSanPham = iClickItemSanPham;
        this.iClickDeleteSanPham = iClickDeleteSanPham;
    }

    public SanPhamAdapter(List<SanPham> sanPhamList, Context context, IClickItemSanPham iClickItemSanPham, IClickDeleteSanPham iClickDeleteSanPham, int chose_button) {
        this.sanPhamList = sanPhamList;
        this.context = context;
        this.iClickItemSanPham = iClickItemSanPham;
        this.iClickDeleteSanPham = iClickDeleteSanPham;
        this.chose_button = chose_button;
    }

    public SanPhamAdapter(List<SanPham> sanPhamList, Context mcontext) {
        this.sanPhamList = sanPhamList;
        this.context = mcontext;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        if(sanPham == null){
            return;
        }

        if(chose_button == 2){
            holder.btn_delete_sanpham.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.baseline_close_black_24dp, 0);
        }

        holder.title_sanpham.setText(sanPham.getTen());
        holder.tv_ma_sanpham.setText("Mã: " + sanPham.getMa());
        if(chose_button == 2) {
            holder.tv_soluong_sanpham.setText("Giá: " + sanPham.getGiatien() + " - Số lượng: " + Integer.toString(sanPham.getSoluong()));

            byte[] hinhAnh = sanPham.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            holder.imgview_sanpham.setImageBitmap(bitmap);

        }else {
            holder.tv_soluong_sanpham.setText("Giá: " + sanPham.getGiatien());
            byte[] hinhAnh = sanPham.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            holder.imgview_sanpham.setImageBitmap(bitmap);

        }



        holder.item_sanpham.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 iClickItemSanPham.clickItem(sanPham, position);
             }
        });

        holder.btn_delete_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDeleteSanPham.clickItem(sanPham, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(sanPhamList != null){
            return sanPhamList.size();
        }
        return 0;
    }

    class SanPhamViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item_sanpham;
        private ImageView imgview_sanpham;
        private Button btn_delete_sanpham;
        private TextView title_sanpham, tv_soluong_sanpham, tv_ma_sanpham;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview_sanpham = itemView.findViewById(R.id.imgview_sanpham);
            btn_delete_sanpham = itemView.findViewById(R.id.btn_delete_sanpham);
            title_sanpham = itemView.findViewById(R.id.title_sanpham);
            tv_soluong_sanpham = itemView.findViewById(R.id.tv_soluong_sanpham);
            tv_ma_sanpham = itemView.findViewById(R.id.tv_ma_sanpham);
            item_sanpham = itemView.findViewById(R.id.item_sanpham);
        }
    }
}
