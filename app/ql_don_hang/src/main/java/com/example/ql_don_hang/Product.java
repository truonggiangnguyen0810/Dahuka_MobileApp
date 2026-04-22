package com.example.ql_don_hang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("_id")
    private String id;

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

    public Product() {}

    // Getters mapping to UI
    public String getName() { return maSanPham; }
    public String getModel() { return "Model: " + maSanPham; }
    public int getQuantity() { return soLuong; }
    public long getPrice() { return (long) donGia; }

    public String getId() { return id; }
    public String getMaDonHang() { return maDonHang; }
    public String getMaSanPham() { return maSanPham; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }
    public double getChietKhau() { return chietKhau; }
    public double getThanhTien() { return thanhTien; }
}
