package com.example.xem_san_pham;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.HinhAnhSanPham;
import com.example.common.network.ApiService;
import com.example.common.network.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhDanhSachSanPhamActivity extends AppCompatActivity {

    RecyclerView rv;
    FrameLayout btnLoc;
    LinearLayout panelLoc;
    LinearLayout contentMain;
    View overlayDim;
    boolean panelDangMo = false;

    private static final String TAG = "DanhSachSP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_san_pham);

        rv = findViewById(R.id.rvSanPham);
        btnLoc = findViewById(R.id.btnLoc);
        panelLoc = findViewById(R.id.panelLoc);
        contentMain = findViewById(R.id.contentMain);
        overlayDim = findViewById(R.id.overlayDim);

        rv.setLayoutManager(new GridLayoutManager(this, 2));

        btnLoc.setOnClickListener(v -> moPanelLoc());

        overlayDim.setOnClickListener(v -> dongPanelLoc());

        TextView btnHuyLoc = findViewById(R.id.btnHuyLoc);
        if (btnHuyLoc != null) {
            btnHuyLoc.setOnClickListener(v -> dongPanelLoc());
        }

        TextView btnLocSanPham = findViewById(R.id.btnLocSanPham);
        if (btnLocSanPham != null) {
            btnLocSanPham.setOnClickListener(v -> dongPanelLoc());
        }

        View btnSapXep = findViewById(R.id.btnSapXep);
        LinearLayout layoutSortOptions = findViewById(R.id.layoutSortOptions);
        TextView tvSortLabel = findViewById(R.id.tvSortLabel);

        btnSapXep.setOnClickListener(v -> {
            if (layoutSortOptions.getVisibility() == View.GONE) {
                layoutSortOptions.setVisibility(View.VISIBLE);
            } else {
                layoutSortOptions.setVisibility(View.GONE);
            }
        });

        View.OnClickListener chonSapXep = v -> {
            TextView option = (TextView) v;
            tvSortLabel.setText(option.getText());
            layoutSortOptions.setVisibility(View.GONE);
        };

        findViewById(R.id.optionMoiNhat).setOnClickListener(chonSapXep);
        findViewById(R.id.optionBanChay).setOnClickListener(chonSapXep);
        findViewById(R.id.optionGiaTang).setOnClickListener(chonSapXep);
        findViewById(R.id.optionGiaGiam).setOnClickListener(chonSapXep);

        layDuLieuTuApi();
    }

    private void layDuLieuTuApi() {
        ApiService apiService = RetrofitClient.getApiService();

        apiService.getAllSanPham().enqueue(new Callback<List<com.example.common.model.SanPham>>() {
            @Override
            public void onResponse(Call<List<com.example.common.model.SanPham>> call, Response<List<com.example.common.model.SanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<com.example.common.model.SanPham> dsSanPham = response.body();
                    Log.d(TAG, "Lấy được " + dsSanPham.size() + " sản phẩm từ API");

                    Map<String, String> mapHinhAnh = new HashMap<>();
                    for (com.example.common.model.SanPham sp : dsSanPham) {
                        mapHinhAnh.put(sp.getMaSP(), null);
                    }

                    apiService.getAllHinhAnh().enqueue(new Callback<List<HinhAnhSanPham>>() {
                        @Override
                        public void onResponse(Call<List<HinhAnhSanPham>> call2, Response<List<HinhAnhSanPham>> response2) {
                            if (response2.isSuccessful() && response2.body() != null) {
                                for (HinhAnhSanPham ha : response2.body()) {
                                    mapHinhAnh.put(ha.getMaSP(), ha.getDuongDanHinhAnh());
                                }
                            }

                            ArrayList<SanPham> list = new ArrayList<>();
                            for (com.example.common.model.SanPham sp : dsSanPham) {
                                String url = mapHinhAnh.get(sp.getMaSP());
                                String gia = sp.getThongTinSanPham() != null ? sp.getThongTinSanPham() : "Liên hệ";
                                list.add(new SanPham(sp.getMaSP(), sp.getTenSP(), gia, url));
                            }

                            rv.setAdapter(new DieuHopSanPham(ManHinhDanhSachSanPhamActivity.this, list));
                        }

                        @Override
                        public void onFailure(Call<List<HinhAnhSanPham>> call2, Throwable t) {
                            Log.e(TAG, "Lỗi tải hình ảnh: " + t.getMessage());
                            ArrayList<SanPham> list = new ArrayList<>();
                            for (com.example.common.model.SanPham sp : dsSanPham) {
                                String gia = sp.getThongTinSanPham() != null ? sp.getThongTinSanPham() : "Liên hệ";
                                list.add(new SanPham(sp.getMaSP(), sp.getTenSP(), gia, null));
                            }
                            rv.setAdapter(new DieuHopSanPham(ManHinhDanhSachSanPhamActivity.this, list));
                        }
                    });

                } else {
                    Log.e(TAG, "API trả lỗi: " + response.code());
                    Toast.makeText(ManHinhDanhSachSanPhamActivity.this, "Lỗi tải dữ liệu: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<com.example.common.model.SanPham>> call, Throwable t) {
                Log.e(TAG, "Kết nối thất bại: " + t.getMessage());
                Toast.makeText(ManHinhDanhSachSanPhamActivity.this, "Không thể kết nối server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moPanelLoc() {
        if (panelDangMo) return;
        panelDangMo = true;
        panelLoc.setVisibility(View.VISIBLE);
        overlayDim.setVisibility(View.VISIBLE);
        lamMoNen();
    }

    private void dongPanelLoc() {
        if (!panelDangMo) return;
        panelDangMo = false;
        panelLoc.setVisibility(View.GONE);
        overlayDim.setVisibility(View.GONE);
        boMoNen();
    }

    private void lamMoNen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            contentMain.setRenderEffect(
                    RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.CLAMP)
            );
        }
    }

    private void boMoNen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            contentMain.setRenderEffect(null);
        }
    }
}
