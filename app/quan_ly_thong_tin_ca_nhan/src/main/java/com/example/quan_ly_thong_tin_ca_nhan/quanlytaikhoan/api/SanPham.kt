package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api

import com.google.gson.annotations.SerializedName

data class SanPham(
    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("MaSP")
    val maSP: String? = null,

    @SerializedName("TenSP")
    val tenSP: String? = null,

    @SerializedName("MoTa")
    val moTa: String? = null,

    @SerializedName("MoTaChiTiet")
    val moTaChiTiet: String? = null,

    @SerializedName("LoaiMay")
    val loaiMay: String? = null
)
