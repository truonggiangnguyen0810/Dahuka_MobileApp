package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class SoDiaChi {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaDiaChi")
    private String maDiaChi;

    @SerializedName("MaKhachHang")
    private String maKhachHang;

    @SerializedName("TenNguoiNhan")
    private String tenNguoiNhan;

    @SerializedName("Email")
    private String email;

    @SerializedName("ThanhPho")
    private String thanhPho;

    @SerializedName("QuanHuyen")
    private String quanHuyen;

    @SerializedName("PhuongXa")
    private String phuongXa;

    @SerializedName("DiaChiCuThe")
    private String diaChiCuThe;

    @SerializedName("DiaChiMacDinh")
    private int diaChiMacDinh;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaDiaChi() { return maDiaChi; }
    public void setMaDiaChi(String maDiaChi) { this.maDiaChi = maDiaChi; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getTenNguoiNhan() { return tenNguoiNhan; }
    public void setTenNguoiNhan(String tenNguoiNhan) { this.tenNguoiNhan = tenNguoiNhan; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getThanhPho() { return thanhPho; }
    public void setThanhPho(String thanhPho) { this.thanhPho = thanhPho; }

    public String getQuanHuyen() { return quanHuyen; }
    public void setQuanHuyen(String quanHuyen) { this.quanHuyen = quanHuyen; }

    public String getPhuongXa() { return phuongXa; }
    public void setPhuongXa(String phuongXa) { this.phuongXa = phuongXa; }

    public String getDiaChiCuThe() { return diaChiCuThe; }
    public void setDiaChiCuThe(String diaChiCuThe) { this.diaChiCuThe = diaChiCuThe; }

    public int getDiaChiMacDinh() { return diaChiMacDinh; }
    public void setDiaChiMacDinh(int diaChiMacDinh) { this.diaChiMacDinh = diaChiMacDinh; }
}
