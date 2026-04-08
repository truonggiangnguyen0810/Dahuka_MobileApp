package com.example.xem_san_pham;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgSanPham);
            ten = itemView.findViewById(R.id.tvTenSanPham);
            gia = itemView.findViewById(R.id.tvGiaSanPham);
            btn = itemView.findViewById(R.id.btnTimHieuThem);
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
        h.img.setImageResource(sp.getHinhAnh());

        View.OnClickListener click = v -> {
            Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
            intent.putExtra("ten", sp.getTen());
            intent.putExtra("gia", sp.getGia());
            intent.putExtra("img", sp.getHinhAnh());
            context.startActivity(intent);
        };

        h.itemView.setOnClickListener(click);
        h.btn.setOnClickListener(click);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}