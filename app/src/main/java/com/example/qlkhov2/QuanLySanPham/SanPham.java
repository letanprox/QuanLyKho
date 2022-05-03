package com.example.qlkhov2.QuanLySanPham;

public class SanPham {

    private int Ma;
    private String Ten;
    private String Donvi;
    private String UrlAnh;
    private int Soluong;
    private String Giatien;
    private byte[] HinhAnh;

    public SanPham(int ma, String ten, String donvi, String urlAnh, int soluong, String giatien) {
        Ma = ma;
        Ten = ten;
        Donvi = donvi;
        UrlAnh = urlAnh;
        Soluong = soluong;
        Giatien = giatien;
    }

    public SanPham(int ma, String ten, String donvi, int soluong, String giatien, byte[] hinhAnh) {
        Ma = ma;
        Ten = ten;
        Donvi = donvi;
        Soluong = soluong;
        Giatien = giatien;
        HinhAnh = hinhAnh;
    }

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getDonvi() {
        return Donvi;
    }

    public void setDonvi(String donvi) {
        Donvi = donvi;
    }

    public String getUrlAnh() {
        return UrlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        UrlAnh = urlAnh;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public String getGiatien() {
        return Giatien;
    }

    public void setGiatien(String giatien) {
        Giatien = giatien;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
