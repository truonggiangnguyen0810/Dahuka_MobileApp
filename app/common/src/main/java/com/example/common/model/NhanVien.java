package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class NhanVien {
    @SerializedName("_id")
    private String _id;

    @SerializedName("NhaVianVien")
    private String maNhanVien;

    @SerializedName("id")
    private int id;

    @SerializedName("TenNhanVien")
    private String tenNhanVien;

    @SerializedName("ThanhPho")
    private String thanhPho;

    @SerializedName("QuanHuyen")
    private String quanHuyen;

    @SerializedName("PhuongXa")
    private String phuongXa;

    @SerializedName("SDT")
    private long sdt;

    @SerializedName("Email")
    private String email;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenNhanVien() { return tenNhanVien; }
    public void setTenNhanVien(String tenNhanVien) { this.tenNhanVien = tenNhanVien; }

    public String getThanhPho() { return thanhPho; }
    public void setThanhPho(String thanhPho) { this.thanhPho = thanhPho; }

    public String getQuanHuyen() { return quanHuyen; }
    public void setQuanHuyen(String quanHuyen) { this.quanHuyen = quanHuyen; }

    public String getPhuongXa() { return phuongXa; }
    public void setPhuongXa(String phuongXa) { this.phuongXa = phuongXa; }

    public long getSdt() { return sdt; }
    public void setSdt(long sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
