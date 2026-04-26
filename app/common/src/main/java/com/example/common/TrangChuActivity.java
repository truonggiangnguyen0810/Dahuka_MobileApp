package com.example.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TrangChuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        setupTaskbar();
        setupNavigationButtons();
    }

    private void setupNavigationButtons() {
        View btnKhamPha = findViewById(R.id.btnKhamPha);
        if (btnKhamPha != null) {
            btnKhamPha.setOnClickListener(v -> navigateToProductList());
        }

        View btnKhamPhaNgay = findViewById(R.id.btnKhamPhaNgay);
        if (btnKhamPhaNgay != null) {
            btnKhamPhaNgay.setOnClickListener(v -> navigateToProductList());
        }

        View btnXemTatCa = findViewById(R.id.btnXemTatCa);
        if (btnXemTatCa != null) {
            btnXemTatCa.setOnClickListener(v -> navigateToProductList());
        }
    }

    private void navigateToProductList() {
        try {
            Class<?> clazz = Class.forName("com.example.xem_san_pham.ManHinhDanhSachSanPhamActivity");
            startActivity(new Intent(this, clazz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
