package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api

import com.google.gson.annotations.SerializedName

data class DonHang(
    @SerializedName("_id")
    val _id: String? = null,

    @SerializedName("MaDonHang")
    val maDonHang: String? = null,

    @SerializedName("ManHanVien")
    val maNhanVien: String? = null,

    @SerializedName("MaKhachHang")
    val maKhachHang: String? = null,

    @SerializedName("MaDiaChi")
    val maDiaChi: String? = null,

    @SerializedName("TongSoLuong")
    val tongSoLuong: Int? = null,

    @SerializedName("TongThanhTien")
    val tongThanhTien: Double? = null,

    @SerializedName("TongChietKhau")
    val tongChietKhau: Double? = null,

    @SerializedName("TongThanhToan")
    val tongThanhToan: Double? = null,

    @SerializedName("PTTT")
    val pttt: String? = null,

    @SerializedName("GhiChu")
    val ghiChu: String? = null,

    @SerializedName("TrangThaiDonHang")
    val trangThaiDonHang: String? = null,

    @SerializedName("PhuongThucVanChuyen")
    val phuongThucVanChuyen: String? = null
)
