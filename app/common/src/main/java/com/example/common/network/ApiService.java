package com.example.common.network;

import com.example.common.model.AuthUser;
import com.example.common.model.ChiTietDonHang;
import com.example.common.model.ChiTietGioHang;
import com.example.common.model.ChiTietKhuyenMai;
import com.example.common.model.DanhMucSanPham;
import com.example.common.model.DonHang;
import com.example.common.model.HinhAnhSanPham;
import com.example.common.model.HinhAnhTrangWeb;
import com.example.common.model.KhachHang;
import com.example.common.model.KhuyenMai;
import com.example.common.model.LoginResponse;
import com.example.common.model.NhanVien;
import com.example.common.model.RegisterResponse;
import com.example.common.model.SanPham;
import com.example.common.model.SoDiaChi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // ==================== Sản phẩm ====================
    @GET("san-pham")
    Call<List<SanPham>> getAllSanPham();

    @GET("san-pham/{id}")
    Call<SanPham> getSanPhamById(@Path("id") String id);

    @POST("san-pham")
    Call<SanPham> createSanPham(@Body SanPham sanPham);

    @PUT("san-pham/{id}")
    Call<SanPham> updateSanPham(@Path("id") String id, @Body SanPham sanPham);

    @DELETE("san-pham/{id}")
    Call<Void> deleteSanPham(@Path("id") String id);

    // ==================== Danh mục sản phẩm ====================
    @GET("danh-muc-san-pham")
    Call<List<DanhMucSanPham>> getAllDanhMuc();

    @GET("danh-muc-san-pham/{id}")
    Call<DanhMucSanPham> getDanhMucById(@Path("id") String id);

    @POST("danh-muc-san-pham")
    Call<DanhMucSanPham> createDanhMuc(@Body DanhMucSanPham danhMuc);

    @PUT("danh-muc-san-pham/{id}")
    Call<DanhMucSanPham> updateDanhMuc(@Path("id") String id, @Body DanhMucSanPham danhMuc);

    @DELETE("danh-muc-san-pham/{id}")
    Call<Void> deleteDanhMuc(@Path("id") String id);

    // ==================== Hình ảnh sản phẩm ====================
    @GET("hinh-anh-san-pham")
    Call<List<HinhAnhSanPham>> getAllHinhAnh();

    @GET("hinh-anh-san-pham/ma-sp/{maSP}")
    Call<List<HinhAnhSanPham>> getHinhAnhByMaSP(@Path("maSP") String maSP);

    @GET("hinh-anh-san-pham/{id}")
    Call<HinhAnhSanPham> getHinhAnhById(@Path("id") String id);

    @POST("hinh-anh-san-pham")
    Call<HinhAnhSanPham> createHinhAnh(@Body HinhAnhSanPham hinhAnh);

    @PUT("hinh-anh-san-pham/{id}")
    Call<HinhAnhSanPham> updateHinhAnh(@Path("id") String id, @Body HinhAnhSanPham hinhAnh);

    @DELETE("hinh-anh-san-pham/{id}")
    Call<Void> deleteHinhAnh(@Path("id") String id);

    // ==================== Hình ảnh trang web ====================
    @GET("hinh-anh-trang-web")
    Call<List<HinhAnhTrangWeb>> getAllHinhAnhTrangWeb();

    // ==================== Khách hàng ====================
    @GET("khach-hang")
    Call<List<KhachHang>> getAllKhachHang();

    @GET("khach-hang/{id}")
    Call<KhachHang> getKhachHangById(@Path("id") String id);

    @POST("khach-hang")
    Call<KhachHang> createKhachHang(@Body KhachHang khachHang);

    @PUT("khach-hang/{id}")
    Call<KhachHang> updateKhachHang(@Path("id") String id, @Body KhachHang khachHang);

    @DELETE("khach-hang/{id}")
    Call<Void> deleteKhachHang(@Path("id") String id);

    // ==================== Đơn hàng ====================
    @GET("don-hang")
    Call<List<DonHang>> getAllDonHang();

    @GET("don-hang/{id}")
    Call<DonHang> getDonHangById(@Path("id") String id);

    @POST("don-hang")
    Call<DonHang> createDonHang(@Body DonHang donHang);

    @PUT("don-hang/{id}")
    Call<DonHang> updateDonHang(@Path("id") String id, @Body DonHang donHang);

    @DELETE("don-hang/{id}")
    Call<Void> deleteDonHang(@Path("id") String id);

    // ==================== Chi tiết đơn hàng ====================
    @GET("chi-tiet-don-hang")
    Call<List<ChiTietDonHang>> getAllChiTietDonHang();

    @GET("chi-tiet-don-hang/{id}")
    Call<ChiTietDonHang> getChiTietDonHangById(@Path("id") String id);

    @POST("chi-tiet-don-hang")
    Call<ChiTietDonHang> createChiTietDonHang(@Body ChiTietDonHang chiTiet);

    @PUT("chi-tiet-don-hang/{id}")
    Call<ChiTietDonHang> updateChiTietDonHang(@Path("id") String id, @Body ChiTietDonHang chiTiet);

    @DELETE("chi-tiet-don-hang/{id}")
    Call<Void> deleteChiTietDonHang(@Path("id") String id);

    // ==================== Chi tiết giỏ hàng ====================
    @GET("chi-tiet-gio-hang")
    Call<List<ChiTietGioHang>> getAllChiTietGioHang();

    @GET("chi-tiet-gio-hang/{id}")
    Call<ChiTietGioHang> getChiTietGioHangById(@Path("id") String id);

    @POST("chi-tiet-gio-hang")
    Call<ChiTietGioHang> createChiTietGioHang(@Body ChiTietGioHang chiTiet);

    @PUT("chi-tiet-gio-hang/{id}")
    Call<ChiTietGioHang> updateChiTietGioHang(@Path("id") String id, @Body ChiTietGioHang chiTiet);

    @DELETE("chi-tiet-gio-hang/{id}")
    Call<Void> deleteChiTietGioHang(@Path("id") String id);

    // ==================== Sổ địa chỉ ====================
    @GET("so-dia-chi")
    Call<List<SoDiaChi>> getAllSoDiaChi();

    @GET("so-dia-chi/{id}")
    Call<SoDiaChi> getSoDiaChiById(@Path("id") String id);

    @POST("so-dia-chi")
    Call<SoDiaChi> createSoDiaChi(@Body SoDiaChi soDiaChi);

    @PUT("so-dia-chi/{id}")
    Call<SoDiaChi> updateSoDiaChi(@Path("id") String id, @Body SoDiaChi soDiaChi);

    @DELETE("so-dia-chi/{id}")
    Call<Void> deleteSoDiaChi(@Path("id") String id);

    // ==================== Khuyến mãi ====================
    @GET("khuyen-mai")
    Call<List<KhuyenMai>> getAllKhuyenMai();

    @GET("khuyen-mai/{id}")
    Call<KhuyenMai> getKhuyenMaiById(@Path("id") String id);

    @POST("khuyen-mai")
    Call<KhuyenMai> createKhuyenMai(@Body KhuyenMai khuyenMai);

    @PUT("khuyen-mai/{id}")
    Call<KhuyenMai> updateKhuyenMai(@Path("id") String id, @Body KhuyenMai khuyenMai);

    @DELETE("khuyen-mai/{id}")
    Call<Void> deleteKhuyenMai(@Path("id") String id);

    // ==================== Chi tiết khuyến mãi ====================
    @GET("chi-tiet-khuyen-mai")
    Call<List<ChiTietKhuyenMai>> getAllChiTietKhuyenMai();

    @GET("chi-tiet-khuyen-mai/{id}")
    Call<ChiTietKhuyenMai> getChiTietKhuyenMaiById(@Path("id") String id);

    @POST("chi-tiet-khuyen-mai")
    Call<ChiTietKhuyenMai> createChiTietKhuyenMai(@Body ChiTietKhuyenMai chiTiet);

    @PUT("chi-tiet-khuyen-mai/{id}")
    Call<ChiTietKhuyenMai> updateChiTietKhuyenMai(@Path("id") String id, @Body ChiTietKhuyenMai chiTiet);

    @DELETE("chi-tiet-khuyen-mai/{id}")
    Call<Void> deleteChiTietKhuyenMai(@Path("id") String id);

    // ==================== Nhân viên ====================
    @GET("nhan-vien")
    Call<List<NhanVien>> getAllNhanVien();

    @GET("nhan-vien/{id}")
    Call<NhanVien> getNhanVienById(@Path("id") String id);

    @POST("nhan-vien")
    Call<NhanVien> createNhanVien(@Body NhanVien nhanVien);

    @PUT("nhan-vien/{id}")
    Call<NhanVien> updateNhanVien(@Path("id") String id, @Body NhanVien nhanVien);

    @DELETE("nhan-vien/{id}")
    Call<Void> deleteNhanVien(@Path("id") String id);

    // ==================== Auth ====================
    @GET("auth")
    Call<List<AuthUser>> getAllUsers();

    @POST("auth/login")
    Call<LoginResponse> login(@Body AuthUser user);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body AuthUser user);
}
