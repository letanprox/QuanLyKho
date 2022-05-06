package com.example.qlkhov2.QuanLyNhanVien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
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
import com.example.qlkhov2.QuanLySanPham.SanPham;
import com.example.qlkhov2.TemPlate.DialogListFragment;
import com.example.qlkhov2.TemPlate.DialogListItem;
import com.example.qlkhov2.TemPlate.IClickItemDialogList;
import com.example.qlkhov2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class NhanVienActivity extends AppCompatActivity {

    private ListView lvdanhsachnhanvien;
    private List<NhanVien> nhanVienList;
    private NhanVienAdapter nhanVienAdapter;

    private Button btn_them_nhanvien;
    private TextView btn_close_them_nhanvien;

    private LinearLayout layout_them_nhanvien_bottom;
    private BottomSheetBehavior bottomSheetBehavior;

    private Button open_list_dialog_kho;
    private List<DialogListItem> dialogListItemList;

    private int chose_kho = -1;
    private String SDT;
    private int MaNhanVien = -1;
    private int PosItemNhanVien = -1;

    Button label_btn_nhanvien, label_btn_sua_nhanvien, label_btn_xoa_nhanvien;

    private static final int REQUEST_CALL = 1;

    EditText input_ten_themnhanvien,input_diachi_themnhanvien,input_tuoi_themnhanvien,input_cccd_themnhanvien,input_sdt_themnhanvien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        setData();

        setTagElement();

        setListNhanvien();

        openBottomThemNhanvien();

        dialogchoseKhotoAdd();

        label_btn_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long result = MainActivity.database.INSERT_NHANVIEN(
                        input_ten_themnhanvien.getText().toString(),
                        input_diachi_themnhanvien.getText().toString(),
                        Integer.valueOf(input_tuoi_themnhanvien.getText().toString()),
                        input_cccd_themnhanvien.getText().toString(),
                        input_sdt_themnhanvien.getText().toString(),
                        chose_kho
                );

                Toast.makeText(NhanVienActivity.this, "Da them", Toast.LENGTH_SHORT).show();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                nhanVienList.add(new NhanVien(
                        (int) result,
                        input_ten_themnhanvien.getText().toString(),
                        input_diachi_themnhanvien.getText().toString(),
                        Integer.valueOf(input_tuoi_themnhanvien.getText().toString()),
                        input_cccd_themnhanvien.getText().toString(),
                        input_sdt_themnhanvien.getText().toString(),
                        chose_kho
                ));
                nhanVienAdapter.notifyDataSetChanged();

            }
        });


        label_btn_xoa_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DELETE_NHANVIEN(MaNhanVien, PosItemNhanVien);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        label_btn_sua_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UPDATE_NHANVIEN(MaNhanVien, PosItemNhanVien);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }


    void setData(){
        //LIST NHAN VIEN:
        nhanVienList = new ArrayList<>();
        Cursor cursor = MainActivity.database.GetData("SELECT * FROM NhanVien");
        while (cursor.moveToNext()){
            nhanVienList.add(new NhanVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
        }


//        nhanVienList.add(new NhanVien("Ngọc Thi","12/An nhân",12,"4244545","12312321","1","Nhân Viên","12"));
        //LIST KHO:
        dialogListItemList = new ArrayList<>();
        cursor = MainActivity.database.GetData("SELECT MaKho,TenKho FROM NhaKho");
        while (cursor.moveToNext()){
            dialogListItemList.add(new DialogListItem(
                    Integer.toString(cursor.getInt(0)),
                    cursor.getString(1)
            ));
        }
    }


    void setTagElement(){
        //MAIN ELEMENT:
        lvdanhsachnhanvien = findViewById(R.id.lv_danhsach_nhavien);

        //ADD NHAN VIEN ELEMENT:
        input_ten_themnhanvien = findViewById(R.id.input_ten_themnhanvien);
        input_diachi_themnhanvien = findViewById(R.id.input_diachi_themnhanvien);
        input_tuoi_themnhanvien = findViewById(R.id.input_tuoi_themnhanvien);
        input_cccd_themnhanvien = findViewById(R.id.input_cccd_themnhanvien);
        input_sdt_themnhanvien = findViewById(R.id.input_sdt_themnhanvien);
        label_btn_nhanvien = findViewById(R.id.label_btn_nhanvien);
        label_btn_xoa_nhanvien = findViewById(R.id.label_btn_xoa_nhanvien);
        label_btn_sua_nhanvien = findViewById(R.id.label_btn_sua_nhanvien);
        open_list_dialog_kho = findViewById(R.id.open_list_dialog_kho);
            //DIALOG KHO ELEMENT:
            open_list_dialog_kho = findViewById(R.id.open_list_dialog_kho);
    }


    void setListNhanvien(){

        nhanVienAdapter = new NhanVienAdapter(nhanVienList, this, new IClickDeleteNhanVien() {
            @Override
            public void clickItem(NhanVien nhanVien) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NhanVienActivity.this);

                makePhoneCall(nhanVien.getSDT());
//                dialog.setTitle( "Bạn có muốn goi nhân viên: " +  nhanVien.getTen())
//                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialoginterface, int i) {
//                                dialoginterface.cancel();
//                            }}).setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//
//                    }
//                }).show();
            }
        }, new IClickItemNhanVien() {
            @Override
            public void clickItem(NhanVien nhanVien, int pos) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                input_ten_themnhanvien.setText(nhanVien.getTen());
                input_diachi_themnhanvien.setText(nhanVien.getDiachi());
                input_tuoi_themnhanvien.setText(Integer.toString(nhanVien.getTuoi()));
                input_cccd_themnhanvien.setText(nhanVien.getCCCD());
                input_sdt_themnhanvien.setText(nhanVien.getSDT());
                label_btn_nhanvien.setVisibility(View.GONE);
                label_btn_sua_nhanvien.setVisibility(View.VISIBLE);
                label_btn_xoa_nhanvien.setVisibility(View.VISIBLE);

                MaNhanVien = nhanVien.getMa();
                PosItemNhanVien = pos;

                for(DialogListItem obj : dialogListItemList) {
                    if(Integer.valueOf(obj.getId())  == (chose_kho)){
                        open_list_dialog_kho.setText(obj.getName());
                    }
                }



            }
        });
        lvdanhsachnhanvien.setAdapter(nhanVienAdapter);
    }


    private void makePhoneCall(String SDT) {
        String number = SDT;
        SDT = SDT;
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(NhanVienActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NhanVienActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(NhanVienActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }


    void openBottomThemNhanvien(){
        btn_them_nhanvien = findViewById(R.id.btn_them_nhanvien);
        layout_them_nhanvien_bottom = findViewById(R.id.layout_them_nhanvien_bottom);
        bottomSheetBehavior = BottomSheetBehavior.from(layout_them_nhanvien_bottom);
        btn_close_them_nhanvien = findViewById(R.id.btn_close_them_nhanvien);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn_them_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    input_ten_themnhanvien.setText("");
                    input_diachi_themnhanvien.setText("");
                    input_tuoi_themnhanvien.setText("");
                    input_cccd_themnhanvien.setText("");
                    input_sdt_themnhanvien.setText("");

                    label_btn_nhanvien.setVisibility(View.VISIBLE);
                    label_btn_sua_nhanvien.setVisibility(View.GONE);
                    label_btn_xoa_nhanvien.setVisibility(View.GONE);

                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        btn_close_them_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }


    void dialogchoseKhotoAdd(){
        open_list_dialog_kho.setText(dialogListItemList.get(0).getName());
        chose_kho = Integer.valueOf(dialogListItemList.get(0).getId());
        DialogListFragment dialogListFragment  = new DialogListFragment(dialogListItemList, new IClickItemDialogList() {
            @Override
            public void clickItem(DialogListItem dialogListItem) {
                open_list_dialog_kho.setText(dialogListItem.getName());
                chose_kho = Integer.valueOf(dialogListItem.getId());
                Toast.makeText(NhanVienActivity.this,"Chọn kho: " + dialogListItem.getName(), Toast.LENGTH_LONG).show();
            }
        });
        open_list_dialog_kho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListFragment.show(getSupportFragmentManager(),"Example Dialog");
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(SDT);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void DELETE_NHANVIEN(int MANHANVIEN, int POS){
        MainActivity.database.QueryData("DELETE FROM NhanVien WHERE MaNhanVien ='"+MANHANVIEN+"'");
        nhanVienList.remove(POS);
        nhanVienAdapter.notifyDataSetChanged();
    }

    void UPDATE_NHANVIEN(int MANHANVIEN, int POS){
        MainActivity.database.QueryData("UPDATE NhanVien SET " +
                "TenNhanVien = '"+input_ten_themnhanvien.getText().toString()+" ' ," +
                "DiaChi = '"+input_diachi_themnhanvien.getText().toString()+" ' ," +
                "Tuoi = '"+input_tuoi_themnhanvien.getText().toString()+" ' ," +
                "CCCD = '"+input_cccd_themnhanvien.getText().toString()+" ' ," +
                "SDT = '"+input_sdt_themnhanvien.getText().toString()+" ' ," +
                "MaKho = '"+chose_kho+"' " +
                "WHERE MaNhanVien = " + MANHANVIEN );
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        nhanVienList.get(POS).setTen(input_ten_themnhanvien.getText().toString());
        nhanVienList.get(POS).setDiachi(input_diachi_themnhanvien.getText().toString());
        nhanVienList.get(POS).setSDT(input_sdt_themnhanvien.getText().toString());
        nhanVienList.get(POS).setTuoi( Integer.valueOf(input_tuoi_themnhanvien.getText().toString()));
        nhanVienList.get(POS).setCCCD(input_cccd_themnhanvien.getText().toString());
        nhanVienList.get(POS).setMaKho(chose_kho);
        nhanVienAdapter.notifyDataSetChanged();
    }
}