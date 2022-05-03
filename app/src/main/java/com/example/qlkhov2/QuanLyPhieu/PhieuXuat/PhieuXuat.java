package com.example.qlkhov2.QuanLyPhieu.PhieuXuat;

public class PhieuXuat {
    private int MaPhieu;

    private String NgayXuatKho;
    private String TenKho;
    private String TenNhanVien;
    private String TenKhachHang;
    private String SoSanPham;
    private String TongTien;

    public PhieuXuat(int maPhieu, String ngayXuatKho, String tenKho, String tenNhanVien, String tenKhachHang, String soSanPham, String tongTien) {
        MaPhieu = maPhieu;
        NgayXuatKho = ngayXuatKho;
        TenKho = tenKho;
        TenNhanVien = tenNhanVien;
        TenKhachHang = tenKhachHang;
        SoSanPham = soSanPham;
        TongTien = tongTien;
    }

    public int getMaPhieu() {
        return MaPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        MaPhieu = maPhieu;
    }

    public String getNgayXuatKho() {
        return NgayXuatKho;
    }

    public void setNgayXuatKho(String ngayXuatKho) {
        NgayXuatKho = ngayXuatKho;
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

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
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
