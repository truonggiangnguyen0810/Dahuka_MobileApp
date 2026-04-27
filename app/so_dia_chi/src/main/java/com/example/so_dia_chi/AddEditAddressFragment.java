package com.example.so_dia_chi;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.common.UserManager;

public class AddEditAddressFragment extends Fragment {

    private EditText etFullName, etPhoneNumber, etStreet, etCityDistrictWard;
    private TextView tvToolbarTitle;
    private LinearLayout btnTypeHome, btnTypeOffice;
    private SwitchCompat swDefault;
    private Button btnDelete, btnComplete;
    private ImageButton btnBack;
    private AddressViewModel viewModel;
    private Address existingAddress;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_update_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);
        userId = UserManager.getUserId(requireContext());

        etFullName         = view.findViewById(R.id.et_full_name);
        etPhoneNumber      = view.findViewById(R.id.et_phone_number);
        etStreet           = view.findViewById(R.id.et_street_building_house);
        etCityDistrictWard = view.findViewById(R.id.et_city_district_ward);
        tvToolbarTitle     = view.findViewById(R.id.tv_toolbar_title);
        btnTypeHome        = view.findViewById(R.id.btn_type_home);
        btnTypeOffice      = view.findViewById(R.id.btn_type_office);
        swDefault          = view.findViewById(R.id.sw_default);
        btnDelete          = view.findViewById(R.id.btn_delete);
        btnComplete        = view.findViewById(R.id.btn_complete);
        btnBack            = view.findViewById(R.id.btn_back);

        // Navigate back khi API thành công
        viewModel.getActionSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                viewModel.resetActionSuccess();
                if (getView() != null) {
                    Navigation.findNavController(view).navigateUp();
                }
            }
        });

        // Hiển thị lỗi
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                viewModel.resetErrorMessage();
            }
        });

        // Loading state
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), loading -> {
            if (loading != null) {
                btnComplete.setEnabled(!loading);
                btnComplete.setText(loading ? "Đang xử lý..." : "Hoàn thành");
            }
        });

        // Điền dữ liệu nếu đang sửa
        if (getArguments() != null) {
            existingAddress = getArguments().getParcelable("address");
        }
        if (existingAddress != null) {
            tvToolbarTitle.setText("Sửa địa chỉ");
            etFullName.setText(existingAddress.getFullName());
            etPhoneNumber.setText(existingAddress.getPhone());
            etStreet.setText(existingAddress.getDetailAddress());
            etCityDistrictWard.setText(existingAddress.getFullAddress());
            swDefault.setChecked(existingAddress.isDefault());
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            tvToolbarTitle.setText("Địa chỉ mới");
            btnDelete.setVisibility(View.GONE);
        }

        btnBack.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence s, int i, int i1, int i2) { validateForm(); }
            @Override public void afterTextChanged(Editable s) {}
        };
        etFullName.addTextChangedListener(watcher);
        etPhoneNumber.addTextChangedListener(watcher);
        etStreet.addTextChangedListener(watcher);
        etCityDistrictWard.addTextChangedListener(watcher);

        btnTypeHome.setOnClickListener(v -> {
            btnTypeHome.setBackgroundResource(R.drawable.bg_type_selected);
            btnTypeOffice.setBackgroundResource(R.drawable.bg_type_unselected);
        });
        btnTypeOffice.setOnClickListener(v -> {
            btnTypeOffice.setBackgroundResource(R.drawable.bg_type_selected);
            btnTypeHome.setBackgroundResource(R.drawable.bg_type_unselected);
        });

        btnDelete.setOnClickListener(v -> showDeleteDialog());

        btnComplete.setOnClickListener(v -> {
            String name     = etFullName.getText().toString().trim();
            String phone    = etPhoneNumber.getText().toString().trim();
            String street   = etStreet.getText().toString().trim();
            String fullAddr = etCityDistrictWard.getText().toString().trim();
            boolean isDef   = swDefault.isChecked();

            if (phone.length() < 10) {
                Toast.makeText(getContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            Address newAddress = new Address(name, phone, street, fullAddr, isDef);

            if (existingAddress != null) {
                viewModel.updateAddress(userId, existingAddress, newAddress);
            } else {
                viewModel.postNewAddress(userId, newAddress);
            }
            // Không navigate ở đây — chờ actionSuccess observer
        });

        validateForm();
    }

    private void validateForm() {
        String name   = etFullName.getText().toString().trim();
        String phone  = etPhoneNumber.getText().toString().trim();
        String street = etStreet.getText().toString().trim();
        String city   = etCityDistrictWard.getText().toString().trim();

        boolean valid = !name.isEmpty() && !phone.isEmpty() && !street.isEmpty() && !city.isEmpty();
        btnComplete.setEnabled(valid);
        if (valid) {
            btnComplete.setBackgroundTintList(
                    ContextCompat.getColorStateList(requireContext(), R.color.primary_green));
            btnComplete.setTextColor(Color.WHITE);
        } else {
            btnComplete.setBackgroundTintList(
                    ContextCompat.getColorStateList(requireContext(), R.color.button_disabled));
            btnComplete.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.text_disabled));
        }
    }

    private void showDeleteDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_accept_xoa);
        dialog.setCancelable(true);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        Button btnCancel        = dialog.findViewById(R.id.btn_cancel);
        Button btnConfirmDelete = dialog.findViewById(R.id.btn_confirm_delete);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnConfirmDelete.setOnClickListener(v -> {
            if (existingAddress != null) {
                viewModel.deleteAddress(userId, existingAddress);
            }
            dialog.dismiss();
            Navigation.findNavController(requireView()).navigateUp();
        });

        dialog.show();
    }
}
