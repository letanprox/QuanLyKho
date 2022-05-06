package com.example.qlkhov2.QuanLyNhaCungCap;

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
import com.example.qlkhov2.QuanLyKho.DsKho;
import com.example.qlkhov2.QuanLyKho.NhaKhoActivity;
import com.example.qlkhov2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class NhaSanXuatActivity extends AppCompatActivity {

 //   private RecyclerView rcvdanhsachnsx;
 //   private NhaSanXuatAdapter nhaSanXuatAdapter;

    private List<NhaSanXuat> nhaSanXuatList;
    private NhaCungCapAdapter nhaCungCapAdapter;
    private ListView listViewncc;

    private Button btn_them_nsx;
    private TextView btn_close_them_nsx;

    private LinearLayout layout_them_nsx_bottom;
    private BottomSheetBehavior bottomSheetBehavior;

    private EditText input_tennsx_themnsx,input_diachi_themnsx,input_sdt_themnsx,input_email_themnsx;
    private Button label_btn_nsx;

    private int MaNSX = -1;
    private int posItemNSX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_san_xuat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        setData();

        setTagElement();

        setListNcc();

        openBottomThemNSX();

        label_btn_nsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MaNSX != -1){
                    UPDATE_NHACUNGCAP(MaNSX, posItemNSX);
                    MaNSX = -1;
                }else {
                    INSERT_NHACUNGCAP();
                }
            }
        });
    }

    void setData(){
        nhaSanXuatList = new ArrayList<>();

        Cursor cursor = MainActivity.database.GetData("SELECT * FROM NhaCungCap");
        while (cursor.moveToNext()){
            nhaSanXuatList.add(new NhaSanXuat(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }
    }


    void setTagElement(){
        //SET MAIN ELEMENT:
        //rcvdanhsachnsx = findViewById(R.id.rcv_danhsach_nsx);
        listViewncc = findViewById(R.id.lv_danhsach_nsx);

        btn_them_nsx = findViewById(R.id.btn_them_nsx);
        layout_them_nsx_bottom = findViewById(R.id.layout_them_nsx_bottom);
        btn_close_them_nsx = findViewById(R.id.btn_close_them_nsx);

        //SET ADD ELEMENT:
        input_tennsx_themnsx = findViewById(R.id.input_tennsx_themnsx);
        input_diachi_themnsx = findViewById(R.id.input_diachi_themnsx);
        input_sdt_themnsx = findViewById(R.id.input_sdt_themnsx);
        input_email_themnsx = findViewById(R.id.input_email_themnsx);
        label_btn_nsx = findViewById(R.id.label_btn_nsx);
    }

    void setListNcc(){

        nhaCungCapAdapter = new NhaCungCapAdapter(nhaSanXuatList,this, new IClickDeleteNSX() {
            @Override
            public void clickItem(NhaSanXuat nhaSanXuat, int pos) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NhaSanXuatActivity.this);
                dialog.setTitle( "Bạn có muốn xóa nhà cung cấp: " +  nhaSanXuat.getTen())
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }}).setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        DELETE_NHACUNGCAP(nhaSanXuat.getMa(), pos);
                    }
                }).show();
            }
        }, new IClickItemNSX() {
            @Override
            public void clickItem(NhaSanXuat nhaSanXuat, int pos) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    input_tennsx_themnsx.setText(nhaSanXuat.getTen());
                    input_diachi_themnsx.setText(nhaSanXuat.getDiaChi());
                    input_sdt_themnsx.setText(nhaSanXuat.getSDT());
                    input_email_themnsx.setText(nhaSanXuat.getEmail());
                    label_btn_nsx.setText("SỬA NHÀ CUNG CẤP");

                    posItemNSX = pos;
                    MaNSX = nhaSanXuat.getMa();

                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        listViewncc.setAdapter(nhaCungCapAdapter);

    }

    void openBottomThemNSX(){
        bottomSheetBehavior = BottomSheetBehavior.from(layout_them_nsx_bottom);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn_them_nsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    input_tennsx_themnsx.setText("");
                    input_diachi_themnsx.setText("");
                    input_sdt_themnsx.setText("");
                    input_email_themnsx.setText("");
                    label_btn_nsx.setText("THÊM NHÀ CUNG CẤP");
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        btn_close_them_nsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }



    void INSERT_NHACUNGCAP(){
        long result = MainActivity.database.INSERT_NHACUNGCAP(
                input_tennsx_themnsx.getText().toString(),
                input_diachi_themnsx.getText().toString(),
                input_email_themnsx.getText().toString(),
                input_sdt_themnsx.getText().toString()
        );
        Toast.makeText(NhaSanXuatActivity.this, "Da them", Toast.LENGTH_SHORT).show();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        nhaSanXuatList.add(new NhaSanXuat(
                (int) result,
                input_tennsx_themnsx.getText().toString(),
                input_diachi_themnsx.getText().toString(),
                input_email_themnsx.getText().toString(),
                input_sdt_themnsx.getText().toString()
        ));
        nhaCungCapAdapter.notifyDataSetChanged();
    }

    void DELETE_NHACUNGCAP(int MANHACUNGCAP, int POS){
        MainActivity.database.QueryData("DELETE FROM NhaCungCap WHERE MaNhaCungCap ='"+MANHACUNGCAP+"'");
        nhaSanXuatList.remove(POS);
        nhaCungCapAdapter.notifyDataSetChanged();
    }

    void UPDATE_NHACUNGCAP(int MANHACUNGCAP, int POS){

        MainActivity.database.QueryData("UPDATE NhaCungCap SET " +
                "Ten = '"+input_tennsx_themnsx.getText().toString()+"' ," +
                "DiaChi = '"+input_diachi_themnsx.getText().toString()+"' ," +
                "Email = '"+input_email_themnsx.getText().toString()+"' ," +
                "SDT = '"+input_sdt_themnsx.getText().toString()+"' " +
                "WHERE MaNhaCungCap = " + MANHACUNGCAP );

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        nhaSanXuatList.get(POS).setTen(input_tennsx_themnsx.getText().toString());
        nhaSanXuatList.get(POS).setDiaChi(input_diachi_themnsx.getText().toString());
        nhaSanXuatList.get(POS).setEmail(input_email_themnsx.getText().toString());
        nhaSanXuatList.get(POS).setSDT(input_sdt_themnsx.getText().toString());

        nhaCungCapAdapter.notifyDataSetChanged();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}