package com.example.qlkhov2.QuanLySanPham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlkhov2.MainActivity;
import com.example.qlkhov2.QuanLyKho.DsKho;
import com.example.qlkhov2.QuanLyKho.NhaKhoActivity;
import com.example.qlkhov2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SanPhamActivity extends AppCompatActivity {

    private RecyclerView rcv_sanpham;
    private List<SanPham> sanPhamList;
    private SanPhamAdapter sanPhamAdapter;

    private Button btn_them_sanpham, upload_anh_btn, label_btn_themsp;
    private TextView btn_close_themsp;

    private LinearLayout layout_them_sanpham_bottom;
    private BottomSheetBehavior bottomSheetBehavior;

    int SELECT_PICTURE = 200;
    ImageView previewimage_themsp;
    private EditText input_ten_themsp,input_donvi_themsp,input_giatien_themsp;

    int MaSanPham = -1;
    int posItemSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        setData();

        setTagElement();

        setListSp();

        setAddSanpham();

        label_btn_themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MaSanPham == -1){
                    INSERT_SANPHAM();

                }else {
                    UPDATE_SANPHAM(MaSanPham, posItemSanPham);
                    MaSanPham = -1;
                }

            }
        });
    }


    void setData(){
        sanPhamList = new ArrayList<>();

        //GET DATA:
        Cursor cursor = MainActivity.database.GetData("SELECT * FROM SanPham");
        while (cursor.moveToNext()){
            sanPhamList.add(new SanPham(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getBlob(5)
            ));
        }

//        sanPhamList.add(new SanPham("5245421","Thép Hòa Phát", "Cây thép", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png", 12,"12121"));
//        sanPhamList.add(new SanPham("5245421","Thép Hòa Phát", "Cây thép", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png", 12,"12121"));
//        sanPhamList.add(new SanPham("5245421","Thép Hòa Phát", "Cây thép", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png", 12,"12121"));
//        sanPhamList.add(new SanPham("5245421","Thép Hòa Phát", "Cây thép", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png", 12,"12121"));
//        sanPhamList.add(new SanPham("5245421","Thép Hòa Phát", "Cây thép", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png", 12,"12121"));
//        sanPhamList.add(new SanPham("5245421","Thép Hòa Phát", "Cây thép", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png", 12,"12121"));
    }


    void setTagElement(){
        //MAIN ELEMENT:
        rcv_sanpham = findViewById(R.id.rcv_quanly_sanpham);
        btn_them_sanpham = findViewById(R.id.btn_them_sanpham);
        btn_close_themsp = findViewById(R.id.btn_close_themsp);
        layout_them_sanpham_bottom = findViewById(R.id.layout_them_sanpham_bottom);


        //ADD SP ELEMENT:
        input_ten_themsp = findViewById(R.id.input_ten_themsp);
        input_donvi_themsp = findViewById(R.id.input_donvi_themsp);
        input_giatien_themsp = findViewById(R.id.input_giatien_themsp);
        upload_anh_btn = findViewById(R.id.upload_anh_btn);
        previewimage_themsp = findViewById(R.id.previewimage_themsp);
        previewimage_themsp.setImageResource(R.drawable.baseline_photo_black_48dp);
        label_btn_themsp = findViewById(R.id.label_btn_themsp);
    }



    void setListSp(){
        sanPhamAdapter = new SanPhamAdapter(sanPhamList, this, new IClickItemSanPham() {
            @Override
            public void clickItem(SanPham sanPham, int pos) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                input_ten_themsp.setText(sanPham.getTen());
                input_donvi_themsp.setText(sanPham.getDonvi());
                input_giatien_themsp.setText(sanPham.getGiatien());
                byte[] hinhAnh = sanPham.getHinhAnh();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
                previewimage_themsp.setImageBitmap(bitmap);
                previewimage_themsp.setScaleType(ImageView.ScaleType.CENTER_CROP);

                label_btn_themsp.setText("SỬA SẢN PHẨM");
                MaSanPham = sanPham.getMa();
                posItemSanPham = pos;

            }
        }, new IClickDeleteSanPham() {
            @Override
            public void clickItem(SanPham sanPham, int pos) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SanPhamActivity.this);
                dialog.setTitle( "Bạn có muốn xóa sản phẩm: " +  sanPham.getTen())
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }}).setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {


                        DELETE_SANPHAM(sanPham,pos);


                    }
                }).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_sanpham.setLayoutManager(linearLayoutManager);
        rcv_sanpham.setAdapter(sanPhamAdapter);
    }


    void setAddSanpham(){
        bottomSheetBehavior = BottomSheetBehavior.from(layout_them_sanpham_bottom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn_them_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    input_ten_themsp.setText("");
                    input_donvi_themsp.setText("");
                    input_giatien_themsp.setText("");
                    previewimage_themsp.setImageResource(R.drawable.baseline_photo_black_48dp);

                    label_btn_themsp.setText("THÊM SẢN PHẨM");
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        btn_close_themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        upload_anh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    previewimage_themsp.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void INSERT_SANPHAM(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) previewimage_themsp.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] hinhanh = byteArrayOutputStream.toByteArray();
        Toast.makeText(SanPhamActivity.this, "Da them", Toast.LENGTH_SHORT).show();
        long result = MainActivity.database.INSERT_SANPHAM(
                input_ten_themsp.getText().toString(),
                input_donvi_themsp.getText().toString(),
                0,
                input_giatien_themsp.getText().toString(),
                hinhanh
        );
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sanPhamList.add(new SanPham(
                (int) result,
                input_ten_themsp.getText().toString(),
                input_donvi_themsp.getText().toString(),
                0,
                input_giatien_themsp.getText().toString(),
                hinhanh
        ));
        sanPhamAdapter.notifyDataSetChanged();
    }


    void UPDATE_SANPHAM(int MASANPHAM, int POS){

        BitmapDrawable bitmapDrawable = (BitmapDrawable) previewimage_themsp.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] hinhanh = byteArrayOutputStream.toByteArray();

        int result = MainActivity.database.UPDATE_SANPHAM(
                MASANPHAM,
                input_ten_themsp.getText().toString(),
                input_donvi_themsp.getText().toString(),
                0,
                input_giatien_themsp.getText().toString(),
                hinhanh
        );


        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        sanPhamList.get(POS).setTen(input_ten_themsp.getText().toString());
        sanPhamList.get(POS).setDonvi(input_donvi_themsp.getText().toString());
        sanPhamList.get(POS).setGiatien(input_giatien_themsp.getText().toString());
        sanPhamList.get(POS).setHinhAnh(hinhanh);
        sanPhamAdapter.notifyDataSetChanged();
    }


    void DELETE_SANPHAM(SanPham sanPham, int POS){
        sanPhamList.remove(POS);
        MainActivity.database.QueryData("DELETE FROM SanPham WHERE MaSanPham ='"+sanPham.getMa()+"'");
        sanPhamAdapter.notifyDataSetChanged();
    }

}