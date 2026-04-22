package com.example.xem_san_pham.api;

import com.google.gson.annotations.SerializedName;

public class SanPhamResponse {
    @SerializedName("MaSP")
    private String maSP;

    @SerializedName("TenSP")
    private String tenSP;

    @SerializedName("MoTa")
    private String moTa;

    // Getters
    public String getMaSP() { return maSP; }
    public String getTenSP() { return tenSP; }
    public String getMoTa() { return moTa; }
}
