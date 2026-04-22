package com.example.so_dia_chi;

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

public class AddressViewModel extends ViewModel {
    private final MutableLiveData<List<Address>> addresses = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(null);
    
    private final AddressApiService apiService;

    public AddressViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://boney-unspoiled-thesis.ngrok-free.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(AddressApiService.class);
        fetchAddresses();
    }

    public LiveData<List<Address>> getAddresses() {
        return addresses;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchAddresses() {
        isLoading.setValue(true);
        apiService.getAddresses().enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    addresses.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi khi tải danh sách: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void addAddress(Address address) {
        apiService.addAddress(address).enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.isSuccessful()) {
                    fetchAddresses(); // Refresh list
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                errorMessage.setValue("Không thể thêm địa chỉ");
            }
        });
    }

    public void updateAddress(Address oldAddress, Address newAddress) {
        apiService.updateAddress(oldAddress.getId(), newAddress).enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.isSuccessful()) {
                    fetchAddresses();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                errorMessage.setValue("Không thể cập nhật địa chỉ");
            }
        });
    }

    public void deleteAddress(Address address) {
        apiService.deleteAddress(address.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchAddresses();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorMessage.setValue("Không thể xóa địa chỉ");
            }
        });
    }
}
