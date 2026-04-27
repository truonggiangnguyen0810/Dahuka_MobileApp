package com.example.xem_san_pham;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.common.UserManager;
import com.example.common.model.ChiTietGioHang;
import com.example.common.network.ApiService;
import com.example.common.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DieuHopSanPham extends RecyclerView.Adapter<DieuHopSanPham.ViewHolder> {

    private Context context;
    private List<SanPham> list;

    public DieuHopSanPham(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ten, gia, btn;
        FrameLayout btnGioHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgSanPham);
            ten = itemView.findViewById(R.id.tvTenSanPham);
            gia = itemView.findViewById(R.id.tvGiaSanPham);
            btn = itemView.findViewById(R.id.btnTimHieuThem);
            btnGioHang = itemView.findViewById(R.id.btnThemGioHang);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        SanPham sp = list.get(i);

        h.ten.setText(sp.getTen());
        h.gia.setText(sp.getGia());

        Glide.with(context)
                .load(sp.getHinhAnhUrl())
                .placeholder(R.drawable.sp_1)
                .error(R.drawable.sp_1)
                .into(h.img);

        View.OnClickListener click = v -> {
            Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
            intent.putExtra("maSP", sp.getMaSP());
            intent.putExtra("ten", sp.getTen());
            intent.putExtra("gia", sp.getGia());
            intent.putExtra("imgUrl", sp.getHinhAnhUrl());
            intent.putExtra("_id", sp.get_id());
            context.startActivity(intent);
        };

        h.itemView.setOnClickListener(click);
        h.btn.setOnClickListener(click);

        h.btnGioHang.setOnClickListener(v -> themVaoGioHang(sp.getMaSP()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void themVaoGioHang(String maSP) {
        if (!UserManager.isLoggedIn(context)) {
            Toast.makeText(context, "Vui lòng đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        String maKH = UserManager.getMaKhachHang(context);
        if (maKH == null) {
            Toast.makeText(context, "Không xác định được tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient.getApiService();
        api.getAllChiTietGioHang().enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(context, "Lỗi kiểm tra giỏ hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChiTietGioHang tonTai = null;
                for (ChiTietGioHang ct : response.body()) {
                    if (maKH.equals(ct.getMaKhachHang()) && maSP.equals(ct.getMaTietPham())) {
                        tonTai = ct;
                        break;
                    }
                }

                if (tonTai != null) {
                    capNhatSoLuong(tonTai);
                } else {
                    taoMoi(maKH, maSP);
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void capNhatSoLuong(ChiTietGioHang ct) {
        ct.setSoLuong(ct.getSoLuong() + 1);
        ApiService api = RetrofitClient.getApiService();
        api.updateChiTietGioHang(ct.getMaKhachHang(), ct.getMaTietPham(), ct).enqueue(new Callback<ChiTietGioHang>() {
            @Override
            public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Đã cập nhật giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi cập nhật giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taoMoi(String maKH, String maSP) {
        ChiTietGioHang ct = new ChiTietGioHang();
        ct.setMaKhachHang(maKH);
        ct.setMaTietPham(maSP);
        ct.setSoLuong(1);

        ApiService api = RetrofitClient.getApiService();
        api.createChiTietGioHang(ct).enqueue(new Callback<ChiTietGioHang>() {
            @Override
            public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
