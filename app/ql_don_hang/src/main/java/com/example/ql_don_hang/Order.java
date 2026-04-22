package com.example.ql_don_hang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("MaDonHang")
    private String maDonHang;

    @SerializedName("MaKhachHang")
    private String maKhachHang;

    @SerializedName("MaDiaChi")
    private String maDiaChi;

    @SerializedName("TongSoLuong")
    private int tongSoLuong;

    @SerializedName("TongThanhTien")
    private double tongThanhTien;

    @SerializedName("TongChietKhau")
    private double tongChietKhau;

    @SerializedName("TongThanhToan")
    private double tongThanhToan;

    @SerializedName("GhiChu")
    private String ghiChu;

    @SerializedName("TrangThaiDonHang")
    private String trangThaiDonHang;

    @SerializedName("PhuongThucVanChuyen")
    private String phuongThucVanChuyen;

    private List<Product> products = new ArrayList<>();
    private String cancelDate;
    private String cancelReason;

    public Order() {}

    public Order(String maDonHang, String trangThaiDonHang, double tongThanhToan) {
        this.maDonHang = maDonHang;
        this.trangThaiDonHang = trangThaiDonHang;
        this.tongThanhToan = tongThanhToan;
    }

    // Getters
    public String getId() { return id; }
    public String getMaDonHang() { return maDonHang; }
    public String getMaKhachHang() { return maKhachHang; }
    public String getMaDiaChi() { return maDiaChi; }
    public int getTongSoLuong() { return tongSoLuong; }
    public double getTongThanhTien() { return tongThanhTien; }
    public double getTongChietKhau() { return tongChietKhau; }
    public double getTongThanhToan() { return tongThanhToan; }
    public String getGhiChu() { return ghiChu; }
    public String getTrangThaiDonHang() { return trangThaiDonHang; }
    public String getPhuongThucVanChuyen() { return phuongThucVanChuyen; }

    // Logic mappings to old names to avoid breaking adapter/fragment
    public String getDate() { return "Chưa cập nhật"; } // API doesn't have date
    public String getCustomerName() { return maKhachHang; }
    public double getTotalAmount() { return tongThanhToan; }
    public String getStatus() { return trangThaiDonHang; }
    public String getAddress() { return maDiaChi; }
    public String getPhone() { return "Chưa cập nhật"; }

    public List<Product> getProducts() { return products; }
    public String getCancelDate() { return cancelDate; }
    public String getCancelReason() { return cancelReason; }

    // Setters
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }
    public void setTrangThaiDonHang(String trangThaiDonHang) { this.trangThaiDonHang = trangThaiDonHang; }
    public void setStatus(String status) { this.trangThaiDonHang = status; }
    public void setTongThanhToan(double tongThanhToan) { this.tongThanhToan = tongThanhToan; }
    public void setProducts(List<Product> products) { this.products = products; }
    public void setCancelDate(String cancelDate) { this.cancelDate = cancelDate; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
}
