package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api

import com.google.gson.annotations.SerializedName

data class HinhAnhSanPham(
    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("MaHinhAnh")
    val maHinhAnh: String? = null,

    @SerializedName("DuongDanHinhAnh")
    val duongDanHinhAnh: String? = null,

    @SerializedName("MaSP")
    val maSP: String? = null,

    @SerializedName("TenHinhAnh")
    val tenHinhAnh: String? = null
)
