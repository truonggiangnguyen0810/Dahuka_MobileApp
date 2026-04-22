package com.example.gio_hang.api

import retrofit2.Call
import retrofit2.http.GET

interface GioHangApi {
    @GET("api/chi-tiet-gio-hang")
    fun getGioHang(): Call<List<GioHangItem>>

    @GET("api/san-pham")
    fun getSanPham(): Call<List<SanPhamItem>>

    @GET("api/chi-tiet-don-hang")
    fun getChiTietDonHang(): Call<List<ChiTietDonHangItem>>
}
