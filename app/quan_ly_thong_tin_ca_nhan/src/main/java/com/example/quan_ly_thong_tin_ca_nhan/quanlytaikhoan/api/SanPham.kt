package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api

import com.google.gson.annotations.SerializedName

data class SanPham(
    @SerializedName("_id")
    val _id: String? = null,

    @SerializedName("MaSP")
    val maSP: String? = null,

    @SerializedName("MaDMSP")
    val maDMSP: Int? = null,

    @SerializedName("TenSP")
    val tenSP: String? = null,

    @SerializedName("ThongTinSanPham")
    val thongTinSanPham: String? = null,

    @SerializedName("MoTa")
    val moTa: String? = null,

    @SerializedName("MoTaChiTiet")
    val moTaChiTiet: String? = null,

    @SerializedName("TinhNang")
    val tinhNang: String? = null,

    @SerializedName("ThongSo")
    val thongSo: String? = null,

    @SerializedName("CongSuatLoc")
    val congSuatLoc: String? = null,

    @SerializedName("LoaiMay")
    val loaiMay: String? = null,

    @SerializedName("CongNgheLoc")
    val congNgheLoc: String? = null,

    @SerializedName("DungTichBinhChua")
    val dungTichBinhChua: String? = null,

    @SerializedName("NhietDoNuocNong")
    val nhietDoNuocNong: Double? = null,

    @SerializedName("NhietDoNuocLanh")
    val nhietDoNuocLanh: Double? = null,

    @SerializedName("NamRaMat")
    val namRaMat: Int? = null,

    @SerializedName("SoLoiLoc")
    val soLoiLoc: Int? = null,

    @SerializedName("KichThuoc")
    val kichThuoc: String? = null,

    @SerializedName("KhoiLuong")
    val khoiLuong: String? = null,

    @SerializedName("NoiSX")
    val noiSX: String? = null,

    @SerializedName("ThoiGianBH")
    val thoiGianBH: String? = null
)
