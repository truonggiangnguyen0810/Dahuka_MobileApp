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
import com.example.common.model.ChangePasswordRequest;
import com.example.common.model.ChangePasswordResponse;
import com.example.common.model.CheckUsernameResponse;
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

    @GET("san-pham/danh-muc/{maDMSP}")
    Call<List<SanPham>> getSanPhamByDanhMuc(@Path("maDMSP") String maDMSP);

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

    @GET("hinh-anh-trang-web/loai-banner/{loai}")
    Call<List<HinhAnhTrangWeb>> getHinhAnhTrangWebByLoai(@Path("loai") String loai);

    @GET("hinh-anh-trang-web/{id}")
    Call<HinhAnhTrangWeb> getHinhAnhTrangWebById(@Path("id") String id);

    @POST("hinh-anh-trang-web")
    Call<HinhAnhTrangWeb> createHinhAnhTrangWeb(@Body HinhAnhTrangWeb hinhAnh);

    @PUT("hinh-anh-trang-web/{id}")
    Call<HinhAnhTrangWeb> updateHinhAnhTrangWeb(@Path("id") String id, @Body HinhAnhTrangWeb hinhAnh);

    @DELETE("hinh-anh-trang-web/{id}")
    Call<Void> deleteHinhAnhTrangWeb(@Path("id") String id);

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
    // Backend dùng MaDonHang (KHÔNG phải MongoDB _id)
    @GET("don-hang")
    Call<List<DonHang>> getAllDonHang();

    @GET("don-hang/khach-hang/{maKH}")
    Call<List<DonHang>> getDonHangByKhachHang(@Path("maKH") String maKH);

    @GET("don-hang/nhan-vien/{maNV}")
    Call<List<DonHang>> getDonHangByNhanVien(@Path("maNV") String maNV);

    @GET("don-hang/{maDonHang}")
    Call<DonHang> getDonHangByMa(@Path("maDonHang") String maDonHang);

    @POST("don-hang")
    Call<DonHang> createDonHang(@Body DonHang donHang);

    @PUT("don-hang/{maDonHang}")
    Call<DonHang> updateDonHang(@Path("maDonHang") String maDonHang, @Body DonHang donHang);

    @DELETE("don-hang/{maDonHang}")
    Call<Void> deleteDonHang(@Path("maDonHang") String maDonHang);

    // ==================== Chi tiết đơn hàng ====================
    // Backend dùng khóa phức hợp {MaDonHang}/{MaSanPham}
    @GET("chi-tiet-don-hang")
    Call<List<ChiTietDonHang>> getAllChiTietDonHang();

    @GET("chi-tiet-don-hang/don-hang/{maDH}")
    Call<List<ChiTietDonHang>> getChiTietDonHangByDonHang(@Path("maDH") String maDH);

    @GET("chi-tiet-don-hang/{maDonHang}/{maSanPham}")
    Call<ChiTietDonHang> getChiTietDonHangById(
            @Path("maDonHang") String maDonHang,
            @Path("maSanPham") String maSanPham
    );

    @POST("chi-tiet-don-hang")
    Call<ChiTietDonHang> createChiTietDonHang(@Body ChiTietDonHang chiTiet);

    @PUT("chi-tiet-don-hang/{maDonHang}/{maSanPham}")
    Call<ChiTietDonHang> updateChiTietDonHang(
            @Path("maDonHang") String maDonHang,
            @Path("maSanPham") String maSanPham,
            @Body ChiTietDonHang chiTiet
    );

    @DELETE("chi-tiet-don-hang/{maDonHang}/{maSanPham}")
    Call<Void> deleteChiTietDonHang(
            @Path("maDonHang") String maDonHang,
            @Path("maSanPham") String maSanPham
    );

    // ==================== Chi tiết giỏ hàng ====================
    // Backend dùng khóa phức hợp {MaKhachHang}/{MaTietPham}
    @GET("chi-tiet-gio-hang")
    Call<List<ChiTietGioHang>> getAllChiTietGioHang();

    @GET("chi-tiet-gio-hang/khach-hang/{maKH}")
    Call<List<ChiTietGioHang>> getChiTietGioHangByKhachHang(@Path("maKH") String maKH);

    @GET("chi-tiet-gio-hang/{maKhachHang}/{maTietPham}")
    Call<ChiTietGioHang> getChiTietGioHangById(
            @Path("maKhachHang") String maKhachHang,
            @Path("maTietPham") String maTietPham
    );

    @POST("chi-tiet-gio-hang")
    Call<ChiTietGioHang> createChiTietGioHang(@Body ChiTietGioHang chiTiet);

    @PUT("chi-tiet-gio-hang/{maKhachHang}/{maTietPham}")
    Call<ChiTietGioHang> updateChiTietGioHang(
            @Path("maKhachHang") String maKhachHang,
            @Path("maTietPham") String maTietPham,
            @Body ChiTietGioHang chiTiet
    );

    @DELETE("chi-tiet-gio-hang/{maKhachHang}/{maTietPham}")
    Call<Void> deleteChiTietGioHang(
            @Path("maKhachHang") String maKhachHang,
            @Path("maTietPham") String maTietPham
    );

    // ==================== Sổ địa chỉ ====================
    @GET("so-dia-chi")
    Call<List<SoDiaChi>> getAllSoDiaChi();

    @GET("so-dia-chi/khach-hang/{maKH}")
    Call<List<SoDiaChi>> getSoDiaChiByKhachHang(@Path("maKH") String maKH);

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

    @GET("auth/check-username/{username}")
    Call<CheckUsernameResponse> checkUsername(@Path("username") String username);

    @POST("auth/login")
    Call<LoginResponse> login(@Body AuthUser user);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body AuthUser user);

    @PUT("auth/change-password/{username}")
    Call<ChangePasswordResponse> changePassword(
            @Path("username") String username,
            @Body ChangePasswordRequest request
    );
}
