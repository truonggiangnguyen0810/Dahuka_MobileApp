package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class ChiTietKhuyenMai {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaKhuyenMai")
    private String maKhuyenMai;

    @SerializedName("MaSanPham")
    private String maSanPham;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }
}
