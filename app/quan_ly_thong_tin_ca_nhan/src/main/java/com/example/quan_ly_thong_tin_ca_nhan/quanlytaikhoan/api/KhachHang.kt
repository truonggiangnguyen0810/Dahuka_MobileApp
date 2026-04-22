package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api

import com.google.gson.annotations.SerializedName

data class KhachHang(
    @SerializedName("_id")
    val _id: String? = null,

    @SerializedName("MaKhachHang")
    val maKhachHang: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("TenKhachHang")
    val tenKhachHang: String? = null,

    @SerializedName("Email")
    val email: String? = null,

    @SerializedName("SDT")
    val sdt: Long? = null,

    @SerializedName("GioiTinh")
    val gioiTinh: String? = null,

    @SerializedName("NgaySinh")
    val ngaySinh: String? = null
)
