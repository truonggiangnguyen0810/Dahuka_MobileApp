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

        ImageView img = findViewById(R.id.imgMainProduct);
        TextView ten = findViewById(R.id.txtTenSanPham);
        TextView gia = findViewById(R.id.txtGiaSanPham);
        TextView maSP = findViewById(R.id.txtMaSanPham);

        String tenStr = getIntent().getStringExtra("ten");
        String giaStr = getIntent().getStringExtra("gia");
        String maSPStr = getIntent().getStringExtra("maSP");
        String imgUrl = getIntent().getStringExtra("imgUrl");

        ten.setText(tenStr);
        gia.setText(giaStr);
        if (maSP != null) {
            maSP.setText("Mã sản phẩm: " + maSPStr);
        }

        Glide.with(this)
                .load(imgUrl)
                .placeholder(R.drawable.sp_1)
                .error(R.drawable.sp_1)
                .into(img);
    }
}
