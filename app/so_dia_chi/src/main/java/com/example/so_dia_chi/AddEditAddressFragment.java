package com.example.so_dia_chi;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AddEditAddressFragment extends Fragment {

    private EditText etFullName, etPhoneNumber, etStreet;
    private TextView tvCityDistrictWard;
    private LinearLayout btnTypeHome, btnTypeOffice;
    private SwitchCompat swDefault;
    private Button btnDelete, btnComplete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_update_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFullName = view.findViewById(R.id.et_full_name);
        etPhoneNumber = view.findViewById(R.id.et_phone_number);
        etStreet = view.findViewById(R.id.et_street_building_house);
        tvCityDistrictWard = view.findViewById(R.id.tv_city_district_ward);
        btnTypeHome = view.findViewById(R.id.btn_type_home);
        btnTypeOffice = view.findViewById(R.id.btn_type_office);
        swDefault = view.findViewById(R.id.sw_default);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnComplete = view.findViewById(R.id.btn_complete);

        if (getArguments() != null && getArguments().getParcelable("address") != null) {
            Address address = getArguments().getParcelable("address");
            if (address != null) {
                etFullName.setText(address.getFullName());
                etPhoneNumber.setText(address.getPhone());
                etStreet.setText(address.getDetailAddress());
                btnDelete.setVisibility(View.VISIBLE);
            }
        }

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
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigateUp();
        });
    }

    private void showDeleteDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_accept_xoa);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnConfirmDelete = dialog.findViewById(R.id.btn_confirm_delete);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnConfirmDelete.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(getContext(), "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigateUp();
        });

        dialog.show();
    }
}
