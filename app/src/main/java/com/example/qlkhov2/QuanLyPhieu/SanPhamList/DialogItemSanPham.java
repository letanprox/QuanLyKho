package com.example.qlkhov2.QuanLyPhieu.SanPhamList;

public class DialogItemSanPham {
    private int Id;
    private String Name;
    private byte[] HinhAnh;

    public DialogItemSanPham(int id, String name, byte[] hinhAnh) {
        Id = id;
        Name = name;
        HinhAnh = hinhAnh;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
