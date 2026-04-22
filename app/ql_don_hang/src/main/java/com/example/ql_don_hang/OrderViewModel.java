package com.example.ql_don_hang;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderViewModel extends ViewModel {
    private final MutableLiveData<List<Order>> orders = new MutableLiveData<>(new ArrayList<>());
    private final OrderApiService apiService;

    public OrderViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://boney-unspoiled-thesis.ngrok-free.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(OrderApiService.class);
        fetchOrders();
    }

    public LiveData<List<Order>> getOrders() {
        return orders;
    }

    public void fetchOrders() {
        apiService.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                // Handle error
            }
        });
    }

    public void updateOrder(Order order) {
        apiService.updateOrder(order.getId(), order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    fetchOrders(); // Refresh list after update
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                // Handle error
            }
        });
    }
}
