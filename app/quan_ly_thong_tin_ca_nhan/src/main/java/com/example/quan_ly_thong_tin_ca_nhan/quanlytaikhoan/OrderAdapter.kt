package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.DonHangBinding

data class Order(
    val id: String,
    val date: String,
    val status: String,
    val productName: String,
    val modelName: String,
    val quantity: String,
    val price: String,
    val total: String,
    val imageResId: Int
)

class OrderAdapter(private val context: Context, private val orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: DonHangBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = DonHangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.orderId.text = order.id
        holder.binding.orderDate.text = order.date
        holder.binding.statusBadge.text = order.status
        holder.binding.productName.text = order.productName
        holder.binding.quantity.text = "Số lượng: ${order.quantity}"
        holder.binding.price.text = order.price
        holder.binding.totalPayment.text = order.total
        holder.binding.productImage.setImageResource(order.imageResId)

        when (order.status) {
            "GIAO HÀNG THÀNH CÔNG" -> {
                holder.binding.statusBadge.setBackgroundResource(R.drawable.bg_badge_green)
                holder.binding.statusBadge.setTextColor(context.getColor(R.color.primaryGreen))
            }
            "ĐANG GIAO HÀNG", "ĐANG XỬ LÝ" -> {
                holder.binding.statusBadge.setBackgroundResource(R.drawable.bg_badge_blue)
                holder.binding.statusBadge.setTextColor(context.getColor(R.color.blue))
            }
            "ĐÃ HỦY" -> {
                holder.binding.statusBadge.setBackgroundResource(R.drawable.bg_badge_gray)
                holder.binding.statusBadge.setTextColor(context.getColor(R.color.textGray))
            }
        }

        holder.binding.viewDetailsButton.setBounceClickEffect {
            val intent = Intent(context, OrderDetailActivity::class.java).apply {
                putExtra("ORDER_ID", order.id)
                putExtra("PRODUCT_NAME", order.productName)
                putExtra("MODEL_NAME", order.modelName)
                putExtra("QUANTITY", order.quantity)
                putExtra("PRICE", order.price)
                putExtra("TOTAL", order.total)
                putExtra("STATUS", order.status)
                putExtra("IMAGE_RES", order.imageResId)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orders.size
}
