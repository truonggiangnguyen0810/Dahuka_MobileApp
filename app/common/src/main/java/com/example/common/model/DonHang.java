package com.example.common.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DonHang {
    @SerializedName(value = "_id", alternate = {"id"})
    private String _id;

    @SerializedName(value = "MaDonHang", alternate = {"maDonHang"})
    private String maDonHang;

    @SerializedName(value = "MaKhachHang", alternate = {"maKhachHang"})
    private int maKhachHang; // Chuyển sang int để khớp với DB

    @SerializedName(value = "MaDiaChi", alternate = {"maDiaChi"})
    private String maDiaChi;

    @SerializedName(value = "TongSoLuong", alternate = {"tongSoLuong"})
    private int tongSoLuong;

    @SerializedName(value = "TongThanhToan", alternate = {"tongThanhToan"})
    private double tongThanhToan;

    @SerializedName(value = "TrangThaiDonHang", alternate = {"trangThaiDonHang"})
    private String trangThaiDonHang;

    @SerializedName(value = "NgayDatHang", alternate = {"ngayDatHang"})
    private String ngayDatHang;

    @SerializedName("cancelDate")
    private String cancelDate;

    @SerializedName("cancelReason")
    private String cancelReason;

    @SerializedName(value = "TongThanhTien", alternate = {"tongThanhTien"})
    private double tongThanhTien;

    @SerializedName(value = "TongChietKhau", alternate = {"tongChietKhau"})
    private double tongChietKhau;

    @SerializedName(value = "GhiChu", alternate = {"ghiChu"})
    private String ghiChu;

    @SerializedName(value = "PhuongThucVanChuyen", alternate = {"phuongThucVanChuyen"})
    private String phuongThucVanChuyen;

    // Getters and Setters
    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getMaDiaChi() { return maDiaChi; }
    public void setMaDiaChi(String maDiaChi) { this.maDiaChi = maDiaChi; }

    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }

    public double getTongThanhToan() { return tongThanhToan; }
    public void setTongThanhToan(double tongThanhToan) { this.tongThanhToan = tongThanhToan; }

    public String getTrangThaiDonHang() { return trangThaiDonHang; }
    public void setTrangThaiDonHang(String trangThaiDonHang) { this.trangThaiDonHang = trangThaiDonHang; }

    public String getNgayDatHang() { return ngayDatHang; }
    public void setNgayDatHang(String ngayDatHang) { this.ngayDatHang = ngayDatHang; }

    public String getCancelDate() { return cancelDate; }
    public void setCancelDate(String cancelDate) { this.cancelDate = cancelDate; }

    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }

    public double getTongThanhTien() { return tongThanhTien; }
    public void setTongThanhTien(double tongThanhTien) { this.tongThanhTien = tongThanhTien; }

    public double getTongChietKhau() { return tongChietKhau; }
    public void setTongChietKhau(double tongChietKhau) { this.tongChietKhau = tongChietKhau; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public String getPhuongThucVanChuyen() { return phuongThucVanChuyen; }
    public void setPhuongThucVanChuyen(String phuongThucVanChuyen) { this.phuongThucVanChuyen = phuongThucVanChuyen; }
}
