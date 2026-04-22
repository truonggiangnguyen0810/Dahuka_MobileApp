package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.DonHangBinding
import com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api.DonHang
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(
    private val context: Context,
    private val orders: List<DonHang>,
    // MaDonHang -> TenSP đầu tiên
    private val productNameMap: Map<String, String> = emptyMap(),
    // MaDonHang -> URL hình ảnh sản phẩm đầu tiên
    private val productImageMap: Map<String, String> = emptyMap()
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: DonHangBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = DonHangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val maDH = order.maDonHang ?: ""

        holder.binding.orderId.text = "#$maDH"
        holder.binding.orderDate.text = order.phuongThucVanChuyen ?: ""

        val status = order.trangThaiDonHang ?: ""
        holder.binding.statusBadge.text = status.uppercase()

        // Show first product name; fallback to order code
        val tenSP = productNameMap[maDH] ?: "Đơn hàng $maDH"
        holder.binding.productName.text = tenSP
        holder.binding.quantity.text = "Số lượng: ${order.tongSoLuong ?: 0}"
        holder.binding.price.text = formatCurrency(order.tongThanhTien)
        holder.binding.totalPayment.text = formatCurrency(order.tongThanhToan)

        // Load image from map
        val imageUrl = productImageMap[maDH]
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.product1)
                .error(R.drawable.product1)
                .into(holder.binding.productImage)
        } else {
            holder.binding.productImage.setImageResource(R.drawable.product1)
        }

        // Status badge style
        when (status) {
            "Đã giao" -> {
                holder.binding.statusBadge.setBackgroundResource(R.drawable.bg_badge_green)
                holder.binding.statusBadge.setTextColor(context.getColor(R.color.primaryGreen))
            }
            "Đang giao", "Đang xử lý", "Chờ xác nhận" -> {
                holder.binding.statusBadge.setBackgroundResource(R.drawable.bg_badge_blue)
                holder.binding.statusBadge.setTextColor(context.getColor(R.color.blue))
            }
            "Đã hủy" -> {
                holder.binding.statusBadge.setBackgroundResource(R.drawable.bg_badge_gray)
                holder.binding.statusBadge.setTextColor(context.getColor(R.color.textGray))
            }
            else -> {
                holder.binding.statusBadge.setBackgroundResource(R.drawable.bg_badge_gray)
                holder.binding.statusBadge.setTextColor(context.getColor(R.color.textGray))
            }
        }

        holder.binding.viewDetailsButton.setBounceClickEffect {
            val intent = Intent(context, OrderDetailActivity::class.java).apply {
                putExtra("DON_HANG_ID", order.id)
                putExtra("MA_DON_HANG", maDH)
                putExtra("MA_DIA_CHI", order.maDiaChi)
                putExtra("TONG_SO_LUONG", order.tongSoLuong ?: 0)
                putExtra("TONG_THANH_TIEN", order.tongThanhTien ?: 0.0)
                putExtra("TONG_CHIET_KHAU", order.tongChietKhau ?: 0.0)
                putExtra("TONG_THANH_TOAN", order.tongThanhToan ?: 0.0)
                putExtra("GHI_CHU", order.ghiChu ?: "Không có ghi chú")
                putExtra("TRANG_THAI", status)
                putExtra("PHUONG_THUC_VAN_CHUYEN", order.phuongThucVanChuyen ?: "")
                // Pass first product name and image
                putExtra("TEN_SAN_PHAM", tenSP)
                putExtra("HINH_ANH_SAN_PHAM", imageUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orders.size

    companion object {
        fun formatCurrency(amount: Double?): String {
            if (amount == null) return "0 đ"
            val formatter = NumberFormat.getNumberInstance(Locale("vi", "VN"))
            return "${formatter.format(amount.toLong())} đ"
        }
    }
}
