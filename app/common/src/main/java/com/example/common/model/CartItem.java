package com.example.common.model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String tenSanPham;
    private long donGia;
    private int soLuong;

    public CartItem() {}

    public CartItem(String tenSanPham, long donGia, int soLuong) {
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public long getDonGia() { return donGia; }
    public void setDonGia(long donGia) { this.donGia = donGia; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}
