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

public class AddEditAddressFragment extends Fragment {

    private EditText etFullName, etPhoneNumber, etStreet;
    private TextView tvCityDistrictWard, tvToolbarTitle;
    private LinearLayout btnTypeHome, btnTypeOffice;
    private SwitchCompat swDefault;
    private Button btnDelete, btnComplete;
    private ImageButton btnBack;
    private boolean isCitySelected = false;
    private AddressViewModel viewModel;
    private Address existingAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_update_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);

        etFullName = view.findViewById(R.id.et_full_name);
        etPhoneNumber = view.findViewById(R.id.et_phone_number);
        etStreet = view.findViewById(R.id.et_street_building_house);
        tvCityDistrictWard = view.findViewById(R.id.tv_city_district_ward);
        tvToolbarTitle = view.findViewById(R.id.tv_toolbar_title);
        btnTypeHome = view.findViewById(R.id.btn_type_home);
        btnTypeOffice = view.findViewById(R.id.btn_type_office);
        swDefault = view.findViewById(R.id.sw_default);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnComplete = view.findViewById(R.id.btn_complete);
        btnBack = view.findViewById(R.id.btn_back);

        if (getArguments() != null && getArguments().getParcelable("address") != null) {
            existingAddress = getArguments().getParcelable("address");
            if (existingAddress != null) {
                tvToolbarTitle.setText("Sửa địa chỉ");
                etFullName.setText(existingAddress.getFullName());
                etPhoneNumber.setText(existingAddress.getPhone());
                etStreet.setText(existingAddress.getDetailAddress());
                tvCityDistrictWard.setText(existingAddress.getFullAddress());
                swDefault.setChecked(existingAddress.isDefault());
                isCitySelected = true;
                btnDelete.setVisibility(View.VISIBLE);
            }
        } else {
            tvToolbarTitle.setText("Địa chỉ mới");
        }

        btnBack.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        tvCityDistrictWard.setOnClickListener(v -> {
            tvCityDistrictWard.setText("Phường Phú Quý, Quận Ngũ Hành, Đà Nẵng");
            tvCityDistrictWard.setTextColor(Color.BLACK);
            isCitySelected = true;
            validateForm();
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateForm();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        etFullName.addTextChangedListener(watcher);
        etPhoneNumber.addTextChangedListener(watcher);
        etStreet.addTextChangedListener(watcher);

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
            String name = etFullName.getText().toString().trim();
            String phone = etPhoneNumber.getText().toString().trim();
            String street = etStreet.getText().toString().trim();
            String fullAddr = tvCityDistrictWard.getText().toString().trim();
            boolean isDef = swDefault.isChecked();

            if (phone.length() < 10) {
                Toast.makeText(getContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            Address newAddress = new Address(name, phone, street, fullAddr, isDef);

            if (existingAddress != null) {
                viewModel.updateAddress(existingAddress, newAddress);
                Toast.makeText(getContext(), "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.addAddress(newAddress);
                Toast.makeText(getContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
            }
            Navigation.findNavController(view).navigateUp();
        });

        validateForm();
    }

    private void validateForm() {
        String name = etFullName.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();
        String street = etStreet.getText().toString().trim();

        boolean isValid = !name.isEmpty() && !phone.isEmpty() && !street.isEmpty() && isCitySelected;

        btnComplete.setEnabled(isValid);
        if (isValid) {
            btnComplete.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.primary_green));
            btnComplete.setTextColor(Color.WHITE);
        } else {
            btnComplete.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.button_disabled));
            btnComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_disabled));
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

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnConfirmDelete = dialog.findViewById(R.id.btn_confirm_delete);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnConfirmDelete.setOnClickListener(v -> {
            if (existingAddress != null) {
                viewModel.deleteAddress(existingAddress);
            }
            dialog.dismiss();
            Toast.makeText(getContext(), "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigateUp();
        });

        dialog.show();
    }
}
