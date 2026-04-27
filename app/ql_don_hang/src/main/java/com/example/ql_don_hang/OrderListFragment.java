package com.example.ql_don_hang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ql_don_hang.databinding.ViewDonHangBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment {

    private ViewDonHangBinding binding;
    private OrderAdapter adapter;
    private OrderViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewDonHangBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        binding.rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getOrders().observe(getViewLifecycleOwner(), orders -> {
            adapter = new OrderAdapter(orders, order -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                Navigation.findNavController(view).navigate(R.id.action_OrderListFragment_to_OrderDetailFragment, bundle);
            });
            binding.rvOrders.setAdapter(adapter);
            
            if (orders.isEmpty()) {
                Toast.makeText(getContext(), "Không có đơn hàng nào", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Hiển thị loading nếu cần
        });
        
        viewModel.fetchOrders();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
