package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class KhuyenMai {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaKhuyenMai")
    private String maKhuyenMai;

    @SerializedName("TenKhuyenMai")
    private String tenKhuyenMai;

    @SerializedName("TrangThai")
    private String trangThai;

    @SerializedName("NgayBatDau")
    private String ngayBatDau;

    @SerializedName("NgayKetThuc")
    private String ngayKetThuc;

    @SerializedName("SoTienToiThluc")
    private double soTienToiThieu;

    @SerializedName("HinhThukKhuyenMai")
    private String hinhThucKhuyenMai;

    @SerializedName("GiaTriKhuyenMai")
    private double giaTriKhuyenMai;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }

    public String getTenKhuyenMai() { return tenKhuyenMai; }
    public void setTenKhuyenMai(String tenKhuyenMai) { this.tenKhuyenMai = tenKhuyenMai; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(String ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public String getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(String ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

    public double getSoTienToiThieu() { return soTienToiThieu; }
    public void setSoTienToiThieu(double soTienToiThieu) { this.soTienToiThieu = soTienToiThieu; }

    public String getHinhThucKhuyenMai() { return hinhThucKhuyenMai; }
    public void setHinhThucKhuyenMai(String hinhThucKhuyenMai) { this.hinhThucKhuyenMai = hinhThucKhuyenMai; }

    public double getGiaTriKhuyenMai() { return giaTriKhuyenMai; }
    public void setGiaTriKhuyenMai(double giaTriKhuyenMai) { this.giaTriKhuyenMai = giaTriKhuyenMai; }
}
