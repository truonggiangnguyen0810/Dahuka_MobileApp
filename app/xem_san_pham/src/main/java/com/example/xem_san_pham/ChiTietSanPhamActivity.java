package com.example.xem_san_pham;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_san_pham);

        ImageView img = findViewById(R.id.imgLon);
        TextView ten = findViewById(R.id.tvTenChiTiet);
        TextView gia = findViewById(R.id.tvGiaChiTiet);

        ten.setText(getIntent().getStringExtra("ten"));
        gia.setText(getIntent().getStringExtra("gia"));
        String url = getIntent().getStringExtra("img");
        
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.sp_1)
            .into(img);
    }
}