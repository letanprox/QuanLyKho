package com.example.qlkhov2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }

    public long INSERT_NHAKHO(String ten, String mota,String email, String diachi, String sdt){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO NhaKho VALUES(null, ?, ?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, diachi);
        sqLiteStatement.bindString(3, email);
        sqLiteStatement.bindString(4, sdt);
        sqLiteStatement.bindString(5, mota);

        return  sqLiteStatement.executeInsert();
    }


    public int UPDATE_SANPHAM(int ma,String ten, String donvi,int soluong, String giatien, byte[] hinhanh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE SanPham SET " +
                "TenSanPham = ? ," +
                "DonVi = ?," +
                "Soluong = 0 ," +
                "GiaTien = ? ," +
                "HinhAnh = ? " +
                "WHERE MaSanPham = " + ma ;
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, donvi);
        sqLiteStatement.bindString(3, giatien);
        sqLiteStatement.bindBlob(4, hinhanh);
        return  sqLiteStatement.executeUpdateDelete();
    }

    public long INSERT_SANPHAM(String tensanpha, String donvi, int soluong, String giatien, byte[] hinhanh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO SanPham VALUES(null, ?, ?, "+soluong+",?,?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, tensanpha);
        sqLiteStatement.bindString(2, donvi);
        sqLiteStatement.bindString(3, giatien);
        sqLiteStatement.bindBlob(4, hinhanh);

        return sqLiteStatement.executeInsert();
    }

    public  long INSERT_NHANVIEN(String tennhanvien,String diachi,int tuoi,String cccd,String sdt,int makho){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO NhanVien VALUES(null, ?, ?, "+tuoi+",?,?, "+makho+")";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, tennhanvien);
        sqLiteStatement.bindString(2, diachi);
        sqLiteStatement.bindString(3, cccd);
        sqLiteStatement.bindString(4, sdt);

        return sqLiteStatement.executeInsert();
    }

    public long INSERT_NHACUNGCAP(String ten, String diachi, String email, String sdt){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO NhaCungCap VALUES(null, ?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, diachi);
        sqLiteStatement.bindString(3, email);
        sqLiteStatement.bindString(4, sdt);

        return sqLiteStatement.executeInsert();
    }

    public long INSERT_KHACHHANG(String ten, String diachi, int tuoi, String cccd, String sdt){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO KhachHang VALUES(null, ?, ?, "+tuoi+",?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, diachi);
        sqLiteStatement.bindString(3, cccd);
        sqLiteStatement.bindString(4, sdt);

        return sqLiteStatement.executeInsert();
    }


    public long INSERT_PHIEUNHAP(int makho, int manhacungcap, int manhanvien, int sosanpham, String tongtien, String ngaytaophieu){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO PhieuNhap VALUES(null, "+makho+", "+manhacungcap+", "+manhanvien+","+sosanpham+", ? , ? )";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1,tongtien);
        sqLiteStatement.bindString(2,ngaytaophieu);

        return sqLiteStatement.executeInsert();
    }



    public long INSERT_PHIEUXUAT(int makho, int makhachhang, int manhanvien, int sosanpham, String tongtien, String ngaytaophieu){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO PhieuXuat VALUES(null, "+makho+", "+makhachhang+", "+manhanvien+","+sosanpham+", ? , ? )";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1,tongtien);
        sqLiteStatement.bindString(2,ngaytaophieu);

        return sqLiteStatement.executeInsert();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
