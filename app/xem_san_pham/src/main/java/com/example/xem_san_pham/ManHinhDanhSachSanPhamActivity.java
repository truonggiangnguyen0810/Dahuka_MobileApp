package com.example.xem_san_pham;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xem_san_pham.api.ChiTietDonHangResponse;
import com.example.xem_san_pham.api.HinhAnhSanPhamResponse;
import com.example.xem_san_pham.api.RetrofitClient;
import com.example.xem_san_pham.api.SanPhamApi;
import com.example.xem_san_pham.api.SanPhamResponse;
import com.google.android.material.navigation.NavigationView;

import android.widget.PopupMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhDanhSachSanPhamActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView rv;
    private ProgressBar pb;
    private TextView tvSapXep;
    private final ArrayList<SanPham> listSanPham = new ArrayList<>();
    private final Map<String, String> imageMap = new HashMap<>();
    private final Map<String, String> priceMap = new HashMap<>();
    private DieuHopSanPham adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_san_pham);

        drawerLayout = findViewById(R.id.drawerLayout);
        rv = findViewById(R.id.rvSanPham);
        pb = findViewById(R.id.pbLoading);
        tvSapXep = findViewById(R.id.tvSapXep);

        findViewById(R.id.btnLoc).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        findViewById(R.id.btnSapXep).setOnClickListener(v -> showSortMenu());

        // Setup filter buttons inside drawer
        NavigationView navView = findViewById(R.id.navigationView);
        navView.findViewById(R.id.btnApplyLoc).setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(this, "Đang áp dụng bộ lọc...", Toast.LENGTH_SHORT).show();
        });
        navView.findViewById(R.id.btnHuyLoc).setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new DieuHopSanPham(this, listSanPham);
        rv.setAdapter(adapter);

        loadData();
    }

    private void showSortMenu() {
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.btnSapXep));
        popup.getMenuInflater().inflate(R.menu.menu_sap_xep, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            tvSapXep.setText(item.getTitle());
            Toast.makeText(this, "Sắp xếp theo: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });
        popup.show();
    }

    private void loadData() {
        pb.setVisibility(View.VISIBLE);
        SanPhamApi api = RetrofitClient.getClient().create(SanPhamApi.class);

        // Chain 1: Fetch images
        api.getAllHinhAnh().enqueue(new Callback<List<HinhAnhSanPhamResponse>>() {
            @Override
            public void onResponse(Call<List<HinhAnhSanPhamResponse>> call, Response<List<HinhAnhSanPhamResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (HinhAnhSanPhamResponse img : response.body()) {
                        if (img.getMaSP() != null) {
                            imageMap.put(img.getMaSP(), img.getDuongDanHinhAnh());
                        }
                    }
                }
                fetchPrices(api);
            }

            @Override
            public void onFailure(Call<List<HinhAnhSanPhamResponse>> call, Throwable t) {
                fetchPrices(api);
            }
        });
    }

    private void fetchPrices(SanPhamApi api) {
        api.getAllChiTiet().enqueue(new Callback<List<ChiTietDonHangResponse>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonHangResponse>> call, Response<List<ChiTietDonHangResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DecimalFormat df = new DecimalFormat("#,###đ");
                    for (ChiTietDonHangResponse item : response.body()) {
                        if (item.getMaSanPham() != null && item.getDonGia() > 0) {
                            priceMap.put(item.getMaSanPham(), df.format(item.getDonGia()));
                        }
                    }
                }
                fetchProducts(api);
            }

            @Override
            public void onFailure(Call<List<ChiTietDonHangResponse>> call, Throwable t) {
                fetchProducts(api);
            }
        });
    }

    private void fetchProducts(SanPhamApi api) {
        api.getAllSanPham().enqueue(new Callback<List<SanPhamResponse>>() {
            @Override
            public void onResponse(Call<List<SanPhamResponse>> call, Response<List<SanPhamResponse>> response) {
                pb.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listSanPham.clear();
                    for (SanPhamResponse spRes : response.body()) {
                        String imageUrl = imageMap.get(spRes.getMaSP());
                        String price = priceMap.get(spRes.getMaSP());
                        if (price == null) price = "Liên hệ"; 
                        
                        listSanPham.add(new SanPham(
                            spRes.getTenSP() != null ? spRes.getTenSP() : "Sản phẩm",
                            price,
                            imageUrl
                        ));
                    }
                    adapter.notifyDataSetChanged();
                    Log.d("API", "Displayed " + listSanPham.size() + " products");
                } else {
                    Toast.makeText(ManHinhDanhSachSanPhamActivity.this, "Lỗi khi lấy danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamResponse>> call, Throwable t) {
                pb.setVisibility(View.GONE);
                Toast.makeText(ManHinhDanhSachSanPhamActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
