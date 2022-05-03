package com.example.qlkhov2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.qlkhov2.QuanLyKhachHang.KhachHangActivity;
import com.example.qlkhov2.QuanLyKho.NhaKhoActivity;
import com.example.qlkhov2.QuanLyNhaCungCap.NhaSanXuatActivity;
import com.example.qlkhov2.QuanLyNhanVien.NhanVienActivity;
import com.example.qlkhov2.QuanLyPhieu.MenuPhieuActivity;
import com.example.qlkhov2.QuanLySanPham.SanPhamActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;

    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //CREATE DATA:
        STARTDATABASE();

        List<Item_Menu> item_menus = getListMenu();
        this.gridView = (GridView)findViewById(R.id.grid_menu_main);

        gridView.setAdapter(new Grid_Menu_Adapter(this,item_menus));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = gridView.getItemAtPosition(i);
                Item_Menu item_menu = (Item_Menu) o;

                if (item_menu.getId() == 1){
                   Intent intent = new Intent(MainActivity.this, NhaKhoActivity.class);
                   startActivity(intent);
                }

                if (item_menu.getId() == 2){
                    Intent intent = new Intent(MainActivity.this, SanPhamActivity.class);
                    startActivity(intent);
                }


                if (item_menu.getId() == 3){
                    Intent intent = new Intent(MainActivity.this, MenuPhieuActivity.class);
                    startActivity(intent);
                }

                if (item_menu.getId() == 4){
                    Intent intent = new Intent(MainActivity.this, NhaSanXuatActivity.class);
                    startActivity(intent);
                }

                if (item_menu.getId() == 5){
                    Intent intent = new Intent(MainActivity.this, NhanVienActivity.class);
                    startActivity(intent);
                }

                if (item_menu.getId() == 6){
                    Intent intent = new Intent(MainActivity.this, KhachHangActivity.class);
                    startActivity(intent);
                }
            }
        });

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void STARTDATABASE(){
        database = new Database(this,"QUANLYKHO.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS NhaKho(  " +
                "MaKho INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenKho  VARCHAR(150), " +
                "DiaChi VARCHAR(200), " +
                "Email VARCHAR(100), " +
                "SDT VARCHAR(100), " +
                "MoTa VARCHAR(250) )");

        database.QueryData("CREATE TABLE IF NOT EXISTS SanPham(  " +
                "MaSanPham INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenSanPham  VARCHAR(150), " +
                "DonVi VARCHAR(100), " +
                "Soluong INT, " +
                "GiaTien VARCHAR(100), " +
                "HinhAnh BLOB )");


        database.QueryData("CREATE TABLE IF NOT EXISTS NhanVien(  " +
                "MaNhanVien INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenNhanVien  VARCHAR(150), " +
                "DiaChi VARCHAR(150), " +
                "Tuoi INTEGER, " +
                "CCCD VARCHAR(100), " +
                "SDT VARCHAR(100), " +
                "MaKho INTEGER NOT NULL," +
                "FOREIGN KEY (MaKho) " +
                " REFERENCES NhaKho (MaKho) )");


        database.QueryData("CREATE TABLE IF NOT EXISTS NhaCungCap(  " +
                "MaNhaCungCap INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Ten  VARCHAR(150), " +
                "DiaChi VARCHAR(150), " +
                "Email VARCHAR(100), " +
                "SDT VARCHAR(100) )");


//        database.QueryData("DROP TABLE IF EXISTS KhachHang;");

        database.QueryData("CREATE TABLE IF NOT EXISTS KhachHang(  " +
                "MaKhachHang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Ten  VARCHAR(150), " +
                "DiaChi VARCHAR(150), " +
                "Tuoi INT, " +
                "CCCD VARCHAR(100), " +
                "SDT VARCHAR(100) )");




        database.QueryData("CREATE TABLE IF NOT EXISTS PhieuNhap(  " +
                "MaPhieu INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaKho  INT, " +
                "MaNhaCungCap INT, " +
                "MaNhanVien INT, " +
                "SoSanPham INT, " +
                "TongTien VARCHAR(100), " +
                "NgayTaoPhieu  VARCHAR(15) )");

        database.QueryData("CREATE TABLE IF NOT EXISTS SanPhamPhieuNhap(  " +
                "Ma INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaPhieu  INT NOT NULL, " +
                "MaSanPham INT NOT NULL, " +
                "SoLuong INT )");



        database.QueryData("CREATE TABLE IF NOT EXISTS PhieuXuat(  " +
                "MaPhieu INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaKho  INT, " +
                "MaKhachHang INT, " +
                "MaNhanVien INT, " +
                "SoSanPham INT, " +
                "TongTien VARCHAR(100), " +
                "NgayTaoPhieu  VARCHAR(15) )");

        database.QueryData("CREATE TABLE IF NOT EXISTS SanPhamPhieuXuat(  " +
                "Ma INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaPhieu  INT NOT NULL, " +
                "MaSanPham INT NOT NULL, " +
                "SoLuong INT )");

    }

    private List<Item_Menu> getListMenu(){
        List<Item_Menu> list = new ArrayList<>();
        Item_Menu item1 = new Item_Menu(1, "Nhà Kho");
        Item_Menu item2 = new Item_Menu(2, "Quản lý sản phẩm");
        Item_Menu item3 = new Item_Menu(3, "Phiếu nhập xuất");
        Item_Menu item4 = new Item_Menu(4, "Nhà cung cấp");
        Item_Menu item5 = new Item_Menu(5, "Nhân viên");
        Item_Menu item6 = new Item_Menu(6, "Khách hàng");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);
        list.add(item6);
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_topleft, menu);
        return super.onCreateOptionsMenu(menu);
    }
}


