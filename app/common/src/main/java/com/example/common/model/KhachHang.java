package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class KhachHang {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaKhachHang")
    private String maKhachHang;

    @SerializedName("id")
    private int id;

    @SerializedName("TenKhachHang")
    private String tenKhachHang;

    @SerializedName("Email")
    private String email;

    @SerializedName("SDT")
    private long sdt;

    @SerializedName("GioiTinh")
    private String gioiTinh;

    @SerializedName("NgaySinh")
    private String ngaySinh;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getSdt() { return sdt; }
    public void setSdt(long sdt) { this.sdt = sdt; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
}
