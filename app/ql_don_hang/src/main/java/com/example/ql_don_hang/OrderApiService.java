package com.example.ql_don_hang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApiService {
    // ==================== ĐƠN HÀNG (DON-HANG) ====================
    
    /**
     * GET - Lấy danh sách tất cả đơn hàng
     */
    @GET("api/don-hang")
    Call<List<Order>> getOrders();

    /**
     * GET - Lấy thông tin chi tiết một đơn hàng theo ID
     */
    @GET("api/don-hang/{id}")
    Call<Order> getOrderById(@Path("id") String id);

    /**
     * POST - Tạo đơn hàng mới
     */
    @POST("api/don-hang")
    Call<Order> createOrder(@Body Order order);

    /**
     * PUT - Cập nhật thông tin đơn hàng
     */
    @PUT("api/don-hang/{id}")
    Call<Order> updateOrder(@Path("id") String id, @Body Order order);

    /**
     * DELETE - Xóa đơn hàng
     */
    @DELETE("api/don-hang/{id}")
    Call<Void> deleteOrder(@Path("id") String id);

    // ==================== CHI TIẾT ĐƠN HÀNG (CHI-TIET-DON-HANG) ====================
    
    /**
     * GET - Lấy danh sách tất cả chi tiết đơn hàng
     */
    @GET("api/chi-tiet-don-hang")
    Call<List<Product>> getAllOrderDetails();

    /**
     * GET - Lấy chi tiết đơn hàng theo ID đơn hàng
     */
    @GET("api/chi-tiet-don-hang/{orderId}")
    Call<List<Product>> getOrderDetails(@Path("orderId") String orderId);

    /**
     * POST - Thêm sản phẩm vào đơn hàng
     */
    @POST("api/chi-tiet-don-hang")
    Call<Product> addOrderDetail(@Body OrderDetailRequest orderDetailRequest);

    /**
     * PUT - Cập nhật chi tiết đơn hàng (số lượng, giá...)
     */
    @PUT("api/chi-tiet-don-hang/{id}")
    Call<Product> updateOrderDetail(@Path("id") String id, @Body Product product);

    /**
     * DELETE - Xóa một sản phẩm khỏi đơn hàng
     */
    @DELETE("api/chi-tiet-don-hang/{id}")
    Call<Void> deleteOrderDetail(@Path("id") String id);
}
