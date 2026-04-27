package com.example.common.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonHang {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaDonHang")
    private String maDonHang;

    @SerializedName("ManHanVien")
    private String maNhanVien;

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

    @SerializedName("cancelDate")
    private String cancelDate;

    @SerializedName("cancelReason")
    private String cancelReason;

    @SerializedName("products")
    private List<Object> products;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getMaDiaChi() { return maDiaChi; }
    public void setMaDiaChi(String maDiaChi) { this.maDiaChi = maDiaChi; }

    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }

    public double getTongThanhTien() { return tongThanhTien; }
    public void setTongThanhTien(double tongThanhTien) { this.tongThanhTien = tongThanhTien; }

    public double getTongChietKhau() { return tongChietKhau; }
    public void setTongChietKhau(double tongChietKhau) { this.tongChietKhau = tongChietKhau; }

    public double getTongThanhToan() { return tongThanhToan; }
    public void setTongThanhToan(double tongThanhToan) { this.tongThanhToan = tongThanhToan; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public String getTrangThaiDonHang() { return trangThaiDonHang; }
    public void setTrangThaiDonHang(String trangThaiDonHang) { this.trangThaiDonHang = trangThaiDonHang; }

    public String getPhuongThucVanChuyen() { return phuongThucVanChuyen; }
    public void setPhuongThucVanChuyen(String phuongThucVanChuyen) { this.phuongThucVanChuyen = phuongThucVanChuyen; }

    public String getCancelDate() { return cancelDate; }
    public void setCancelDate(String cancelDate) { this.cancelDate = cancelDate; }

    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }

    public List<Object> getProducts() { return products; }
    public void setProducts(List<Object> products) { this.products = products; }
}
