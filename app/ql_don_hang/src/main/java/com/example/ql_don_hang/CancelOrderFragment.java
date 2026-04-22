package com.example.ql_don_hang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.ql_don_hang.databinding.ViewHuyDonHangBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CancelOrderFragment extends Fragment {

    private ViewHuyDonHangBinding binding;
    private Order order;
    private OrderViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable("order");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewHuyDonHangBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        if (order != null) {
            displayOrderInfo();
        }

        setupReasonSpinner();

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        binding.btnSendRequest.setOnClickListener(v -> {
            if (order != null) {
                // Update order object locally
                order.setStatus("Cancelled");
                String selectedReason = binding.spinnerReason.getSelectedItem().toString();
                if (selectedReason.equals("Chọn một lý do")) {
                    selectedReason = "Khác";
                }
                order.setCancelReason(selectedReason);
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                order.setCancelDate(sdf.format(new Date()));

                // POST to API via ViewModel
                viewModel.updateOrder(order);
                
                Toast.makeText(getContext(), "Đã gửi yêu cầu hủy đơn hàng", Toast.LENGTH_SHORT).show();

                // Return to detail screen
                Navigation.findNavController(v).navigateUp();
            }
        });
    }

    private void displayOrderInfo() {
        if (order.getProducts() != null && !order.getProducts().isEmpty()) {
            Product product = order.getProducts().get(0);
            binding.tvProductName.setText(product.getName());
            binding.tvProductModel.setText(product.getModel());
            binding.tvProductQuantity.setText("Số lượng: " + product.getQuantity() + " Bộ");
            binding.tvProductPrice.setText(String.format("%,d", product.getPrice()) + "đ");
        }
    }

    private void setupReasonSpinner() {
        String[] reasons = {"Chọn một lý do", "Tôi muốn thay đổi sản phẩm", "Tôi tìm thấy giá rẻ hơn", "Thay đổi ý định", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, reasons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerReason.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
