package com.example.ql_don_hang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApiService {
    @GET("api/don-hang")
    Call<List<Order>> getOrders();

    @GET("api/chi-tiet-don-hang")
    Call<List<OrderDetail>> getOrderDetails();

    @PUT("api/don-hang/{id}")
    Call<Order> updateOrder(@Path("id") String id, @Body Order order);
}
