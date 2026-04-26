package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class HinhAnhTrangWeb {
    @SerializedName("_id")
    private String _id;

    @SerializedName("LoaiBanner")
    private String loaiBanner;

    @SerializedName("DuongDanHinhAnh")
    private String duongDanHinhAnh;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getLoaiBanner() { return loaiBanner; }
    public void setLoaiBanner(String loaiBanner) { this.loaiBanner = loaiBanner; }

    public String getDuongDanHinhAnh() { return duongDanHinhAnh; }
    public void setDuongDanHinhAnh(String duongDanHinhAnh) { this.duongDanHinhAnh = duongDanHinhAnh; }
}
