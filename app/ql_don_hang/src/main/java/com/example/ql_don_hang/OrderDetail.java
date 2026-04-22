package com.example.ql_don_hang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OrderDetail implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("MaDonHang")
    private String maDonHang;

    @SerializedName("TenSanPham")
    private String tenSanPham;

    @SerializedName("SoLuong")
    private int soLuong;

    @SerializedName("DonGia")
    private double donGia;

    @SerializedName("HinhAnh")
    private String hinhAnh;

    public OrderDetail() {}

    public int getId() { return id; }
    public String getMaDonHang() { return maDonHang; }
    public String getTenSanPham() { return tenSanPham; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }
    public String getHinhAnh() { return hinhAnh; }
}
