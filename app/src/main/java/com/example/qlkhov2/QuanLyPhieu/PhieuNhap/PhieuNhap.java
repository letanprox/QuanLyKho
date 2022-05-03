package com.example.qlkhov2.QuanLyPhieu.PhieuNhap;

public class PhieuNhap {
    private int MaPhieu;

    private String NgayNhapKho;
    private String TenKho;
    private String TenNhanVien;
    private String TenNhaCungCap;
    private String SoSanPham;
    private String TongTien;

    public PhieuNhap(int maPhieu, String ngayNhapKho, String tenKho, String tenNhanVien, String tenNhaCungCap, String soSanPham, String tongTien) {
        MaPhieu = maPhieu;
        NgayNhapKho = ngayNhapKho;
        TenKho = tenKho;
        TenNhanVien = tenNhanVien;
        TenNhaCungCap = tenNhaCungCap;
        SoSanPham = soSanPham;
        TongTien = tongTien;
    }

    public int getMaPhieu() {
        return MaPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        MaPhieu = maPhieu;
    }

    public String getNgayNhapKho() {
        return NgayNhapKho;
    }

    public void setNgayNhapKho(String ngayNhapKho) {
        NgayNhapKho = ngayNhapKho;
    }

    public String getTenKho() {
        return TenKho;
    }

    public void setTenKho(String tenKho) {
        TenKho = tenKho;
    }

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        TenNhanVien = tenNhanVien;
    }

    public String getTenNhaCungCap() {
        return TenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        TenNhaCungCap = tenNhaCungCap;
    }

    public String getSoSanPham() {
        return SoSanPham;
    }

    public void setSoSanPham(String soSanPham) {
        SoSanPham = soSanPham;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }
}
