package com.example.ql_don_hang;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.common.UserManager;
import com.example.common.model.DonHang;
import com.example.common.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Order>> orders = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public OrderViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Order>> getOrders() {
        return orders;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchOrders() {
        int userId = UserManager.getUserId(getApplication());
        
        if (userId == -1) {
            Log.e("OrderViewModel", "User chưa đăng nhập");
            return;
        }

        Log.d("OrderViewModel", "Fetching orders for UserId: " + userId);
        isLoading.setValue(true);
        
        // Sử dụng userId dạng String cho @Path parameter của API GET
        // API: don-hang/khach-hang/{maKH}
        RetrofitClient.getApiService().getDonHangByKhachHang(String.valueOf(userId)).enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("OrderViewModel", "Success: found " + response.body().size() + " orders");
                    mapAndSetOrders(response.body());
                } else {
                    Log.d("OrderViewModel", "No orders found for: " + userId + " (Code: " + response.code() + ")");
                    orders.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("OrderViewModel", "API Failure: " + t.getMessage());
                errorMessage.setValue("Lỗi kết nối: " + t.getMessage());
                orders.setValue(new ArrayList<>());
            }
        });
    }

    private void mapAndSetOrders(List<DonHang> dhList) {
        List<Order> mappedOrders = new ArrayList<>();
        for (DonHang dh : dhList) {
            // Mapping dữ liệu từ DonHang (Common) sang Order (Local)
            Order order = new Order(
                    dh.getMaDonHang(),
                    dh.getNgayDatHang() != null ? dh.getNgayDatHang() : "Không rõ ngày",
                    "Đơn hàng #" + dh.getMaDonHang(),
                    (long) dh.getTongThanhToan(),
                    dh.getTrangThaiDonHang() != null ? dh.getTrangThaiDonHang() : "Placed"
            );
            
            order.setAddress(dh.getMaDiaChi());
            order.setCancelDate(dh.getCancelDate());
            order.setCancelReason(dh.getCancelReason());

            // Tạo danh sách sản phẩm ảo hoặc từ chi tiết (nếu có)
            List<Product> products = new ArrayList<>();
            products.add(new Product(
                    "Sản phẩm Dahuka", 
                    "Mã ĐH: " + dh.getMaDonHang(), 
                    dh.getTongSoLuong() > 0 ? dh.getTongSoLuong() : 1, 
                    (long) dh.getTongThanhToan()
            ));
            order.setProducts(products);
            
            mappedOrders.add(order);
        }
        orders.setValue(mappedOrders);
    }

    public void updateOrder(Order order) {
        if (order.getId() == null) return;
        
        isLoading.setValue(true);
        // Bước 1: Lấy dữ liệu hiện tại để đảm bảo không mất thông tin khi update
        RetrofitClient.getApiService().getDonHangByMa(order.getId()).enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DonHang donHang = response.body();
                    donHang.setTrangThaiDonHang(order.getStatus());
                    donHang.setCancelDate(order.getCancelDate());
                    donHang.setCancelReason(order.getCancelReason());

                    // Bước 2: Gửi update
                    RetrofitClient.getApiService().updateDonHang(order.getId(), donHang).enqueue(new Callback<DonHang>() {
                        @Override
                        public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                            isLoading.setValue(false);
                            if (response.isSuccessful()) {
                                fetchOrders();
                            } else {
                                errorMessage.setValue("Cập nhật thất bại: " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<DonHang> call, Throwable t) {
                            isLoading.setValue(false);
                            errorMessage.setValue("Lỗi kết nối: " + t.getMessage());
                        }
                    });
                } else {
                    isLoading.setValue(false);
                    errorMessage.setValue("Không tìm thấy đơn hàng để cập nhật");
                }
            }
            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
