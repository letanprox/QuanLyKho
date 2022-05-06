package com.example.qlkhov2.QuanLyKho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
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
import com.example.qlkhov2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class NhaKhoActivity extends AppCompatActivity {

    private ListView lvdanhsachkho;
    private List<DsKho> dsKhoList;
    private NhaKhoAdapter nhaKhoAdapter;

    private Button btn_them_kho, label_btn_dskho;
    private TextView btn_close_them_kho;
    private LinearLayout layout_them_kho_bottom;
    private BottomSheetBehavior bottomSheetBehavior;

    private EditText input_tenkho_themkho,input_diachi_themkho,input_sdt_themkho,input_email_themkho,input_mota_themkho;

    private int MaKho = -1;
    private int posItemDsKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_kho);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        //SET VIEW TAG:
        setTagElement();

        //GET DATA KHO:
        setData();

        //START LIST KHO:
        //SUA KHO:
        StartListKho();

        //OPEN THEM KHO:
        openBottomThemKho();

        label_btn_dskho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MaKho != -1){
                    UPDATE_NHAKHO(MaKho, posItemDsKho);
                    MaKho = -1;
                }else {
                    INSERT_NHAKHO();
                }
            }
        });
    }


    void setData(){
        dsKhoList = new ArrayList<>();
        //GET DATA:
        Cursor cursor = MainActivity.database.GetData("SELECT * FROM NhaKho");
        while (cursor.moveToNext()){
            dsKhoList.add(new DsKho(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));
        }
    }


    void setTagElement(){
        //MAIN ELEMENT:
        lvdanhsachkho = findViewById(R.id.lv_danhsach_kho);
        btn_them_kho = findViewById(R.id.btn_them_kho);
        btn_close_them_kho = findViewById(R.id.btn_close_them_kho);
        layout_them_kho_bottom = findViewById(R.id.layout_them_kho_bottom);

        //ADD KHO ELEMENT:
        input_tenkho_themkho = findViewById(R.id.input_tenkho_themkho);
        input_diachi_themkho = findViewById(R.id.input_diachi_themkho);
        input_sdt_themkho = findViewById(R.id.input_sdt_themkho);
        input_email_themkho = findViewById(R.id.input_email_themkho);
        input_mota_themkho = findViewById(R.id.input_mota_themkho);
        label_btn_dskho = findViewById(R.id.label_btn_dskho);
    }

    void StartListKho(){

        nhaKhoAdapter = new NhaKhoAdapter(dsKhoList, new IClickItemDsKho() {
            @Override
            public void clickItem(DsKho dsKho, int pos) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //SET DATA ELEMENT
                    input_tenkho_themkho.setText(dsKho.getTenKho());
                    input_diachi_themkho.setText(dsKho.getDiaChi());
                    input_sdt_themkho.setText(dsKho.getSDT());
                    input_email_themkho.setText(dsKho.getEmail());
                    input_mota_themkho.setText(dsKho.getMota());
                    label_btn_dskho.setText("SỬA KHO");

                    MaKho = dsKho.getMaKho();
                    posItemDsKho = pos;

                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        }, new IClickDeleteDsKho() {
            @Override
            public void clickItem(DsKho dsKho, int pos) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NhaKhoActivity.this);
                dialog.setTitle( "Bạn có muốn xóa kho: " +  dsKho.getTenKho())
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }}).setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        DELETE_NHAKHO(dsKho.getMaKho(), pos);
                    }
                }).show();
            }
        }
        );
        lvdanhsachkho.setAdapter(nhaKhoAdapter);
    }

    void openBottomThemKho(){
        bottomSheetBehavior = BottomSheetBehavior.from(layout_them_kho_bottom);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn_them_kho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //CLEAR DATA ELEMENT
                    input_tenkho_themkho.setText("");
                    input_diachi_themkho.setText("");
                    input_sdt_themkho.setText("");
                    input_email_themkho.setText("");
                    input_mota_themkho.setText("");
                    label_btn_dskho.setText("THÊM KHO");

                    MaKho = -1;
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        btn_close_them_kho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    void UPDATE_NHAKHO(int MAKHO, int POS){
        MainActivity.database.QueryData("UPDATE NhaKho SET " +
                "TenKho = '"+input_tenkho_themkho.getText().toString()+" ' ," +
                "DiaChi = '"+input_diachi_themkho.getText().toString()+" ' ," +
                "Email = '"+input_email_themkho.getText().toString()+" ' ," +
                "SDT = '"+input_sdt_themkho.getText().toString()+" ' ," +
                "MoTa = '"+input_mota_themkho.getText().toString()+" ' " +
                "WHERE MaKho = " + MAKHO );
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        dsKhoList.get(POS).setTenKho(input_tenkho_themkho.getText().toString());
        dsKhoList.get(POS).setDiaChi(input_diachi_themkho.getText().toString());
        dsKhoList.get(POS).setEmail(input_email_themkho.getText().toString());
        dsKhoList.get(POS).setSDT(input_sdt_themkho.getText().toString());
        dsKhoList.get(POS).setMota(input_mota_themkho.getText().toString());
        nhaKhoAdapter.notifyDataSetChanged();
    }


    void INSERT_NHAKHO(){
        long result = MainActivity.database.INSERT_NHAKHO(
                input_tenkho_themkho.getText().toString(),
                input_diachi_themkho.getText().toString(),
                input_email_themkho.getText().toString(),
                input_sdt_themkho.getText().toString(),
                input_mota_themkho.getText().toString()
        );
        Toast.makeText(NhaKhoActivity.this, "Da them", Toast.LENGTH_SHORT).show();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        dsKhoList.add(new DsKho(
                (int) result,
                input_tenkho_themkho.getText().toString(),
                input_diachi_themkho.getText().toString(),
                input_email_themkho.getText().toString(),
                input_sdt_themkho.getText().toString(),
                input_mota_themkho.getText().toString()
        ));
        nhaKhoAdapter.notifyDataSetChanged();
    }

    void DELETE_NHAKHO(int MAKHO, int POS){
        MainActivity.database.QueryData("DELETE FROM NhaKho WHERE MaKho ='"+MAKHO+"'");
        dsKhoList.remove(POS);
        nhaKhoAdapter.notifyDataSetChanged();
    }


}