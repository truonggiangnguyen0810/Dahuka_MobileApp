package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class ChiTietDonHang {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaDonHang")
    private String maDonHang;

    @SerializedName("MaSanPham")
    private String maSanPham;

    @SerializedName("SoLuong")
    private int soLuong;

    @SerializedName("DonGia")
    private double donGia;

    @SerializedName("ChietKhau")
    private double chietKhau;

    @SerializedName("ThanhTien")
    private double thanhTien;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public double getChietKhau() { return chietKhau; }
    public void setChietKhau(double chietKhau) { this.chietKhau = chietKhau; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}
