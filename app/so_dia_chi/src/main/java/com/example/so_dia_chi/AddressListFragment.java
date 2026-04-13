package com.example.so_dia_chi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddressListFragment extends Fragment {

    private RecyclerView rvAddresses;
    private AddressAdapter adapter;
    private List<Address> addressList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_sdc1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAddresses = view.findViewById(R.id.rv_addresses);
        rvAddresses.setLayoutManager(new LinearLayoutManager(getContext()));

        addressList = new ArrayList<>();
        addressList.add(new Address("Trần Thị Thùy Trinh", "(+84) 999 777 666",
                "Đặng Hồi Xuân", "Phường Phú Quý, Quận Ngũ Hành, Đà Nẵng", true));
        addressList.add(new Address("Nguyễn Văn An", "(+84) 123 456 789",
                "Số 12, Ngõ 5", "Phường Hòa Khánh, Quận Liên Chiểu, Đà Nẵng", false));

        adapter = new AddressAdapter(addressList, address -> {
            Bundle args = new Bundle();
            args.putParcelable("address", address);
            Navigation.findNavController(view)
                    .navigate(R.id.action_AddressListFragment_to_AddEditAddressFragment, args);
        });

        rvAddresses.setAdapter(adapter);

        view.findViewById(R.id.btn_add_address).setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_AddressListFragment_to_AddEditAddressFragment);
        });
    }
}
