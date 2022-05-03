package com.example.qlkhov2.QuanLyKho;

public class DsKho {

    private int MaKho;
    private String TenKho;
    private String DiaChi;
    private String Email;
    private String SDT;
    private String Mota;


    public DsKho(int maKho, String tenKho, String diaChi, String email, String SDT, String mota) {
        MaKho = maKho;
        TenKho = tenKho;
        DiaChi = diaChi;
        Email = email;
        this.SDT = SDT;
        Mota = mota;
    }

    public String getTenKho() {
        return TenKho;
    }

    public void setTenKho(String tenKho) {
        TenKho = tenKho;
    }

    public int getMaKho() {
        return MaKho;
    }

    public void setMaKho(int maKho) {
        MaKho = maKho;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }
}
