package com.example.xem_san_pham.api;

import com.google.gson.annotations.SerializedName;

public class ChiTietDonHangResponse {
    @SerializedName("MaSanPham")
    private String maSanPham;

    @SerializedName("DonGia")
    private double donGia;

    // Getters
    public String getMaSanPham() { return maSanPham; }
    public double getDonGia() { return donGia; }
}
