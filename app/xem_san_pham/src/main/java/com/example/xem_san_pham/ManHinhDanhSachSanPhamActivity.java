package com.example.xem_san_pham;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.common.model.HinhAnhSanPham;
import com.example.common.model.HinhAnhTrangWeb;
import com.example.common.network.ApiService;
import com.example.common.network.RetrofitClient;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    TextView tvKhongTimThay;
    boolean panelDangMo = false;
    boolean sortDangMo = false;
    private String sortHienTai = "Mới nhất";

    private static final String TAG = "DanhSachSP";
    private final NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("vi", "VN"));

    private List<com.example.common.model.SanPham> tatCaSanPham = new ArrayList<>();
    private Map<String, String> mapHinhAnh = new HashMap<>();
    private String loaiFilter = null;

    private CheckBox cb3Loi, cb7Loi, cb8Loi, cb9Loi, cb10Loi, cb11Loi;
    private CheckBox cbGia4Den6, cbGia6Den8, cbGia8Den10, cbGiaTren10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_san_pham);

        rv = findViewById(R.id.rvSanPham);
        btnLoc = findViewById(R.id.btnLoc);
        panelLoc = findViewById(R.id.panelLoc);
        contentMain = findViewById(R.id.contentMain);
        overlayDim = findViewById(R.id.overlayDim);
        tvKhongTimThay = findViewById(R.id.tvKhongTimThay);

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setNestedScrollingEnabled(false);

        cb3Loi = findViewById(R.id.cb3Loi);
        cb7Loi = findViewById(R.id.cb7Loi);
        cb8Loi = findViewById(R.id.cb8Loi);
        cb9Loi = findViewById(R.id.cb9Loi);
        cb10Loi = findViewById(R.id.cb10Loi);
        cb11Loi = findViewById(R.id.cb11Loi);
        cbGia4Den6 = findViewById(R.id.cbGia4Den6);
        cbGia6Den8 = findViewById(R.id.cbGia6Den8);
        cbGia8Den10 = findViewById(R.id.cbGia8Den10);
        cbGiaTren10 = findViewById(R.id.cbGiaTren10);

        btnLoc.setOnClickListener(v -> moPanelLoc());
        overlayDim.setOnClickListener(v -> dongPanelLoc());

        loaiFilter = getIntent().getStringExtra("loai_filter");

        TextView btnHuyLoc = findViewById(R.id.btnHuyLoc);
        if (btnHuyLoc != null) {
            btnHuyLoc.setOnClickListener(v -> {
                resetFilter();
                hienThiSanPham(locSanPham(tatCaSanPham));
                dongPanelLoc();
            });
        }

        TextView btnLocSanPham = findViewById(R.id.btnLocSanPham);
        if (btnLocSanPham != null) {
            btnLocSanPham.setOnClickListener(v -> {
                apDungLoc();
                dongPanelLoc();
            });
        }

        View btnSapXep = findViewById(R.id.btnSapXep);
        LinearLayout layoutSortOptions = findViewById(R.id.layoutSortOptions);
        TextView tvSortLabel = findViewById(R.id.tvSortLabel);

        btnSapXep.setOnClickListener(v -> {
            if (sortDangMo) {
                dongSortDropdown(layoutSortOptions);
            } else {
                moSortDropdown(layoutSortOptions);
            }
        });

        View.OnClickListener chonSapXep = v -> {
            TextView option = (TextView) v;
            tvSortLabel.setText(option.getText());
            sortHienTai = option.getText().toString();
            apDungSapXep();
            dongSortDropdown(layoutSortOptions);
        };

        findViewById(R.id.optionMoiNhat).setOnClickListener(chonSapXep);
        findViewById(R.id.optionGiaTang).setOnClickListener(chonSapXep);
        findViewById(R.id.optionGiaGiam).setOnClickListener(chonSapXep);

        layDuLieuTuApi();
        loadBanner();
    }

    private void loadBanner() {
        ImageView imgBanner = findViewById(R.id.imgBanner);
        ApiService api = RetrofitClient.getApiService();
        api.getAllHinhAnhTrangWeb().enqueue(new Callback<List<HinhAnhTrangWeb>>() {
            @Override
            public void onResponse(Call<List<HinhAnhTrangWeb>> call, Response<List<HinhAnhTrangWeb>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (HinhAnhTrangWeb h : response.body()) {
                        String loai = h.getLoaiBanner();
                        if (loai != null && loai.toLowerCase().contains("máy lọc nước")) {
                            String url = h.getDuongDanHinhAnh();
                            if (url != null && !url.isEmpty()) {
                                Glide.with(ManHinhDanhSachSanPhamActivity.this)
                                        .load(url)
                                        .placeholder(R.drawable.banner_may_loc_nuoc)
                                        .error(R.drawable.banner_may_loc_nuoc)
                                        .into(imgBanner);
                            }
                            return;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HinhAnhTrangWeb>> call, Throwable t) {}
        });
    }

    private void layDuLieuTuApi() {
        ApiService apiService = RetrofitClient.getApiService();

        apiService.getAllSanPham().enqueue(new Callback<List<com.example.common.model.SanPham>>() {
            @Override
            public void onResponse(Call<List<com.example.common.model.SanPham>> call, Response<List<com.example.common.model.SanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tatCaSanPham = response.body();
                    Log.d(TAG, "Lấy được " + tatCaSanPham.size() + " sản phẩm từ API");

                    mapHinhAnh.clear();
                    for (com.example.common.model.SanPham sp : tatCaSanPham) {
                        mapHinhAnh.put(sp.getMaSP(), null);
                    }

                    apiService.getAllHinhAnh().enqueue(new Callback<List<HinhAnhSanPham>>() {
                        @Override
                        public void onResponse(Call<List<HinhAnhSanPham>> call2, Response<List<HinhAnhSanPham>> response2) {
                            if (response2.isSuccessful() && response2.body() != null) {
                                for (HinhAnhSanPham ha : response2.body()) {
                                    mapHinhAnh.put(ha.getMaSP(), ha.getAnhChinh());
                                }
                            }
                            hienThiSanPham(locSanPham(tatCaSanPham));
                        }

                        @Override
                        public void onFailure(Call<List<HinhAnhSanPham>> call2, Throwable t) {
                                Log.e(TAG, "Lỗi tải hình ảnh: " + t.getMessage());
                                hienThiSanPham(locSanPham(tatCaSanPham));
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

    private void hienThiSanPham(List<com.example.common.model.SanPham> ds) {
        if (ds.isEmpty()) {
            rv.setAdapter(null);
            tvKhongTimThay.setVisibility(View.VISIBLE);
            return;
        }
        tvKhongTimThay.setVisibility(View.GONE);

        ArrayList<SanPham> list = new ArrayList<>();
        for (com.example.common.model.SanPham sp : ds) {
            String url = mapHinhAnh.get(sp.getMaSP());
            String gia = sp.getGia() != null && !sp.getGia().isEmpty() ? sp.getGia() : "Liên hệ";
            list.add(new SanPham(sp.get_id(), sp.getMaSP(), sp.getTenSP(), gia, url));
        }
        rv.setAdapter(new DieuHopSanPham(ManHinhDanhSachSanPhamActivity.this, list));
    }

    private void apDungLoc() {
        List<com.example.common.model.SanPham> dsGoc = locSanPham(tatCaSanPham);
        List<com.example.common.model.SanPham> ketQua = new ArrayList<>();

        boolean chonLoi = cb3Loi.isChecked() || cb7Loi.isChecked() || cb8Loi.isChecked()
                || cb9Loi.isChecked() || cb10Loi.isChecked() || cb11Loi.isChecked();
        boolean chonGia = cbGia4Den6.isChecked() || cbGia6Den8.isChecked()
                || cbGia8Den10.isChecked() || cbGiaTren10.isChecked();

        if (!chonLoi && !chonGia) {
            hienThiSanPham(dsGoc);
            return;
        }

        for (com.example.common.model.SanPham sp : dsGoc) {
            boolean hopLeLoi = true;
            boolean hopLeGia = true;

            if (chonLoi) {
                hopLeLoi = false;
                int soLoi = sp.getSoLoiLoc();
                if (cb3Loi.isChecked() && soLoi == 3) hopLeLoi = true;
                if (cb7Loi.isChecked() && soLoi == 7) hopLeLoi = true;
                if (cb8Loi.isChecked() && soLoi == 8) hopLeLoi = true;
                if (cb9Loi.isChecked() && soLoi == 9) hopLeLoi = true;
                if (cb10Loi.isChecked() && soLoi == 10) hopLeLoi = true;
                if (cb11Loi.isChecked() && soLoi == 11) hopLeLoi = true;
            }

            if (chonGia) {
                hopLeGia = false;
                long giaNum = parseGia(sp.getGia());
                if (cbGia4Den6.isChecked() && giaNum >= 4_000_000 && giaNum <= 6_000_000) hopLeGia = true;
                if (cbGia6Den8.isChecked() && giaNum >= 6_000_000 && giaNum <= 8_000_000) hopLeGia = true;
                if (cbGia4Den6.isChecked() && giaNum >= 8_000_000 && giaNum <= 10_000_000) hopLeGia = true;
                if (cbGiaTren10.isChecked() && giaNum > 10_000_000) hopLeGia = true;
            }

            if (hopLeLoi && hopLeGia) {
                ketQua.add(sp);
            }
        }

        hienThiSanPham(ketQua);
    }

    private long parseGia(String gia) {
        if (gia == null || gia.isEmpty()) return 0;
        String numStr = gia.replaceAll("[^0-9]", "");
        if (numStr.isEmpty()) return 0;
        try {
            return Long.parseLong(numStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void resetFilter() {
        cb3Loi.setChecked(false);
        cb7Loi.setChecked(false);
        cb8Loi.setChecked(false);
        cb9Loi.setChecked(false);
        cb10Loi.setChecked(false);
        cb11Loi.setChecked(false);
        cbGia4Den6.setChecked(false);
        cbGia6Den8.setChecked(false);
        cbGia8Den10.setChecked(false);
        cbGiaTren10.setChecked(false);
    }

    private void moSortDropdown(LinearLayout layoutSortOptions) {
        if (panelDangMo) return;
        sortDangMo = true;

        View btnSapXep = findViewById(R.id.btnSapXep);
        int[] btnPos = new int[2];
        btnSapXep.getLocationOnScreen(btnPos);
        int marginTop = btnPos[1] + btnSapXep.getHeight() + 8;

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layoutSortOptions.getLayoutParams();
        params.topMargin = marginTop;
        params.gravity = android.view.Gravity.END;
        params.rightMargin = 14;
        layoutSortOptions.setLayoutParams(params);

        layoutSortOptions.setVisibility(View.VISIBLE);
        overlayDim.setVisibility(View.VISIBLE);
        lamMoNen();
        overlayDim.setOnClickListener(v -> dongSortDropdown(layoutSortOptions));
    }

    private void dongSortDropdown(LinearLayout layoutSortOptions) {
        if (!sortDangMo) return;
        sortDangMo = false;
        layoutSortOptions.setVisibility(View.GONE);
        overlayDim.setVisibility(View.GONE);
        boMoNen();
        overlayDim.setOnClickListener(v -> dongPanelLoc());
    }

    private void apDungSapXep() {
        List<com.example.common.model.SanPham> ds = new ArrayList<>(locSanPham(tatCaSanPham));
        switch (sortHienTai) {
            case "Mới nhất":
                Collections.sort(ds, (a, b) -> Integer.compare(b.getNamRaMat(), a.getNamRaMat()));
                break;
            case "Giá thấp đến cao":
                Collections.sort(ds, (a, b) -> Long.compare(parseGia(a.getGia()), parseGia(b.getGia())));
                break;
            case "Giá cao đến thấp":
                Collections.sort(ds, (a, b) -> Long.compare(parseGia(b.getGia()), parseGia(a.getGia())));
                break;
        }
        hienThiSanPham(ds);
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

    private List<com.example.common.model.SanPham> locSanPham(List<com.example.common.model.SanPham> ds) {
        if (loaiFilter == null || loaiFilter.isEmpty()) return ds;
        List<com.example.common.model.SanPham> ketQua = new ArrayList<>();
        for (com.example.common.model.SanPham sp : ds) {
            String loai = sp.getLoaiMay();
            if (loai != null && loai.equalsIgnoreCase(loaiFilter)) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }
}
