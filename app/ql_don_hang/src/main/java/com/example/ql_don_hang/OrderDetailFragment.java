package com.example.ql_don_hang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ql_don_hang.databinding.ViewXemChiTietBinding;

public class OrderDetailFragment extends Fragment {

    private ViewXemChiTietBinding binding;
    private Order order;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable("order");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewXemChiTietBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (order != null) {
            displayOrderDetails();
        }

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        binding.btnCancelOrder.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", order);
            Navigation.findNavController(v).navigate(R.id.action_OrderDetailFragment_to_CancelOrderFragment, bundle);
        });
    }

    private void displayOrderDetails() {
        binding.tvCustomerName.setText(order.getCustomerName());
        binding.tvCustomerPhone.setText(order.getPhone());
        binding.tvCustomerAddress.setText(order.getAddress());

        if (order.getProducts() != null && !order.getProducts().isEmpty()) {
            Product product = order.getProducts().get(0);
            binding.tvProductName.setText(product.getName());
            binding.tvProductModel.setText(product.getModel());
            binding.tvProductQuantity.setText("Số lượng: " + product.getQuantity() + " Bộ");
            binding.tvProductPrice.setText(String.format("%,d", product.getPrice()) + "đ");
            
            binding.tvTotalProductPrice.setText(String.format("%,d", product.getPrice()) + " đ");
            binding.tvDeposit.setText(String.format("%,d", (long)(product.getPrice() * 0.1)) + " đ");
            binding.tvBalance.setText(String.format("%,d", (long)(product.getPrice() * 0.9)) + " đ");
        }

        if ("Cancelled".equals(order.getStatus())) {
            binding.cardCancelledStatus.setVisibility(View.VISIBLE);
            binding.tvCancelledTime.setText("vào " + order.getCancelDate());
            binding.bottomContainer.setVisibility(View.GONE);
            binding.layoutCancelReason.setVisibility(View.VISIBLE);
            binding.tvCancelReason.setText(order.getCancelReason());
            
            binding.step1.setTextColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            binding.cardCancelledStatus.setVisibility(View.GONE);
            binding.bottomContainer.setVisibility(View.VISIBLE);
            binding.layoutCancelReason.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
