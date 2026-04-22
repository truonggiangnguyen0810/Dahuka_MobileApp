package com.example.xem_san_pham.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SanPhamApi {
    @GET("api/san-pham")
    Call<List<SanPhamResponse>> getAllSanPham();

    @GET("api/hinh-anh-san-pham")
    Call<List<HinhAnhSanPhamResponse>> getAllHinhAnh();

    @GET("api/chi-tiet-don-hang")
    Call<List<ChiTietDonHangResponse>> getAllChiTiet();
}
