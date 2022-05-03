package com.example.qlkhov2.QuanLyPhieu.SanPhamList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DialogSanPhamAdapter extends RecyclerView.Adapter<DialogSanPhamAdapter.DialogSanPhamViewHolder>{

    private List<DialogItemSanPham> dialogItemSanPhamList;
    private IClickItemSanPhamDialog iClickItemSanPhamDialog;
    private int row_index = -1;

    public DialogSanPhamAdapter(List<DialogItemSanPham> dialogItemSanPhamList,IClickItemSanPhamDialog iClickItemSanPhamDialog) {
        this.dialogItemSanPhamList = dialogItemSanPhamList;
        this.iClickItemSanPhamDialog = iClickItemSanPhamDialog;
    }

    @NonNull
    @Override
    public DialogSanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_sanpham, parent,false);
        return new DialogSanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogSanPhamViewHolder holder, int position) {
        DialogItemSanPham dialogItemSanPham = dialogItemSanPhamList.get(position);
        if(dialogItemSanPham == null){
            return;
        }
        holder.tv_item_dialog_sanpham.setText(dialogItemSanPham.getName());

        byte[] hinhAnh = dialogItemSanPham.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
        holder.imgview_item_dialog_sanpham.setImageBitmap(bitmap);

//        Picasso.get().load(dialogItemSanPham.getUrlImage())
//                .into(holder.imgview_item_dialog_sanpham, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        holder.imgview_item_dialog_sanpham.setScaleType(ImageView.ScaleType.CENTER_CROP);//Or ScaleType.FIT_CENTER
//                    }
//                    @Override
//                    public void onError(Exception e) {
//                    }
//                });



        holder.item_dialog_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index=position;
                notifyDataSetChanged();

                iClickItemSanPhamDialog.clickItem(dialogItemSanPham);

            }
        });

        if(row_index==position){
            holder.item_dialog_sanpham.setBackgroundColor(Color.CYAN);
        }
        else
        {
            holder.item_dialog_sanpham.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        if(dialogItemSanPhamList != null){
            return dialogItemSanPhamList.size();
        }
        return 0;
    }

    public class DialogSanPhamViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_item_dialog_sanpham;
        private ImageView imgview_item_dialog_sanpham;
        private LinearLayout item_dialog_sanpham;
        public DialogSanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            item_dialog_sanpham = itemView.findViewById(R.id.item_dialog_sanpham);
            tv_item_dialog_sanpham = itemView.findViewById(R.id.tv_item_dialog_sanpham);
            imgview_item_dialog_sanpham = itemView.findViewById(R.id.imgview_item_dialog_sanpham);


        }
    }

}
