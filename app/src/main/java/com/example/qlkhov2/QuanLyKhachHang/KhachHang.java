package com.example.qlkhov2.QuanLyKhachHang;

public class KhachHang {

    private int Ma;
    private String Ten;
    private String Diachi;
    private int Tuoi;
    private String CCCD;
    private String SDT;

    public KhachHang(int ma, String ten, String diachi, int tuoi, String CCCD, String SDT) {
        Ma = ma;
        Ten = ten;
        Diachi = diachi;
        Tuoi = tuoi;
        this.CCCD = CCCD;
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


    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public int getTuoi() {
        return Tuoi;
    }

    public void setTuoi(int tuoi) {
        Tuoi = tuoi;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}