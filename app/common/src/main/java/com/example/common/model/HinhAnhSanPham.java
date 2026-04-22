package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class HinhAnhSanPham {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaHinhAnh")
    private String maHinhAnh;

    @SerializedName("DuongDanHinhAnh")
    private String duongDanHinhAnh;

    @SerializedName("MaSP")
    private String maSP;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaHinhAnh() { return maHinhAnh; }
    public void setMaHinhAnh(String maHinhAnh) { this.maHinhAnh = maHinhAnh; }

    public String getDuongDanHinhAnh() { return duongDanHinhAnh; }
    public void setDuongDanHinhAnh(String duongDanHinhAnh) { this.duongDanHinhAnh = duongDanHinhAnh; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
}
