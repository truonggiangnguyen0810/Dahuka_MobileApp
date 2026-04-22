package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class ChiTietGioHang {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaKhachHang")
    private String maKhachHang;

    @SerializedName("MaTietPham")
    private String maTietPham;

    @SerializedName("SoLuong")
    private int soLuong;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getMaTietPham() { return maTietPham; }
    public void setMaTietPham(String maTietPham) { this.maTietPham = maTietPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}
