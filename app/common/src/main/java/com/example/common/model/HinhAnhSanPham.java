package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class HinhAnhSanPham {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaSP")
    private String maSP;

    @SerializedName("AnhChinh")
    private String anhChinh;

    @SerializedName("AnhPhu1")
    private String anhPhu1;

    @SerializedName("AnhPhu2")
    private String anhPhu2;

    @SerializedName("AnhTinhNang")
    private String anhTinhNang;

    @SerializedName("AnhMoTa")
    private String anhMoTa;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getAnhChinh() { return anhChinh; }
    public void setAnhChinh(String anhChinh) { this.anhChinh = anhChinh; }

    public String getAnhPhu1() { return anhPhu1; }
    public void setAnhPhu1(String anhPhu1) { this.anhPhu1 = anhPhu1; }

    public String getAnhPhu2() { return anhPhu2; }
    public void setAnhPhu2(String anhPhu2) { this.anhPhu2 = anhPhu2; }

    public String getAnhTinhNang() { return anhTinhNang; }
    public void setAnhTinhNang(String anhTinhNang) { this.anhTinhNang = anhTinhNang; }

    public String getAnhMoTa() { return anhMoTa; }
    public void setAnhMoTa(String anhMoTa) { this.anhMoTa = anhMoTa; }
}
