package com.example.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * Request body dùng khi POST/PUT - Đã sửa SerializedName thành PascalCase 
 * để khớp chính xác với cấu trúc Database của bạn.
 */
public class SoDiaChiRequest {

    @SerializedName("MaDiaChi")
    private String maDiaChi;

    @SerializedName("MaKhachHang")
    private int maKhachHang;

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

    public String getMaDiaChi() { return maDiaChi; }
    public void setMaDiaChi(String maDiaChi) { this.maDiaChi = maDiaChi; }

    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int maKhachHang) { this.maKhachHang = maKhachHang; }

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
