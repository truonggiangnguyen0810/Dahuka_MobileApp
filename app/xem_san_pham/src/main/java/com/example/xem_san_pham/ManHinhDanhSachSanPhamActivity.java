package com.example.xem_san_pham;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManHinhDanhSachSanPhamActivity extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_san_pham);

        rv = findViewById(R.id.rvSanPham);

        ArrayList<SanPham> list = new ArrayList<>();
        list.add(new SanPham("Máy lọc nước 1", "4,500,000đ", R.drawable.sp_1));
        list.add(new SanPham("Máy lọc nước 2", "6,790,000đ", R.drawable.sp_1));
        list.add(new SanPham("Máy lọc nước 3", "5,790,000đ", R.drawable.sp_1));
        list.add(new SanPham("Máy lọc nước 4", "7,200,000đ", R.drawable.sp_1));

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(new DieuHopSanPham(this, list));
    }
}