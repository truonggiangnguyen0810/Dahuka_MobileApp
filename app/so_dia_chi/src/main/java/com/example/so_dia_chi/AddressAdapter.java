package com.example.so_dia_chi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private final List<Address> addresses;
    private final OnAddressClickListener listener;

    public interface OnAddressClickListener {
        void onAddressClick(Address address);
    }

    public AddressAdapter(List<Address> addresses, OnAddressClickListener listener) {
        this.addresses = addresses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_sdc2, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addresses.get(position);
        
        holder.tvName.setText(address.getTenNguoiNhan());
        // API không có trường Phone rõ ràng, tạm thời hiển thị Email hoặc trống nếu không dùng
        holder.tvPhone.setText(address.getEmail() != null ? address.getEmail() : "");
        holder.tvDetailAddress.setText(address.getDiaChiCuThe());
        
        String fullAddr = address.getPhuongXa() + ", " + address.getQuanHuyen() + ", " + address.getThanhPho();
        holder.tvFullAddress.setText(fullAddr.replace("null, ", ""));

        if (address.getDiaChiMacDinh() == 1) {
            holder.tvDefaultBadge.setVisibility(View.VISIBLE);
        } else {
            holder.tvDefaultBadge.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddressClick(address);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvDetailAddress, tvFullAddress, tvDefaultBadge;

        AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvDetailAddress = itemView.findViewById(R.id.tv_detail_address);
            tvFullAddress = itemView.findViewById(R.id.tv_full_address);
            tvDefaultBadge = itemView.findViewById(R.id.tv_default_badge);
        }
    }
}
