package com.example.xem_san_pham;

public class SanPham {
    private String ten;
    private String gia;
    private String hinhAnhUrl;

    public SanPham(String ten, String gia, String hinhAnhUrl) {
        this.ten = ten;
        this.gia = gia;
        this.hinhAnhUrl = hinhAnhUrl;
    }

    public String getTen() {
        return ten;
    }

    public String getGia() {
        return gia;
    }

    public String getHinhAnhUrl() {
        return hinhAnhUrl;
    }
}