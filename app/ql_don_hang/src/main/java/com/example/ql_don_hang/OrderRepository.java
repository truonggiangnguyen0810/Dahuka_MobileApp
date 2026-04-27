package com.example.ql_don_hang;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class để quản lý các API calls cho đơn hàng
 * Cung cấp các methods tiện lợi với callback để xử lý response
 */
public class OrderRepository {
    private static final String TAG = "OrderRepository";
    private final OrderApiService apiService;

    public OrderRepository() {
        this.apiService = RetrofitClient.getClient().create(OrderApiService.class);
    }

    // ==================== ĐƠN HÀNG (DON-HANG) ====================

    /**
     * Lấy danh sách tất cả đơn hàng
     */
    public void getAllOrders(final OrderCallback<List<Order>> callback) {
        apiService.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                Log.e(TAG, "getAllOrders failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Lấy thông tin chi tiết một đơn hàng theo ID
     */
    public void getOrderById(String orderId, final OrderCallback<Order> callback) {
        apiService.getOrderById(orderId).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                Log.e(TAG, "getOrderById failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Tạo đơn hàng mới
     */
    public void createOrder(Order order, final OrderCallback<Order> callback) {
        apiService.createOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi tạo đơn hàng: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                Log.e(TAG, "createOrder failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Cập nhật thông tin đơn hàng
     */
    public void updateOrder(String orderId, Order order, final OrderCallback<Order> callback) {
        apiService.updateOrder(orderId, order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi cập nhật: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                Log.e(TAG, "updateOrder failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Xóa đơn hàng
     */
    public void deleteOrder(String orderId, final OrderCallback<Void> callback) {
        apiService.deleteOrder(orderId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Lỗi xóa đơn hàng: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "deleteOrder failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // ==================== CHI TIẾT ĐƠN HÀNG (CHI-TIET-DON-HANG) ====================

    /**
     * Lấy danh sách tất cả chi tiết đơn hàng
     */
    public void getAllOrderDetails(final OrderCallback<List<Product>> callback) {
        apiService.getAllOrderDetails().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e(TAG, "getAllOrderDetails failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Lấy chi tiết đơn hàng theo ID đơn hàng
     */
    public void getOrderDetails(String orderId, final OrderCallback<List<Product>> callback) {
        apiService.getOrderDetails(orderId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e(TAG, "getOrderDetails failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Thêm sản phẩm vào đơn hàng
     */
    public void addOrderDetail(OrderDetailRequest request, final OrderCallback<Product> callback) {
        apiService.addOrderDetail(request).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi thêm sản phẩm: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                Log.e(TAG, "addOrderDetail failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Cập nhật chi tiết đơn hàng
     */
    public void updateOrderDetail(String detailId, Product product, final OrderCallback<Product> callback) {
        apiService.updateOrderDetail(detailId, product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi cập nhật: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                Log.e(TAG, "updateOrderDetail failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    /**
     * Xóa sản phẩm khỏi đơn hàng
     */
    public void deleteOrderDetail(String detailId, final OrderCallback<Void> callback) {
        apiService.deleteOrderDetail(detailId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Lỗi xóa sản phẩm: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "deleteOrderDetail failed", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // ==================== CALLBACK INTERFACE ====================

    /**
     * Interface callback để xử lý response từ API
     */
    public interface OrderCallback<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }
}
