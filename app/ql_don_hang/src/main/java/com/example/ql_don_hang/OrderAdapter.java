package com.example.ql_don_hang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_don_hang.databinding.ItemOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private List<Order> orderListFull;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.orderListFull = new ArrayList<>(orderList);
        this.listener = listener;
    }

    public void updateData(List<Order> newList) {
        this.orderList = newList;
        this.orderListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        List<Order> filteredList = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            filteredList.addAll(orderListFull);
        } else {
            String filterPattern = text.toLowerCase().trim();
            for (Order item : orderListFull) {
                if (item.getMaDonHang() != null && item.getMaDonHang().toLowerCase().contains(filterPattern)) {
                    filteredList.add(item);
                }
            }
        }
        orderList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.binding.tvOrderId.setText(order.getMaDonHang());
        holder.binding.tvOrderDate.setText(order.getDate());
        holder.binding.tvCustomerName.setText(order.getCustomerName());
        holder.binding.tvTotalAmount.setText("Tổng tiền: " + String.format("%,.0f", order.getTotalAmount()) + " đ");

        if ("Cancelled".equals(order.getStatus())) {
            holder.binding.btnReorder.setVisibility(View.VISIBLE);
        } else {
            holder.binding.btnReorder.setVisibility(View.GONE);
        }

        holder.binding.btnViewDetail.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderClick(order);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;

        public OrderViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
