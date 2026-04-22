package com.example.xem_san_pham.api;

import com.google.gson.annotations.SerializedName;

public class HinhAnhSanPhamResponse {
    @SerializedName("MaSP")
    private String maSP;

    @SerializedName("DuongDanHinhAnh")
    private String duongDanHinhAnh;

    @SerializedName("TenHinhAnh")
    private String tenHinhAnh;

    // Getters
    public String getMaSP() { return maSP; }
    public String getDuongDanHinhAnh() { return duongDanHinhAnh; }
    public String getTenHinhAnh() { return tenHinhAnh; }
}
