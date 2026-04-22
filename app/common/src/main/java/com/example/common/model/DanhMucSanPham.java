package com.example.common.model;

import com.google.gson.annotations.SerializedName;

public class DanhMucSanPham {
    @SerializedName("_id")
    private String _id;

    @SerializedName("MaDMSP")
    private String maDMSP;

    @SerializedName("TenDM")
    private String tenDM;

    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public String getMaDMSP() { return maDMSP; }
    public void setMaDMSP(String maDMSP) { this.maDMSP = maDMSP; }

    public String getTenDM() { return tenDM; }
    public void setTenDM(String tenDM) { this.tenDM = tenDM; }
}
