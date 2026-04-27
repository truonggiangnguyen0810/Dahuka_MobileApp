package com.example.so_dia_chi;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.common.model.SoDiaChi;
import com.example.common.model.SoDiaChiRequest;
import com.example.common.network.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressViewModel extends AndroidViewModel {
    private static final String TAG = "AddressViewModel";
    private final MutableLiveData<List<Address>> addresses = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> actionSuccess = new MutableLiveData<>();

    public AddressViewModel(@NonNull Application application) {
        super(application);
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

    public void resetErrorMessage() {
        errorMessage.setValue(null);
    }

    public LiveData<Boolean> getActionSuccess() {
        return actionSuccess;
    }

    public void resetActionSuccess() {
        actionSuccess.setValue(null);
    }

    public void loadAddressesFromApi(int userId) {
        if (userId == -1) {
            Log.w(TAG, "loadAddressesFromApi: userId is -1, skipping API call.");
            addresses.setValue(new ArrayList<>());
            return;
        }

        Log.d(TAG, ">>> START: loadAddressesFromApi for UserId: " + userId);
        isLoading.setValue(true);
        
        try {
            String maKH = String.valueOf(userId);
            Call<List<SoDiaChi>> call = RetrofitClient.getApiService().getSoDiaChiByKhachHang(maKH);
            Log.d(TAG, "Request URL: " + call.request().url().toString());

            call.enqueue(new Callback<List<SoDiaChi>>() {
                @Override
                public void onResponse(Call<List<SoDiaChi>> call, Response<List<SoDiaChi>> response) {
                    isLoading.setValue(false);
                    Log.d(TAG, ">>> ON_RESPONSE received");
                    if (response != null) {
                        Log.d(TAG, "Code: " + response.code());
                        if (response.isSuccessful()) {
                            List<SoDiaChi> body = response.body();
                            if (body != null) {
                                Log.d(TAG, "Body: " + new Gson().toJson(body));
                                mapAndSetAddresses(body);
                            } else {
                                Log.w(TAG, "Response body is NULL");
                                addresses.setValue(new ArrayList<>());
                            }
                        } else {
                            Log.e(TAG, "Response Error: " + response.code());
                            addresses.setValue(new ArrayList<>());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<SoDiaChi>> call, Throwable t) {
                    isLoading.setValue(false);
                    Log.e(TAG, ">>> ON_FAILURE triggered");
                    Log.e(TAG, "Error: " + t.getMessage());
                    addresses.setValue(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            isLoading.setValue(false);
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    private void mapAndSetAddresses(List<SoDiaChi> sdcList) {
        List<Address> mappedList = new ArrayList<>();
        for (SoDiaChi sdc : sdcList) {
            String id = sdc.get_id();
            if (id == null || id.isEmpty()) {
                id = sdc.getMaDiaChi();
            }
            
            StringBuilder fullAddr = new StringBuilder();
            if (sdc.getPhuongXa() != null && !sdc.getPhuongXa().isEmpty()) fullAddr.append(sdc.getPhuongXa());
            if (sdc.getQuanHuyen() != null && !sdc.getQuanHuyen().isEmpty()) {
                if (fullAddr.length() > 0) fullAddr.append(", ");
                fullAddr.append(sdc.getQuanHuyen());
            }
            if (sdc.getThanhPho() != null && !sdc.getThanhPho().isEmpty()) {
                if (fullAddr.length() > 0) fullAddr.append(", ");
                fullAddr.append(sdc.getThanhPho());
            }

            mappedList.add(new Address(
                    id,
                    sdc.getTenNguoiNhan(),
                    sdc.getEmail(),
                    sdc.getDiaChiCuThe(),
                    fullAddr.toString(),
                    sdc.getDiaChiMacDinh() == 1
            ));
        }
        addresses.setValue(mappedList);
    }

    public void postNewAddress(int userId, Address address) {
        if (address.isDefault()) {
            disableOtherDefaults(userId, null, () -> executePostNewAddress(userId, address));
        } else {
            executePostNewAddress(userId, address);
        }
    }

    private void executePostNewAddress(int userId, Address address) {
        Log.d(TAG, ">>> START: postNewAddress for UserId: " + userId);
        SoDiaChiRequest req = createRequestFromAddress(address);
        req.setMaKhachHang(userId);
        
        isLoading.setValue(true);
        RetrofitClient.getApiService().createSoDiaChi(req).enqueue(new Callback<SoDiaChi>() {
            @Override
            public void onResponse(Call<SoDiaChi> call, Response<SoDiaChi> response) {
                if (response != null && response.isSuccessful()) {
                    loadAddressesFromApi(userId);
                    actionSuccess.setValue(true);
                } else {
                    isLoading.setValue(false);
                    errorMessage.setValue("Lỗi khi thêm: " + (response != null ? response.code() : "unknown"));
                }
            }
            @Override
            public void onFailure(Call<SoDiaChi> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void updateAddress(int userId, Address oldAddress, Address newAddress) {
        if (newAddress.isDefault()) {
            disableOtherDefaults(userId, oldAddress.getId(), () -> executeUpdateAddress(userId, oldAddress, newAddress));
        } else {
            executeUpdateAddress(userId, oldAddress, newAddress);
        }
    }

    private void executeUpdateAddress(int userId, Address oldAddress, Address newAddress) {
        Log.d(TAG, ">>> START: updateAddress for ID: " + oldAddress.getId());
        SoDiaChiRequest req = createRequestFromAddress(newAddress);
        req.setMaKhachHang(userId);
        req.setMaDiaChi(oldAddress.getId());

        isLoading.setValue(true);
        RetrofitClient.getApiService().updateSoDiaChi(oldAddress.getId(), req).enqueue(new Callback<SoDiaChi>() {
            @Override
            public void onResponse(Call<SoDiaChi> call, Response<SoDiaChi> response) {
                if (response != null && response.isSuccessful()) {
                    loadAddressesFromApi(userId);
                    actionSuccess.setValue(true);
                } else {
                    isLoading.setValue(false);
                    errorMessage.setValue("Lỗi khi cập nhật");
                }
            }
            @Override
            public void onFailure(Call<SoDiaChi> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối");
            }
        });
    }

    private void disableOtherDefaults(int userId, String excludeId, Runnable onComplete) {
        List<Address> currentList = addresses.getValue();
        if (currentList == null || currentList.isEmpty()) {
            onComplete.run();
            return;
        }

        List<Address> defaultsToDisable = new ArrayList<>();
        for (Address a : currentList) {
            if (a.isDefault() && (excludeId == null || !excludeId.equals(a.getId()))) {
                defaultsToDisable.add(a);
            }
        }

        if (defaultsToDisable.isEmpty()) {
            onComplete.run();
            return;
        }

        disableNext(userId, defaultsToDisable, 0, onComplete);
    }

    private void disableNext(int userId, List<Address> list, int index, Runnable onComplete) {
        if (index >= list.size()) {
            onComplete.run();
            return;
        }

        Address a = list.get(index);
        SoDiaChiRequest req = createRequestFromAddress(a);
        req.setMaKhachHang(userId);
        req.setMaDiaChi(a.getId());
        req.setDiaChiMacDinh(0); // Tắt mặc định

        RetrofitClient.getApiService().updateSoDiaChi(a.getId(), req).enqueue(new Callback<SoDiaChi>() {
            @Override
            public void onResponse(Call<SoDiaChi> call, Response<SoDiaChi> response) {
                disableNext(userId, list, index + 1, onComplete);
            }
            @Override
            public void onFailure(Call<SoDiaChi> call, Throwable t) {
                // Tiếp tục kể cả khi lỗi để đảm bảo chạy onComplete
                disableNext(userId, list, index + 1, onComplete);
            }
        });
    }

    public void deleteAddress(int userId, Address address) {
        if (address.getId() == null) return;
        isLoading.setValue(true);
        RetrofitClient.getApiService().deleteSoDiaChi(address.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadAddressesFromApi(userId);
                    actionSuccess.setValue(true);
                } else {
                    isLoading.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
    }

    private SoDiaChiRequest createRequestFromAddress(Address address) {
        SoDiaChiRequest req = new SoDiaChiRequest();
        req.setTenNguoiNhan(address.getFullName());
        req.setEmail(address.getPhone());
        req.setDiaChiCuThe(address.getDetailAddress());
        
        String fullAddr = address.getFullAddress();
        if (fullAddr != null && fullAddr.contains(",")) {
            String[] parts = fullAddr.split(",");
            if (parts.length >= 3) {
                req.setPhuongXa(parts[0].trim());
                req.setQuanHuyen(parts[1].trim());
                req.setThanhPho(parts[2].trim());
            } else if (parts.length == 2) {
                req.setPhuongXa("");
                req.setQuanHuyen(parts[0].trim());
                req.setThanhPho(parts[1].trim());
            } else {
                req.setPhuongXa("");
                req.setQuanHuyen("");
                req.setThanhPho(parts[0].trim());
            }
        } else {
            req.setPhuongXa("");
            req.setQuanHuyen("");
            req.setThanhPho(fullAddr != null ? fullAddr : "");
        }
        
        req.setDiaChiMacDinh(address.isDefault() ? 1 : 0);
        return req;
    }
}
