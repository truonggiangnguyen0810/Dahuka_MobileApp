package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface KhachHangApiService {

    @GET("khach-hang")
    suspend fun getAllKhachHang(): Response<List<KhachHang>>

    @GET("khach-hang/{maKH}")
    suspend fun getKhachHangByMa(@Path("maKH") maKH: String): Response<KhachHang>

    @PUT("khach-hang/{maKH}")
    suspend fun updateKhachHang(
        @Path("maKH") maKH: String,
        @Body khachHang: KhachHang
    ): Response<KhachHang>

    @GET("don-hang")
    suspend fun getAllDonHang(): Response<List<DonHang>>

    @GET("chi-tiet-don-hang")
    suspend fun getAllChiTietDonHang(): Response<List<ChiTietDonHang>>

    @GET("san-pham")
    suspend fun getAllSanPham(): Response<List<SanPham>>

    @GET("hinh-anh-san-pham")
    suspend fun getAllHinhAnhSanPham(): Response<List<HinhAnhSanPham>>
}
