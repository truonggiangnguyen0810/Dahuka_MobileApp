package com.example.gio_hang.api

import com.example.common.model.ChiTietDonHang
import com.example.common.model.ChiTietGioHang
import com.example.common.model.SanPham
import com.example.common.network.RetrofitClient
import retrofit2.Call
import retrofit2.http.GET

interface GioHangApi {

    @GET("chi-tiet-gio-hang")
    fun getGioHang(): Call<List<ChiTietGioHang>>

    @GET("san-pham")
    fun getSanPham(): Call<List<SanPham>>

    @GET("chi-tiet-don-hang")
    fun getChiTietDonHang(): Call<List<ChiTietDonHang>>

    companion object {
        val api: GioHangApi by lazy {
            RetrofitClient.getRetrofit().create(GioHangApi::class.java)
        }
    }
}
