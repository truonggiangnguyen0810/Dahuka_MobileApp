package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api

import com.google.gson.annotations.SerializedName

data class ChiTietDonHang(
    @SerializedName("_id")
    val _id: String? = null,

    @SerializedName("MaDonHang")
    val maDonHang: String? = null,

    @SerializedName("MaSanPham")
    val maSanPham: String? = null,

    @SerializedName("SoLuong")
    val soLuong: Int? = null,

    @SerializedName("DonGia")
    val donGia: Double? = null,

    @SerializedName("ChietKhau")
    val chietKhau: Double? = null,

    @SerializedName("ThanhTien")
    val thanhTien: Double? = null
)
