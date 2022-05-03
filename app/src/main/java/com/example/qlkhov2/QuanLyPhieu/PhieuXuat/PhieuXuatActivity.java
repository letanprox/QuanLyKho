package com.example.qlkhov2.QuanLyPhieu.PhieuXuat;

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
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.ChitietPhieuNhapDialogFragment;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.IClickDeletePhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.IClickItemPhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhapActivity;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhapAdapter;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.DialogItemSanPham;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.DialogSanPhamFragment;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.IClickItemSanPhamDialog;
import com.example.qlkhov2.QuanLyPhieu.SanPhamList.IClickThemSanPhamDialogButton;
import com.example.qlkhov2.QuanLySanPham.IClickDeleteSanPham;
import com.example.qlkhov2.QuanLySanPham.IClickItemSanPham;
import com.example.qlkhov2.QuanLySanPham.SanPham;
import com.example.qlkhov2.QuanLySanPham.SanPhamAdapter;
import com.example.qlkhov2.R;
import com.example.qlkhov2.TemPlate.BottomSheetListItemFragment;
import com.example.qlkhov2.TemPlate.DialogListFragment;
import com.example.qlkhov2.TemPlate.DialogListItem;
import com.example.qlkhov2.TemPlate.IClickItemDialogList;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class PhieuXuatActivity extends AppCompatActivity {


    private RecyclerView rcv_sanpham;
    private List<SanPham> sanPhamList, sanPhamListLoad;
    private SanPhamAdapter sanPhamAdapter;


    private RecyclerView rcv_phieuxuat;
    private List<PhieuXuat> phieuXuatList;
    private PhieuXuatAdapter phieuXuatAdapter;

    private Button btn_them_phieuxuat, btn_them_sanpham_phieuxuat,open_list_dialog_kho_phieuxuat,open_list_dialog_nhanvien_phieuxuat, btn_kho_item_bottom, label_btn_themphieuxuat;
    private TextView btn_close_them_phieuxuat , tongtien_phieuxuat , tenncc_phieuxuat, sdtncc_phieuxuat, diachincc_phieuxuat;
    private EditText input_ma_khachhang_phieuxuat;

    private LinearLayout layout_them_phieuxuat_bottom;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<DialogItemSanPham> dialogItemSanPhamList;
    private List<DialogListItem> dialogListKhos,dialogListNhanViens;

    private String  chose_sanpham_name;
    private int chose_sanpham_id;

    private int Makho = -1;
    private String TenKho;
    private int MaKhachHang = -1;
    private String TenKhachHang;
    private int MaNhanVien = -1;
    private String TenNhanVien;
    private int tongtien = 0;

    String ngayxuatkho = "12/12/2021";
    String ngaytaophieu = "30/12/2022";

    ChitietPhieuXuatDialogFragment chitietPhieuXuatDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_xuat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


        setTagElement();

        setData();

        clickOpenKhoBottomSheetFragment();

        openBottomThemPhieuXuat();

        setSanPhamList();

        setSanPhamDialogList();


        //SET DIALOG KHO LIST:
        setKhoDialogList();

        //SET DIALOG NHANVIEN LIST:
        setNhanVienDialogList();


        ///INPUT KHACH HANG:
        findKhachHang();
        setInfoKhachHang("...","...","...");

        //SET PHIEU NHAP AND CLICK INTO:
        phieuXuatAdapter = new PhieuXuatAdapter(phieuXuatList, this, new IClickItemPhieuXuat() {
            @Override
            public void clickItem(PhieuXuat phieuXuat) {

                CHITIET_PHIEUXUAT(phieuXuat);

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_phieuxuat.setLayoutManager(linearLayoutManager);
        rcv_phieuxuat.setAdapter(phieuXuatAdapter);



        //CLICK ADD PHIEU NHAP:
        label_btn_themphieuxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                INSERT_PHIEUXUAT();

            }
        });

    }




    //INSERT PHIEU XUAT:
    void INSERT_PHIEUXUAT(){
        if(MaKhachHang != -1){
            long result = MainActivity.database.INSERT_PHIEUXUAT(Makho, MaKhachHang, MaNhanVien, sanPhamListLoad.size(), String.valueOf(tongtien), ngaytaophieu);
            for(SanPham obj : sanPhamListLoad) {
                MainActivity.database.QueryData("INSERT INTO SanPhamPhieuXuat VALUES( null,"+(int)result+" ,"+obj.getMa()+", "+obj.getSoluong()+");");
                Toast.makeText(PhieuXuatActivity.this, obj.getTen(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(PhieuXuatActivity.this,"Them thanh cong", Toast.LENGTH_SHORT).show();
            phieuXuatList.add(new PhieuXuat((int) result,ngaytaophieu,TenKho,TenNhanVien,TenKhachHang,String.valueOf(sanPhamListLoad.size()),String.valueOf(tongtien)));
            phieuXuatAdapter.notifyDataSetChanged();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else {
            Toast.makeText(PhieuXuatActivity.this,"Vui long chon Khach hang", Toast.LENGTH_SHORT).show();
        }
    }


    ///CHI TIET PHIEU XUAT:
    void CHITIET_PHIEUXUAT(PhieuXuat phieuXuat){
        List<PhieuXuat> phieuXuatListDetail = new ArrayList<>();
        Cursor cursor = MainActivity.database.GetData("SELECT c1.MaSanPham, c1.TenSanPham, c1.GiaTien,  q1.SoLuong " +
                "FROM (" +
                "SELECT * FROM SanPhamPhieuXuat WHERE MaPhieu='" + phieuXuat.getMaPhieu() + "'" +
                " ) AS q1 " +
                "LEFT JOIN SanPham c1 ON q1.MaSanPham = c1.MaSanPham ");
        while (cursor.moveToNext()){
            phieuXuatListDetail.add(new PhieuXuat( cursor.getInt(0), cursor.getString(1), cursor.getString(2), String.valueOf(cursor.getInt(3)) , String.valueOf(cursor.getInt(3)*Integer.valueOf(cursor.getString(2))),"", "" ));
        }
        chitietPhieuXuatDialogFragment = new ChitietPhieuXuatDialogFragment(phieuXuatListDetail, new IClickDeletePhieuXuat() {
            @Override
            public void clickItem() {
                MainActivity.database.QueryData("DELETE FROM PhieuXuat WHERE MaPhieu ='"+phieuXuat.getMaPhieu()+"'");
                MainActivity.database.QueryData("DELETE FROM SanPhamPhieuXuat WHERE MaPhieu ='"+phieuXuat.getMaPhieu()+"'");
                phieuXuatList.remove(phieuXuat);
                phieuXuatAdapter.notifyDataSetChanged();
                chitietPhieuXuatDialogFragment.dismiss();
            }
        });
        chitietPhieuXuatDialogFragment.show(getSupportFragmentManager(),"DIALOGCHITIETPHIEU1");
    }


    //SET CONNECT ELEMENT:
    void setTagElement(){
        rcv_phieuxuat = findViewById(R.id.rcv_phieu_xuat);

        btn_them_phieuxuat = findViewById(R.id.btn_them_phieuxuat);
        btn_them_sanpham_phieuxuat = findViewById(R.id.btn_them_sanpham_phieuxuat);
        rcv_sanpham = findViewById(R.id.rcv_sanpham_phieuxuat);
        open_list_dialog_kho_phieuxuat = findViewById(R.id.open_list_dialog_kho_phieuxuat);
        btn_kho_item_bottom = findViewById(R.id.btn_kho_item_bottom_phieuxuat);

        input_ma_khachhang_phieuxuat = findViewById(R.id.input_ma_khachhang_phieuxuat);
        diachincc_phieuxuat = findViewById(R.id.diachincc_phieuxuat);
        sdtncc_phieuxuat = findViewById(R.id.sdtncc_phieuxuat);
        tenncc_phieuxuat = findViewById(R.id.tenncc_phieuxuat);
        tongtien_phieuxuat = findViewById(R.id.tongtien_phieuxuat);
        tongtien_phieuxuat.setText("Tổng tiền: " + Integer.toString(tongtien));

        open_list_dialog_nhanvien_phieuxuat = findViewById(R.id.open_list_dialog_nhanvien_phieuxuat);

        label_btn_themphieuxuat = findViewById(R.id.label_btn_themphieuxuat);

        layout_them_phieuxuat_bottom = findViewById(R.id.layout_them_phieuxuat_bottom);
        btn_close_them_phieuxuat = findViewById(R.id.btn_close_them_phieuxuat);
    }


    //SET...
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    void setInfoKhachHang(String ten,String sdt,String diachi){
        tenncc_phieuxuat.setText("tên: "+ten);
        sdtncc_phieuxuat.setText("sdt: "+sdt);
        diachincc_phieuxuat.setText("địa chỉ: "+diachi);
    }

    void findKhachHang(){
        input_ma_khachhang_phieuxuat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                MaKhachHang = -1;
                if(isEmpty(input_ma_khachhang_phieuxuat) == false){
                    int tempMaKhachHang = Integer.valueOf(input_ma_khachhang_phieuxuat.getText().toString());
                    setInfoKhachHang("...","...","...");
                    Cursor cursor = MainActivity.database.GetData("SELECT * FROM KhachHang WHERE MaKhachHang = "+tempMaKhachHang);

                    while (cursor.moveToNext()){
                        MaKhachHang = cursor.getInt(0);
                        TenKhachHang = cursor.getString(1);
                        setInfoKhachHang(cursor.getString(1),cursor.getString(2),cursor.getString(4));
                    }
                }else {
                    setInfoKhachHang("...","...","...");
                }
            }
        });
    }




    //LAYOUT THEM PHIEU XUAT:
    void openBottomThemPhieuXuat(){
        bottomSheetBehavior = BottomSheetBehavior.from(layout_them_phieuxuat_bottom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setDraggable(false);
        btn_them_phieuxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    //RESET LAYOUT:
                    MaKhachHang = -1;
                    tongtien = 0;
                    //----
                    Makho = Integer.valueOf(dialogListKhos.get(0).getId());
                    TenKho = dialogListKhos.get(0).getName();
                    open_list_dialog_kho_phieuxuat.setText(dialogListKhos.get(0).getName());
                    //----
                    MaNhanVien = Integer.valueOf(dialogListNhanViens.get(0).getId());
                    TenNhanVien = dialogListNhanViens.get(0).getName();
                    open_list_dialog_nhanvien_phieuxuat.setText(dialogListNhanViens.get(0).getName());
                    //----
                    sanPhamListLoad.clear();
                    tongtien_phieuxuat.setText("Tổng tiền: " + Integer.toString(tongtien));
                    setInfoKhachHang("...","...","...");
                    input_ma_khachhang_phieuxuat.setText("");
                    sanPhamAdapter.notifyDataSetChanged();

                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        btn_close_them_phieuxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }




    //GET DATA FOR ALL LIST:
    void setData(){

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

        phieuXuatList = new ArrayList<>();
        cursor = MainActivity.database.GetData("SELECT pn.MaPhieu , c1.TenKho, c2.TenNhanVien, c3.Ten , pn.SoSanPham , pn.TongTien, pn.NgayTaoPhieu " +
                "FROM PhieuXuat pn " +
                "LEFT JOIN NhaKho c1 ON pn.MaKho = c1.MaKho " +
                "LEFT JOIN NhanVien c2 ON pn.MaNhanVien = c2.MaNhanVien " +
                "LEFT JOIN KhachHang c3 ON pn.MaKhachHang = c3.MaKhachHang ");
        while (cursor.moveToNext()){
            phieuXuatList.add(new PhieuXuat(cursor.getInt(0),cursor.getString(6),cursor.getString(1),cursor.getString(2),cursor.getString(3),String.valueOf(cursor.getInt(4)),cursor.getString(5)));
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
                tongtien_phieuxuat.setText("Tổng tiền: " + Integer.toString(tongtien));
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(PhieuXuatActivity.this);
                final EditText input = new EditText(PhieuXuatActivity.this);
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
                                            tongtien_phieuxuat.setText("Tổng tiền: " + Integer.toString(tongtien));
                                            sanPhamAdapter.notifyDataSetChanged();
                                        }else {
                                            Toast.makeText(PhieuXuatActivity.this, "Vui lòng nhập số lượng!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }
                        }).show();
            }
        });
        btn_them_sanpham_phieuxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSanPhamFragment.show(getSupportFragmentManager(),"THEM PHIEU XUAT");
            }
        });
    }
    ///SET DIALOG KHO LIST:
    void setKhoDialogList(){
        Makho = Integer.valueOf(dialogListKhos.get(0).getId());
        TenKho = dialogListKhos.get(0).getName();
        open_list_dialog_kho_phieuxuat.setText(dialogListKhos.get(0).getName());
        DialogListFragment dialogListFragment  = new DialogListFragment(dialogListKhos, new IClickItemDialogList() {
            @Override
            public void clickItem(DialogListItem dialogListItem) {
                open_list_dialog_kho_phieuxuat.setText(dialogListItem.getName());
                Makho = Integer.valueOf(dialogListItem.getId()) ;
                TenKho = dialogListItem.getName();
                Toast.makeText(PhieuXuatActivity.this,"Chọn kho: " + dialogListItem.getName(), Toast.LENGTH_LONG).show();
            }
        });
        open_list_dialog_kho_phieuxuat.setOnClickListener(new View.OnClickListener() {
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
        open_list_dialog_nhanvien_phieuxuat.setText(dialogListNhanViens.get(0).getName());
        DialogListFragment dialogListFragment  = new DialogListFragment(dialogListNhanViens, new IClickItemDialogList() {
            @Override
            public void clickItem(DialogListItem dialogListItem) {
                MaNhanVien = Integer.valueOf(dialogListItem.getId()) ;
                TenNhanVien = dialogListItem.getName();
                open_list_dialog_nhanvien_phieuxuat.setText(dialogListItem.getName());
                Toast.makeText(PhieuXuatActivity.this,"Chọn nhân viên: " + dialogListItem.getName(), Toast.LENGTH_LONG).show();
            }
        });
        open_list_dialog_nhanvien_phieuxuat.setOnClickListener(new View.OnClickListener() {
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