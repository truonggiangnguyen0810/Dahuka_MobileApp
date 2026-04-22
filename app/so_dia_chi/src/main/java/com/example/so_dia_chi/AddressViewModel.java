package com.example.so_dia_chi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddressViewModel extends ViewModel {
    private final MutableLiveData<List<Address>> addresses = new MutableLiveData<>(new ArrayList<>());

    public AddressViewModel() {
        List<Address> initialList = new ArrayList<>();
        initialList.add(new Address("Trần Thị Thùy Trinh", "(+84) 999 777 666",
                "Đặng Hồi Xuân", "Phường Phú Quý, Quận Ngũ Hành, Đà Nẵng", true));
        initialList.add(new Address("Nguyễn Văn An", "(+84) 123 456 789",
                "Số 12, Ngõ 5", "Phường Hòa Khánh, Quận Liên Chiểu, Đà Nẵng", false));
        addresses.setValue(initialList);
    }

    public LiveData<List<Address>> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        List<Address> currentList = addresses.getValue();
        if (currentList != null) {
            List<Address> newList = new ArrayList<>(currentList);
            if (address.isDefault()) {
                clearDefaults(newList);
            }
            newList.add(address);
            addresses.setValue(newList);
        }
    }

    public void updateAddress(Address oldAddress, Address newAddress) {
        List<Address> currentList = addresses.getValue();
        if (currentList != null) {
            List<Address> newList = new ArrayList<>(currentList);
            int index = -1;
            for (int i = 0; i < newList.size(); i++) {
                if (newList.get(i).getPhone().equals(oldAddress.getPhone()) && 
                    newList.get(i).getFullName().equals(oldAddress.getFullName())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                if (newAddress.isDefault()) {
                    clearDefaults(newList);
                }
                newList.set(index, newAddress);
                addresses.setValue(newList);
            }
        }
    }

    public void deleteAddress(Address address) {
        List<Address> currentList = addresses.getValue();
        if (currentList != null) {
            List<Address> newList = new ArrayList<>(currentList);
            newList.removeIf(a -> a.getPhone().equals(address.getPhone()) && 
                                 a.getFullName().equals(address.getFullName()));
            addresses.setValue(newList);
        }
    }

    private void clearDefaults(List<Address> list) {
        for (Address a : list) {
            a.setDefault(false);
        }
    }
}
