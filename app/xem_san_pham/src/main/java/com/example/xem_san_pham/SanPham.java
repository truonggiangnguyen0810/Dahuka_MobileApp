package com.example.xem_san_pham;

public class SanPham {
    private String ten;
    private String gia;
    private int hinhAnh;

    public SanPham(String ten, String gia, int hinhAnh) {
        this.ten = ten;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
    }

    public String getTen() {
        return ten;
    }

    public String getGia() {
        return gia;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }
}