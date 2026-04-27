package com.example.so_dia_chi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.UserManager;

import java.util.ArrayList;

public class AddressListFragment extends Fragment {

    private RecyclerView rvAddresses;
    private AddressAdapter adapter;
    private AddressViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_sdc1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);

        rvAddresses = view.findViewById(R.id.rv_addresses);
        rvAddresses.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo adapter một lần duy nhất
        adapter = new AddressAdapter(new ArrayList<>(), address -> {
            Bundle args = new Bundle();
            args.putParcelable("address", address);
            Navigation.findNavController(view)
                    .navigate(R.id.action_AddressListFragment_to_AddEditAddressFragment, args);
        });
        rvAddresses.setAdapter(adapter);

        // Observe LiveData — chỉ gọi updateAddresses, không tạo adapter mới
        viewModel.getAddresses().observe(getViewLifecycleOwner(), addresses -> {
            if (addresses != null) {
                adapter.updateAddresses(addresses);
            }
        });

        view.findViewById(R.id.btn_add_address).setOnClickListener(v ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_AddressListFragment_to_AddEditAddressFragment));

        // Load lần đầu
        int userId = UserManager.getUserId(requireContext());
        viewModel.loadAddressesFromApi(userId);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload mỗi khi quay lại màn hình này (sau khi thêm/sửa/xóa)
        int userId = UserManager.getUserId(requireContext());
        viewModel.loadAddressesFromApi(userId);
    }
}
