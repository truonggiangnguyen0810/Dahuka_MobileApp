package com.example.ql_don_hang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ql_don_hang.databinding.ViewDonHangBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment {

    private ViewDonHangBinding binding;
    private OrderAdapter adapter;
    private List<Order> orderList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewDonHangBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupDummyData();

        adapter = new OrderAdapter(orderList, order -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", order);
            Navigation.findNavController(view).navigate(R.id.action_OrderListFragment_to_OrderDetailFragment, bundle);
        });

        binding.rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvOrders.setAdapter(adapter);
    }

    private void setupDummyData() {
        orderList = new ArrayList<>();
        
        Order order1 = new Order("DH20260122008", "26-02-2026 13:58", "An Nguyễn Văn", 11645000, "Placed");
        order1.setAddress("68-Dương Khuê, Thành phố Đà Nẵng, Quận Sơn Trà, Phường Phước Mỹ");
        order1.setPhone("0349434950");
        order1.getProducts().add(new Product("Máy Lọc Nước RO 12 Cấp", "Mutosi MP-S126", 1, 5790000));
        
        Order order2 = new Order("DH20260122009", "22-01-2026 11:28", "An Nguyễn Văn", 11645000, "Cancelled");
        order2.setAddress("68-Dương Khuê, Thành phố Đà Nẵng, Quận Sơn Trà, Phường Phước Mỹ");
        order2.setPhone("0349434950");
        order2.setCancelDate("28/02/2026 16:40");
        order2.setCancelReason("Khác");
        order2.getProducts().add(new Product("Máy Lọc Nước RO 12 Cấp", "Mutosi MP-S126", 1, 5790000));

        orderList.add(order1);
        orderList.add(order2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
