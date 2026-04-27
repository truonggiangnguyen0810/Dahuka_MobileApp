package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class KhachHang {
    @SerializedName("_id")
    private String _id;

    @SerializedName("id")
    private int id;

    @SerializedName("TenKhachHang")
    private String tenKhachHang;

    @SerializedName("Email")
    private String email;

    @SerializedName("SDT")
    private int sdt;

    @SerializedName("NgaySinh")
    private String ngaySinh;

    @SerializedName("GioiTinh")
    private String gioiTinh;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getSdt() { return sdt; }
    public void setSdt(int sdt) { this.sdt = sdt; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
}
