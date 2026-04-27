package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import com.example.common.model.ChiTietDonHang
import com.example.common.model.DonHang
import com.example.common.model.HinhAnhSanPham
import com.example.common.model.KhachHang
import com.example.common.model.SanPham
import com.example.common.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface KhachHangApiService {

    @GET("khach-hang")
    fun getAllKhachHang(): Call<List<KhachHang>>

    @GET("khach-hang/{maKH}")
    fun getKhachHangByMa(@Path("maKH") maKH: String): Call<KhachHang>

    @PUT("khach-hang/{id}")
    fun updateKhachHang(
        @Path("id") id: String,
        @Body khachHang: KhachHang
    ): Call<KhachHang>

    @GET("don-hang")
    fun getAllDonHang(): Call<List<DonHang>>

    @GET("chi-tiet-don-hang")
    fun getAllChiTietDonHang(): Call<List<ChiTietDonHang>>

    @GET("san-pham")
    fun getAllSanPham(): Call<List<SanPham>>

    @GET("hinh-anh-san-pham")
    fun getAllHinhAnhSanPham(): Call<List<HinhAnhSanPham>>

    companion object {
        val api: KhachHangApiService by lazy {
            RetrofitClient.getRetrofit().create(KhachHangApiService::class.java)
        }
    }
}
