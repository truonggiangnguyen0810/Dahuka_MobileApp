package com.example.xem_san_pham;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.common.model.HinhAnhSanPham;
import com.example.common.model.SanPham;
import com.example.common.network.ApiService;
import com.example.common.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    private ImageView imgMainProduct;
    private ImageView imgThumb1, imgThumb2, imgThumb3;
    private TextView txtTenSanPham, txtGiaSanPham, txtMaSanPham;
    private TextView tabThongSo, tabTinhNang, tabMoTaChiTiet;
    private TextView contentSection;

    private String maSP;
    private SanPham sanPhamData;
    private HinhAnhSanPham hinhAnhData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_san_pham);

        imgMainProduct = findViewById(R.id.imgMainProduct);
        imgThumb1 = findViewById(R.id.imgThumb1);
        imgThumb2 = findViewById(R.id.imgThumb2);
        imgThumb3 = findViewById(R.id.imgThumb3);
        txtTenSanPham = findViewById(R.id.txtTenSanPham);
        txtGiaSanPham = findViewById(R.id.txtGiaSanPham);
        txtMaSanPham = findViewById(R.id.txtMaSanPham);
        tabThongSo = findViewById(R.id.tabThongSo);
        tabTinhNang = findViewById(R.id.tabTinhNang);
        tabMoTaChiTiet = findViewById(R.id.tabMoTaChiTiet);
        contentSection = (TextView) findViewById(R.id.txtSection1Content);

        maSP = getIntent().getStringExtra("maSP");
        String tenStr = getIntent().getStringExtra("ten");
        String giaStr = getIntent().getStringExtra("gia");
        String imgUrl = getIntent().getStringExtra("imgUrl");

        txtTenSanPham.setText(tenStr);
        txtGiaSanPham.setText(giaStr);
        txtMaSanPham.setText("Mã sản phẩm: " + (maSP != null ? maSP : ""));

        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(this).load(imgUrl).placeholder(R.drawable.sp_1).error(R.drawable.sp_1).into(imgMainProduct);
        }

        loadSanPhamFromApi();
        loadHinhAnhFromApi();

        setupTabs();
    }

    private void loadSanPhamFromApi() {
        ApiService api = RetrofitClient.getApiService();
        api.getAllSanPham().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (SanPham sp : response.body()) {
                        if (maSP != null && maSP.equals(sp.getMaSP())) {
                            sanPhamData = sp;
                            txtTenSanPham.setText(sp.getTenSP());
                            txtGiaSanPham.setText(sp.getGia() != null ? sp.getGia() : "Liên hệ");
                            txtMaSanPham.setText("Mã sản phẩm: " + sp.getMaSP());
                            showTabThongSo();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {}
        });
    }

    private void loadHinhAnhFromApi() {
        if (maSP == null) return;
        ApiService api = RetrofitClient.getApiService();
        api.getHinhAnhByMaSP(maSP).enqueue(new Callback<List<HinhAnhSanPham>>() {
            @Override
            public void onResponse(Call<List<HinhAnhSanPham>> call, Response<List<HinhAnhSanPham>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    hinhAnhData = response.body().get(0);

                    String url0 = hinhAnhData.getAnhChinh();
                    String url1 = hinhAnhData.getAnhPhu1();
                    String url2 = hinhAnhData.getAnhPhu2();

                    loadImage(imgMainProduct, url0);
                    loadImage(imgThumb1, url0);
                    loadImage(imgThumb2, url1);
                    loadImage(imgThumb3, url2);

                    imgThumb1.setOnClickListener(v -> loadImage(imgMainProduct, url0));
                    imgThumb2.setOnClickListener(v -> loadImage(imgMainProduct, url1));
                    imgThumb3.setOnClickListener(v -> loadImage(imgMainProduct, url2));
                }
            }

            @Override
            public void onFailure(Call<List<HinhAnhSanPham>> call, Throwable t) {}
        });
    }

    private void loadImage(ImageView iv, String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(this).load(url).placeholder(R.drawable.sp_1).error(R.drawable.sp_1).into(iv);
        }
    }

    private void setupTabs() {
        tabThongSo.setOnClickListener(v -> {
            setActiveTab(0);
            showTabThongSo();
        });
        tabTinhNang.setOnClickListener(v -> {
            setActiveTab(1);
            showTabTinhNang();
        });
        tabMoTaChiTiet.setOnClickListener(v -> {
            setActiveTab(2);
            showTabMoTaChiTiet();
        });
    }

    private void setActiveTab(int index) {
        int activeBg = 0xFF007A5A;
        int inactiveBg = 0xFF8A8A8A;
        tabThongSo.setBackgroundColor(index == 0 ? activeBg : inactiveBg);
        tabTinhNang.setBackgroundColor(index == 1 ? activeBg : inactiveBg);
        tabMoTaChiTiet.setBackgroundColor(index == 2 ? activeBg : inactiveBg);
    }

    private void showTabThongSo() {
        if (sanPhamData == null) return;
        TextView titleView = findViewById(R.id.txtSection1Title);
        TextView subtitleView = findViewById(R.id.txtSection1SubTitle);
        ImageView bannerView = findViewById(R.id.imgBanner);

        if (titleView != null) titleView.setText("THÔNG SỐ KỸ THUẬT");
        if (subtitleView != null) subtitleView.setText(sanPhamData.getTenSP());

        StringBuilder sb = new StringBuilder();
        if (sanPhamData.getCongSuatLoc() != null && !sanPhamData.getCongSuatLoc().isEmpty())
            sb.append("• Công suất lọc: ").append(sanPhamData.getCongSuatLoc()).append("\n");
        if (sanPhamData.getLoaiMay() != null && !sanPhamData.getLoaiMay().isEmpty())
            sb.append("• Loại máy: ").append(sanPhamData.getLoaiMay()).append("\n");
        if (sanPhamData.getCongNgheLoc() != null && !sanPhamData.getCongNgheLoc().isEmpty())
            sb.append("• Công nghệ lọc: ").append(sanPhamData.getCongNgheLoc()).append("\n");
        if (sanPhamData.getDungTichBinhChua() != null && !sanPhamData.getDungTichBinhChua().isEmpty())
            sb.append("• Dung tích bình chứa: ").append(sanPhamData.getDungTichBinhChua()).append("\n");
        if (sanPhamData.getSoLoiLoc() > 0)
            sb.append("• Số lõi lọc: ").append(sanPhamData.getSoLoiLoc()).append("\n");
        if (sanPhamData.getKichThuoc() != null && !sanPhamData.getKichThuoc().isEmpty())
            sb.append("• Kích thước: ").append(sanPhamData.getKichThuoc()).append("\n");
        if (sanPhamData.getKhoiLuong() != null && !sanPhamData.getKhoiLuong().isEmpty())
            sb.append("• Khối lượng: ").append(sanPhamData.getKhoiLuong()).append("\n");
        if (sanPhamData.getNoiSX() != null && !sanPhamData.getNoiSX().isEmpty())
            sb.append("• Nơi sản xuất: ").append(sanPhamData.getNoiSX()).append("\n");
        if (sanPhamData.getThoiGianBH() != null && !sanPhamData.getThoiGianBH().isEmpty())
            sb.append("• Thời gian bảo hành: ").append(sanPhamData.getThoiGianBH()).append("\n");

        if (contentSection != null) contentSection.setText(sb.toString());
        if (hinhAnhData != null) loadImage(bannerView, hinhAnhData.getAnhMoTa());

        TextView sec2Title = findViewById(R.id.txtSection2Title);
        TextView sec2Subtitle = findViewById(R.id.txtSection2SubTitle);
        TextView sec2Content = findViewById(R.id.txtSection2Content);
        if (sec2Title != null) sec2Title.setVisibility(View.GONE);
        if (sec2Subtitle != null) sec2Subtitle.setVisibility(View.GONE);
        if (sec2Content != null) sec2Content.setVisibility(View.GONE);
    }

    private void showTabTinhNang() {
        if (sanPhamData == null) return;
        TextView titleView = findViewById(R.id.txtSection1Title);
        TextView subtitleView = findViewById(R.id.txtSection1SubTitle);
        ImageView bannerView = findViewById(R.id.imgBanner);

        if (titleView != null) titleView.setText("TÍNH NĂNG NỔI BẬT");
        if (subtitleView != null) subtitleView.setText(sanPhamData.getTenSP());
        if (contentSection != null) contentSection.setText(sanPhamData.getTinhNang() != null ? sanPhamData.getTinhNang() : "Chưa có thông tin");
        if (hinhAnhData != null) loadImage(bannerView, hinhAnhData.getAnhTinhNang());

        TextView sec2Title = findViewById(R.id.txtSection2Title);
        TextView sec2Subtitle = findViewById(R.id.txtSection2SubTitle);
        TextView sec2Content = findViewById(R.id.txtSection2Content);
        if (sec2Title != null) sec2Title.setVisibility(View.GONE);
        if (sec2Subtitle != null) sec2Subtitle.setVisibility(View.GONE);
        if (sec2Content != null) sec2Content.setVisibility(View.GONE);
    }

    private void showTabMoTaChiTiet() {
        if (sanPhamData == null) return;
        TextView titleView = findViewById(R.id.txtSection1Title);
        TextView subtitleView = findViewById(R.id.txtSection1SubTitle);
        ImageView bannerView = findViewById(R.id.imgBanner);

        if (titleView != null) titleView.setText("MÔ TẢ CHI TIẾT");
        if (subtitleView != null) subtitleView.setText(sanPhamData.getTenSP());
        if (contentSection != null) contentSection.setText(sanPhamData.getMoTaChiTiet() != null ? sanPhamData.getMoTaChiTiet() : "Chưa có thông tin");
        if (hinhAnhData != null) loadImage(bannerView, hinhAnhData.getAnhMoTa());

        TextView sec2Title = findViewById(R.id.txtSection2Title);
        TextView sec2Subtitle = findViewById(R.id.txtSection2SubTitle);
        TextView sec2Content = findViewById(R.id.txtSection2Content);
        if (sec2Title != null) {
            sec2Title.setVisibility(View.VISIBLE);
            sec2Title.setText("THÔNG TIN SẢN PHẨM");
        }
        if (sec2Subtitle != null) {
            sec2Subtitle.setVisibility(View.VISIBLE);
            sec2Subtitle.setText(sanPhamData.getTenSP());
        }
        if (sec2Content != null) {
            sec2Content.setVisibility(View.VISIBLE);
            String moTa = sanPhamData.getMoTa();
            sec2Content.setText(moTa != null ? moTa : "Chưa có thông tin");
        }
    }
}
