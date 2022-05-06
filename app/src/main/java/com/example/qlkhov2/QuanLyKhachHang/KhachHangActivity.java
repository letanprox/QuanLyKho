package com.example.qlkhov2.QuanLyKhachHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlkhov2.MainActivity;
import com.example.qlkhov2.QuanLyNhaCungCap.NhaSanXuat;
import com.example.qlkhov2.QuanLyNhaCungCap.NhaSanXuatActivity;
import com.example.qlkhov2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class KhachHangActivity extends AppCompatActivity {

    private ListView lvdanhsachkhachhang;
    private List<KhachHang> khachHangList;
    private KhachHangAdapter khachHangAdapter;

    private Button btn_them_khachhang;
    private TextView btn_close_them_khachhang;

    private LinearLayout layout_them_khachhang_bottom;
    private BottomSheetBehavior bottomSheetBehavior;

    private EditText input_tenkhachhang_themkhachhang,input_diachi_themkhachhang,input_sdt_themkhachhang,input_tuoi_themkhachhang,input_cccd_themkhachhang;
    private Button label_btn_khachhang;

    private int MaKhachHang = -1;
    private int posItemKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        //SET DATA:
        setData();

        //SET ELEMENT:
        setTagElement();

        //SET LIST:
        StartListKhachhang();

        //ADD KHACHHANG:
        openBottomThemKhachHang();


        label_btn_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MaKhachHang != -1){
                    UPDATE_KHACHHANG(MaKhachHang, posItemKhachHang);
                    MaKhachHang = -1;
                }else {
                    INSERT_KHACHHANG();
                }
            }
        });
    }


    void setData(){
        khachHangList = new ArrayList<>();

        Cursor cursor = MainActivity.database.GetData("SELECT * FROM KhachHang");
        while (cursor.moveToNext()){
            khachHangList.add(new KhachHang(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));
        }
    }


    void setTagElement(){
        //MAIN ELEMENT:
        lvdanhsachkhachhang = findViewById(R.id.lv_danhsach_khachhang);
        btn_them_khachhang = findViewById(R.id.btn_them_khachhang);
        btn_close_them_khachhang = findViewById(R.id.btn_close_them_khachhang);
        layout_them_khachhang_bottom = findViewById(R.id.layout_them_khachhang_bottom);

        //ADD KHACHHANG ELEMENT:
        input_tenkhachhang_themkhachhang = findViewById(R.id.input_tenkhachhang_themkhachhang);
        input_diachi_themkhachhang = findViewById(R.id.input_diachi_themkhachhang);
        input_sdt_themkhachhang = findViewById(R.id.input_sdt_themkhachhang);
        input_tuoi_themkhachhang = findViewById(R.id.input_tuoi_themkhachhang);
        input_cccd_themkhachhang = findViewById(R.id.input_cccd_themkhachhang);
        label_btn_khachhang = findViewById(R.id.label_btn_khachhang);
    }


    void StartListKhachhang(){

        khachHangAdapter = new KhachHangAdapter(khachHangList, this, new IClickDeleteKhachHang() {
            @Override
            public void clickItem(KhachHang khachHang, int pos) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(KhachHangActivity.this);
                dialog.setTitle( "Bạn có muốn xóa khách hàng: " +  khachHang.getTen())
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }}).setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                        DELETE_KHACHHANG(khachHang.getMa(), pos);

                    }
                }).show();
            }
        }, new IClickItemKhachHang() {
            @Override
            public void clickItem(KhachHang khachHang, int pos) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    input_tenkhachhang_themkhachhang.setText(khachHang.getTen());
                    input_diachi_themkhachhang.setText(khachHang.getDiachi());
                    input_sdt_themkhachhang.setText(khachHang.getSDT());
                    input_cccd_themkhachhang.setText(khachHang.getCCCD());
                    input_tuoi_themkhachhang.setText(Integer.toString(khachHang.getTuoi()));
                    label_btn_khachhang.setText("SỬA KHÁCH HÀNG");

                    
                    MaKhachHang = khachHang.getMa();
                    posItemKhachHang = pos;
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        lvdanhsachkhachhang.setAdapter(khachHangAdapter);
    }


    public void openBottomThemKhachHang(){
        bottomSheetBehavior = BottomSheetBehavior.from(layout_them_khachhang_bottom);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn_them_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //CLEAR DATA ELEMENT
                    input_tenkhachhang_themkhachhang.setText("");
                    input_diachi_themkhachhang.setText("");
                    input_sdt_themkhachhang.setText("");
                    input_cccd_themkhachhang.setText("");
                    input_tuoi_themkhachhang.setText("");
                    label_btn_khachhang.setText("THÊM KHÁCH HÀNG");
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        btn_close_them_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }


    void INSERT_KHACHHANG(){
        long result = MainActivity.database.INSERT_KHACHHANG(
                input_tenkhachhang_themkhachhang.getText().toString(),
                input_diachi_themkhachhang.getText().toString(),
                Integer.valueOf(input_tuoi_themkhachhang.getText().toString()),
                input_cccd_themkhachhang.getText().toString(),
                input_sdt_themkhachhang.getText().toString()
        );
        Toast.makeText(KhachHangActivity.this, "Da them", Toast.LENGTH_SHORT).show();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        khachHangList.add(new KhachHang(
                (int) result,
                input_tenkhachhang_themkhachhang.getText().toString(),
                input_diachi_themkhachhang.getText().toString(),
                Integer.valueOf(input_tuoi_themkhachhang.getText().toString()),
                input_cccd_themkhachhang.getText().toString(),
                input_sdt_themkhachhang.getText().toString()
        ));
        khachHangAdapter.notifyDataSetChanged();
    }

    void DELETE_KHACHHANG(int MAKHACHHANG, int POS){
        MainActivity.database.QueryData("DELETE FROM KhachHang WHERE MaKhachHang ='"+MAKHACHHANG+"'");
        khachHangList.remove(POS);
        khachHangAdapter.notifyDataSetChanged();
    }

    void UPDATE_KHACHHANG(int MAKHACHHANG, int POS){


        MainActivity.database.QueryData("UPDATE KhachHang SET " +
                "Ten = '"+input_tenkhachhang_themkhachhang.getText().toString()+"' ," +
                "DiaChi = '"+input_diachi_themkhachhang.getText().toString()+"' ," +
                "Tuoi = '"+input_tuoi_themkhachhang.getText().toString()+"' ," +
                "CCCD = '"+input_cccd_themkhachhang.getText().toString()+"' ," +
                "SDT = '"+input_sdt_themkhachhang.getText().toString()+"' " +
                "WHERE MaKhachHang = " + MAKHACHHANG );

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        khachHangList.get(POS).setTen(input_tenkhachhang_themkhachhang.getText().toString());
        khachHangList.get(POS).setDiachi(input_diachi_themkhachhang.getText().toString());
        khachHangList.get(POS).setTuoi(Integer.valueOf(input_tuoi_themkhachhang.getText().toString()));
        khachHangList.get(POS).setCCCD(input_cccd_themkhachhang.getText().toString());
        khachHangList.get(POS).setSDT(input_sdt_themkhachhang.getText().toString());

        khachHangAdapter.notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}