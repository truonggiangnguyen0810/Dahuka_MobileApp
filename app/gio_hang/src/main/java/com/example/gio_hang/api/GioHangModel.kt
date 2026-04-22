package com.example.gio_hang.api

import com.google.gson.annotations.SerializedName

data class GioHangItem(
    @SerializedName("_id") val id: String,
    @SerializedName("MaKhachHang") val maKhachHang: String,
    @SerializedName("MaTietPham") val maSanPham: String,
    @SerializedName("SoLuong") val soLuong: Int
)

data class SanPhamItem(
    @SerializedName("MaSP") val maSP: String,
    @SerializedName("TenSP") val tenSP: String,
    @SerializedName("MoTa") val moTa: String = ""
)

data class ChiTietDonHangItem(
    @SerializedName("MaSanPham") val maSanPham: String,
    @SerializedName("DonGia") val donGia: Double
)

// Model kết hợp sau khi join 3 API
data class CartProductModel(
    val maSanPham: String,
    val tenSanPham: String,
    val donGia: Long,
    val soLuong: Int
)
