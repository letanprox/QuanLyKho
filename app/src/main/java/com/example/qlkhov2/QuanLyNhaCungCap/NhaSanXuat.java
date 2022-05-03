package com.example.qlkhov2.QuanLyNhaCungCap;

public class NhaSanXuat {
    private int Ma;
    private String Ten;
    private String DiaChi;
    private String Email;
    private String SDT;

    public NhaSanXuat(int ma, String ten, String diaChi, String email, String SDT) {
        Ma = ma;
        Ten = ten;
        DiaChi = diaChi;
        Email = email;
        this.SDT = SDT;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
