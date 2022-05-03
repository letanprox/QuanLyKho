package com.example.qlkhov2.QuanLyPhieu.PhieuNhap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlkhov2.MainActivity;
import com.example.qlkhov2.QuanLyKho.DsKho;
import com.example.qlkhov2.QuanLyNhanVien.NhanVien;
import com.example.qlkhov2.TemPlate.BottomSheetListItemFragment;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.DialogItemSanPham;
import com.example.qlkhov2.TemPlate.DialogListFragment;
import com.example.qlkhov2.TemPlate.DialogListItem;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.DialogSanPhamFragment;
import com.example.qlkhov2.TemPlate.IClickItemDialogList;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.IClickItemSanPhamDialog;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.IClickThemSanPhamDialogButton;
import com.example.qlkhov2.QuanLySanPham.IClickDeleteSanPham;
import com.example.qlkhov2.QuanLySanPham.IClickItemSanPham;
import com.example.qlkhov2.QuanLySanPham.SanPham;
import com.example.qlkhov2.QuanLySanPham.SanPhamAdapter;
import com.example.qlkhov2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class PhieuNhapActivity extends AppCompatActivity {

    private RecyclerView rcv_sanpham;
    private List<SanPham> sanPhamList, sanPhamListLoad;
    private SanPhamAdapter sanPhamAdapter;

    private RecyclerView rcv_phieunhap;
    private List<PhieuNhap> phieuNhapList;
    private PhieuNhapAdapter phieuNhapAdapter;

    private Button btn_them_phieunhap, btn_them_sanpham_phieunhap,open_list_dialog_kho_phieunhap,open_list_dialog_nhanvien_phieunhap, btn_kho_item_bottom, label_btn_themphieunhap;
    private TextView btn_close_them_phieunhap, tongtien_phieunhap, tenncc_phieunhap, sdtncc_phieunhap, diachincc_phieunhap;
    private EditText input_ma_ncc_phieunhap;

    private LinearLayout layout_them_phieunhap_bottom;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<DialogItemSanPham> dialogItemSanPhamList;
    private List<DialogListItem> dialogListKhos, dialogListNhanViens;

    private String chose_sanpham_name;
    private int chose_sanpham_id;

    private int Makho = -1;
    private String TenKho;
    private int MaNhaCungCap = -1;
    private String TenNhaCungCap;
    private int MaNhanVien = -1;
    private String TenNhanVien;
    private int tongtien = 0;

    String ngaynhapkho = "12/12/2021";
    String ngaytaophieu = "30/12/2022";

    ChitietPhieuNhapDialogFragment chitietPhieuNhapDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_nhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        setTagElement();
        openBottomThemPhieuNhap();
        setDatas();

        //SET DIALOG KHO LIST:
        setKhoDialogList();

        //SET DIALOG NHANVIEN LIST:
        setNhanVienDialogList();

        //SET SANPHAMLIST:
        setSanPhamList();

        //SET DIALOG SANPHAM LIST:
        setSanPhamDialogList();

        //SET BOTTOM SHEET KHO LIST:
        clickOpenKhoBottomSheetFragment();

        //INPUT NCungCap:
        findNhaCungCap();
        setInfoNhaCungCap("...","...","...");

        //SET PHIEU NHAP AND CLICK INTO:
        phieuNhapAdapter = new PhieuNhapAdapter(phieuNhapList, this, new IClickItemPhieuNhap() {
            @Override
            public void clickItem(PhieuNhap phieuNhap) {

                CHITIET_PHIEUNHAP(phieuNhap);

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_phieunhap.setLayoutManager(linearLayoutManager);
        rcv_phieunhap.setAdapter(phieuNhapAdapter);


        //CLICK ADD PHIEU NHAP:
        label_btn_themphieunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                INSERT_PHIEUNHAP();

            }
        });


    }




    //INSERT PHIEU NHAP:
    void INSERT_PHIEUNHAP(){
        if(MaNhaCungCap != -1){
            long result = MainActivity.database.INSERT_PHIEUNHAP(Makho, MaNhaCungCap, MaNhanVien, sanPhamListLoad.size(), String.valueOf(tongtien), ngaytaophieu);
            for(SanPham obj : sanPhamListLoad) {
                MainActivity.database.QueryData("INSERT INTO SanPhamPhieuNhap VALUES( null,"+(int)result+" ,"+obj.getMa()+", "+obj.getSoluong()+");");
                Toast.makeText(PhieuNhapActivity.this, obj.getTen(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(PhieuNhapActivity.this,"Them thanh cong", Toast.LENGTH_SHORT).show();
            phieuNhapList.add(new PhieuNhap((int) result,ngaytaophieu,TenKho,TenNhanVien,TenNhaCungCap,String.valueOf(sanPhamListLoad.size()),String.valueOf(tongtien)));
            phieuNhapAdapter.notifyDataSetChanged();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else {
            Toast.makeText(PhieuNhapActivity.this,"Vui long chon ncc", Toast.LENGTH_SHORT).show();
        }
    }



    ///CHI TIET PHIEU NHAP:
    void CHITIET_PHIEUNHAP(PhieuNhap phieuNhap){
        List<PhieuNhap> phieuNhapListDetail = new ArrayList<>();
        Cursor cursor = MainActivity.database.GetData("SELECT c1.MaSanPham, c1.TenSanPham, c1.GiaTien,  q1.SoLuong " +
                "FROM (" +
                "SELECT * FROM SanPhamPhieuNhap WHERE MaPhieu='" + phieuNhap.getMaPhieu() + "'" +
                " ) AS q1 " +
                "LEFT JOIN SanPham c1 ON q1.MaSanPham = c1.MaSanPham ");
        while (cursor.moveToNext()){
            phieuNhapListDetail.add(new PhieuNhap( cursor.getInt(0), cursor.getString(1), cursor.getString(2), String.valueOf(cursor.getInt(3)) , String.valueOf(cursor.getInt(3)*Integer.valueOf(cursor.getString(2))),"", "" ));
        }
        chitietPhieuNhapDialogFragment = new ChitietPhieuNhapDialogFragment(phieuNhapListDetail, new IClickDeletePhieuNhap() {
            @Override
            public void clickItem() {
                MainActivity.database.QueryData("DELETE FROM PhieuNhap WHERE MaPhieu ='"+phieuNhap.getMaPhieu()+"'");
                MainActivity.database.QueryData("DELETE FROM SanPhamPhieuNhap WHERE MaPhieu ='"+phieuNhap.getMaPhieu()+"'");
                phieuNhapList.remove(phieuNhap);
                phieuNhapAdapter.notifyDataSetChanged();
                chitietPhieuNhapDialogFragment.dismiss();
            }
        });
        chitietPhieuNhapDialogFragment.show(getSupportFragmentManager(),"DIALOGCHITIETPHIEU");
    }




    //SET CONNECT ELEMENT:
    void setTagElement(){
        rcv_phieunhap = findViewById(R.id.rcv_phieu_nhap);

        btn_them_phieunhap = findViewById(R.id.btn_them_phieunhap);
        btn_them_sanpham_phieunhap = findViewById(R.id.btn_them_sanpham_phieunhap);
        rcv_sanpham = findViewById(R.id.rcv_sanpham_phieunhap);
        open_list_dialog_kho_phieunhap = findViewById(R.id.open_list_dialog_kho_phieunhap);
        open_list_dialog_nhanvien_phieunhap = findViewById(R.id.open_list_dialog_nhanvien_phieunhap);
        btn_kho_item_bottom = findViewById(R.id.btn_kho_item_bottom);
        input_ma_ncc_phieunhap = findViewById(R.id.input_ma_ncc_phieunhap);
        diachincc_phieunhap = findViewById(R.id.diachincc_phieunhap);
        sdtncc_phieunhap = findViewById(R.id.sdtncc_phieunhap);
        tenncc_phieunhap = findViewById(R.id.tenncc_phieunhap);
        tongtien_phieunhap = findViewById(R.id.tongtien_phieunhap);
        tongtien_phieunhap.setText("Tổng tiền: " + Integer.toString(tongtien));

        label_btn_themphieunhap = findViewById(R.id.label_btn_themphieunhap);

        layout_them_phieunhap_bottom = findViewById(R.id.layout_them_phieunhap_bottom);
        btn_close_them_phieunhap = findViewById(R.id.btn_close_them_phieunhap);
    }




    //SET...
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    void setInfoNhaCungCap(String ten,String sdt,String diachi){
        tenncc_phieunhap.setText("tên: "+ten);
        sdtncc_phieunhap.setText("sdt: "+sdt);
        diachincc_phieunhap.setText("địa chỉ: "+diachi);
    }
    void findNhaCungCap(){
        input_ma_ncc_phieunhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                MaNhaCungCap = -1;
                if(isEmpty(input_ma_ncc_phieunhap) == false){
                    int tempMaNhaCungCap = Integer.valueOf(input_ma_ncc_phieunhap.getText().toString());
                    setInfoNhaCungCap("...","...","...");
                    Cursor cursor = MainActivity.database.GetData("SELECT * FROM NhaCungCap WHERE MaNhaCungCap = "+tempMaNhaCungCap);

                    while (cursor.moveToNext()){
                        MaNhaCungCap = cursor.getInt(0);
                        TenNhaCungCap = cursor.getString(1);
                        setInfoNhaCungCap(cursor.getString(1),cursor.getString(2),cursor.getString(4));
                    }
                }else {
                    setInfoNhaCungCap("...","...","...");
                }
            }
        });
    }



    //LAYOUT THEM PHIEU NHAP:
    void openBottomThemPhieuNhap(){
        bottomSheetBehavior = BottomSheetBehavior.from(layout_them_phieunhap_bottom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setDraggable(false);
        btn_them_phieunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    //RESET LAYOUT:
                    MaNhaCungCap = -1;
                    tongtien = 0;
                    //----
                    Makho = Integer.valueOf(dialogListKhos.get(0).getId());
                    TenKho = dialogListKhos.get(0).getName();
                    open_list_dialog_kho_phieunhap.setText(dialogListKhos.get(0).getName());
                    //----
                    MaNhanVien = Integer.valueOf(dialogListNhanViens.get(0).getId());
                    TenNhanVien = dialogListNhanViens.get(0).getName();
                    open_list_dialog_nhanvien_phieunhap.setText(dialogListNhanViens.get(0).getName());
                    //----
                    sanPhamListLoad.clear();
                    tongtien_phieunhap.setText("Tổng tiền: " + Integer.toString(tongtien));
                    setInfoNhaCungCap("...","...","...");
                    input_ma_ncc_phieunhap.setText("");
                    sanPhamAdapter.notifyDataSetChanged();

                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        btn_close_them_phieunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }



    //GET DATA FOR ALL LIST:
    void setDatas(){
        sanPhamListLoad = new ArrayList<>();

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

        dialogItemSanPhamList = new ArrayList<>();
        cursor = MainActivity.database.GetData("SELECT * FROM SanPham");
        while (cursor.moveToNext()){
            dialogItemSanPhamList.add(new DialogItemSanPham(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getBlob(5)
            ));
        }

        dialogListKhos = new ArrayList<>();
        cursor = MainActivity.database.GetData("SELECT * FROM NhaKho");
        while (cursor.moveToNext()){
            dialogListKhos.add(new DialogListItem(
                    Integer.toString(cursor.getInt(0)),
                    cursor.getString(1)
            ));
        }

        dialogListNhanViens = new ArrayList<>();
        cursor = MainActivity.database.GetData("SELECT * FROM NhanVien");
        while (cursor.moveToNext()){
            dialogListNhanViens.add(new DialogListItem(
                    Integer.toString(cursor.getInt(0)),
                    cursor.getString(1)
            ));
        }

        phieuNhapList = new ArrayList<>();
        cursor = MainActivity.database.GetData("SELECT pn.MaPhieu , c1.TenKho, c2.TenNhanVien, c3.Ten , pn.SoSanPham , pn.TongTien, pn.NgayTaoPhieu " +
                "FROM PhieuNhap pn " +
                "LEFT JOIN NhaKho c1 ON pn.MaKho = c1.MaKho " +
                "LEFT JOIN NhanVien c2 ON pn.MaNhanVien = c2.MaNhanVien " +
                "LEFT JOIN NhaCungCap c3 ON pn.MaNhaCungCap = c3.MaNhaCungCap ");
        while (cursor.moveToNext()){
            phieuNhapList.add(new PhieuNhap(cursor.getInt(0),cursor.getString(6),cursor.getString(1),cursor.getString(2),cursor.getString(3),String.valueOf(cursor.getInt(4)),cursor.getString(5)));
        }

    }



    //OPEN LAYOUT SHEET KHO:
    private  void  clickOpenKhoBottomSheetFragment(){
        btn_kho_item_bottom.setText("KHO: "+dialogListKhos.get(0).getName());
        btn_kho_item_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetListItemFragment khobottomSheetListItemFragment = new BottomSheetListItemFragment(dialogListKhos, new IClickItemDialogList() {
                    @Override
                    public void clickItem(DialogListItem dialogListItem) {
                        btn_kho_item_bottom.setText("KHO: "+dialogListItem.getName());
                        Toast.makeText(getApplicationContext(),"Chọn kho: " + dialogListItem.getName(), Toast.LENGTH_LONG).show();
                    }
                });
                khobottomSheetListItemFragment.show(getSupportFragmentManager(), khobottomSheetListItemFragment.getTag());
            }
        });
    }



    ///SET SANPHAM CHOSE LIST:
    void setSanPhamList(){
        sanPhamAdapter = new SanPhamAdapter(sanPhamListLoad, this, new IClickItemSanPham() {
            @Override
            public void clickItem(SanPham sanPham, int pos) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }, new IClickDeleteSanPham() {
            @Override
            public void clickItem(SanPham sanPham, int pos) {
                sanPhamListLoad.remove(sanPham);

                tongtien = tongtien - Integer.valueOf(sanPham.getSoluong())*Integer.valueOf(sanPham.getGiatien());
                tongtien_phieunhap.setText("Tổng tiền: " + Integer.toString(tongtien));
                sanPhamAdapter.notifyDataSetChanged();
            }
        },2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_sanpham.setLayoutManager(linearLayoutManager);
        rcv_sanpham.setAdapter(sanPhamAdapter);
    }



    ///SET DIALOG SANPHAM LIST:
    void setSanPhamDialogList(){
        DialogSanPhamFragment dialogSanPhamFragment  = new DialogSanPhamFragment(dialogItemSanPhamList, new IClickItemSanPhamDialog() {
            @Override
            public void clickItem(DialogItemSanPham dialogItemSanPham) {
                chose_sanpham_id =  dialogItemSanPham.getId();
                chose_sanpham_name = dialogItemSanPham.getName();
            }
        }, new IClickThemSanPhamDialogButton() {
            @Override
            public void clickItem() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PhieuNhapActivity.this);
                final EditText input = new EditText(PhieuNhapActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                dialog.setView(input);
                dialog.setTitle( "Nhập số lượng sản phẩm: " + chose_sanpham_name)
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                for(SanPham obj : sanPhamListLoad) {
                                    if(obj.getMa() == (chose_sanpham_id)){
                                        sanPhamListLoad.remove(obj);
                                    }
                                }
                                for(SanPham obj : sanPhamList) {
                                    if(obj.getMa() == (chose_sanpham_id)){
                                        if(!input.getText().toString().isEmpty()){
                                            obj.setSoluong(Integer.parseInt(input.getText().toString()));
                                            sanPhamListLoad.add(obj);

                                            tongtien = tongtien + Integer.parseInt(obj.getGiatien())*Integer.parseInt(input.getText().toString());
                                            tongtien_phieunhap.setText("Tổng tiền: " + Integer.toString(tongtien));
                                            sanPhamAdapter.notifyDataSetChanged();
                                        }else {
                                            Toast.makeText(PhieuNhapActivity.this, "Vui lòng nhập số lượng!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }
                        }).show();
            }
        });
        btn_them_sanpham_phieunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSanPhamFragment.show(getSupportFragmentManager(),"THEM PHIEU NHAP");
            }
        });
    }
    ///SET DIALOG KHO LIST:
    void setKhoDialogList(){
        Makho = Integer.valueOf(dialogListKhos.get(0).getId());
        TenKho = dialogListKhos.get(0).getName();
        open_list_dialog_kho_phieunhap.setText(dialogListKhos.get(0).getName());
        DialogListFragment dialogListFragment  = new DialogListFragment(dialogListKhos, new IClickItemDialogList() {
            @Override
            public void clickItem(DialogListItem dialogListItem) {
                open_list_dialog_kho_phieunhap.setText(dialogListItem.getName());
                Makho = Integer.valueOf(dialogListItem.getId()) ;
                TenKho = dialogListItem.getName();
                Toast.makeText(PhieuNhapActivity.this,"Chọn kho: " + dialogListItem.getName(), Toast.LENGTH_LONG).show();
            }
        });
        open_list_dialog_kho_phieunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListFragment.show(getSupportFragmentManager(),"Examplex Dialog");
            }
        });
    }
    ///SET NHAN VIEN DIALOG LIST:
    void setNhanVienDialogList(){
        MaNhanVien = Integer.valueOf(dialogListNhanViens.get(0).getId());
        TenNhanVien = dialogListNhanViens.get(0).getName();
        open_list_dialog_nhanvien_phieunhap.setText(dialogListNhanViens.get(0).getName());
        DialogListFragment dialogListFragment  = new DialogListFragment(dialogListNhanViens, new IClickItemDialogList() {
            @Override
            public void clickItem(DialogListItem dialogListItem) {
                MaNhanVien = Integer.valueOf(dialogListItem.getId()) ;
                TenNhanVien = dialogListItem.getName();
                open_list_dialog_nhanvien_phieunhap.setText(dialogListItem.getName());
                Toast.makeText(PhieuNhapActivity.this,"Chọn nhân viên: " + dialogListItem.getName(), Toast.LENGTH_LONG).show();
            }
        });
        open_list_dialog_nhanvien_phieunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListFragment.show(getSupportFragmentManager(),"Examplexx Dialog");
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
}