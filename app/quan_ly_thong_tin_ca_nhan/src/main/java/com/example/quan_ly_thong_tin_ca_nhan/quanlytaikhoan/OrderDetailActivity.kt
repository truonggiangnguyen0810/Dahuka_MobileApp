package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.common.model.KhachHang
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.XemChiTietDonHangBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: XemChiTietDonHangBinding

    companion object {
        private const val TAG = "OrderDetail"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = XemChiTietDonHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val maDonHang = intent.getStringExtra("MA_DON_HANG") ?: ""
        val maDiaChi = intent.getStringExtra("MA_DIA_CHI") ?: ""
        val tongSoLuong = intent.getIntExtra("TONG_SO_LUONG", 0)
        val tongThanhTien = intent.getDoubleExtra("TONG_THANH_TIEN", 0.0)
        val tongChietKhau = intent.getDoubleExtra("TONG_CHIET_KHAU", 0.0)
        val tongThanhToan = intent.getDoubleExtra("TONG_THANH_TOAN", 0.0)
        val ghiChu = intent.getStringExtra("GHI_CHU") ?: "Không có ghi chú"
        val trangThai = intent.getStringExtra("TRANG_THAI") ?: ""
        val phuongThucVanChuyen = intent.getStringExtra("PHUONG_THUC_VAN_CHUYEN") ?: ""
        val tenSanPham = intent.getStringExtra("TEN_SAN_PHAM") ?: ""
        val hinhAnhUrl = intent.getStringExtra("HINH_ANH_SAN_PHAM") ?: ""

        binding.shippingAddress.text = maDiaChi
        binding.productName.text = tenSanPham
        binding.quantity.text = "Số lượng: $tongSoLuong"
        binding.price.text = OrderAdapter.formatCurrency(tongThanhToan)

        if (!hinhAnhUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(hinhAnhUrl)
                .placeholder(R.drawable.product1)
                .error(R.drawable.product1)
                .into(binding.productImage)
        } else {
            binding.productImage.setImageResource(R.drawable.product1)
        }

        binding.shippingMethod.text = phuongThucVanChuyen

        binding.totalQuantity.text = tongSoLuong.toString()
        binding.totalAmount.text = OrderAdapter.formatCurrency(tongThanhTien)
        binding.totalDiscount.text = OrderAdapter.formatCurrency(tongChietKhau)
        binding.totalPayment.text = OrderAdapter.formatCurrency(tongThanhToan)
        binding.orderNote.text = ghiChu

        updateStatusTracker(trangThai)

        if (trangThai == "Đã hủy") {
            binding.cancelOrderButton.isEnabled = false
            binding.cancelOrderButton.backgroundTintList =
                android.content.res.ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dividerGray))
            binding.cancelOrderButton.setTextColor(ContextCompat.getColor(this, R.color.textGray))
        }

        loadCustomerInfo()
    }

    private fun updateStatusTracker(status: String) {
        val activeStep = when (status) {
            "Chờ xác nhận", "Đang xử lý" -> 1
            "Đã xác nhận" -> 2
            "Đang giao" -> 3
            "Đã giao" -> 4
            "Đã hủy" -> 0
            else -> 1
        }

        val redColor = ContextCompat.getColor(this, R.color.red)
        val grayColor = ContextCompat.getColor(this, R.color.statusGray)

        val icons = listOf(binding.trackerIcon1, binding.trackerIcon2, binding.trackerIcon3, binding.trackerIcon4)
        val texts = listOf(binding.trackerText1, binding.trackerText2, binding.trackerText3, binding.trackerText4)

        if (activeStep == 0) {
            for (i in icons.indices) {
                icons[i].setBackgroundResource(R.drawable.bg_circle_outline_gray)
                icons[i].imageTintList = android.content.res.ColorStateList.valueOf(grayColor)
                texts[i].setTextColor(grayColor)
            }
            texts[0].text = "Đã hủy"
            texts[0].setTextColor(redColor)
        } else {
            for (i in icons.indices) {
                val stepNum = i + 1
                if (stepNum <= activeStep) {
                    icons[i].setBackgroundResource(R.drawable.bg_circle_outline_red)
                    icons[i].imageTintList = android.content.res.ColorStateList.valueOf(redColor)
                    texts[i].setTextColor(redColor)
                } else {
                    icons[i].setBackgroundResource(R.drawable.bg_circle_outline_gray)
                    icons[i].imageTintList = android.content.res.ColorStateList.valueOf(grayColor)
                    texts[i].setTextColor(grayColor)
                }
            }
        }
    }

    private fun loadCustomerInfo() {
        KhachHangApiService.api.getAllKhachHang().enqueue(object : Callback<List<KhachHang>> {
            override fun onResponse(call: Call<List<KhachHang>>, response: Response<List<KhachHang>>) {
                if (response.isSuccessful) {
                    val khachHang = response.body()?.find { it.get_id() == "69d923c97922bf3246b90ba1" }
                        ?: response.body()?.firstOrNull()
                    if (khachHang != null) {
                        binding.customerName.text = khachHang.getTenKhachHang() ?: "Khách hàng"
                        binding.customerPhone.text = khachHang.getSdt()?.toString() ?: ""
                    }
                }
            }

            override fun onFailure(call: Call<List<KhachHang>>, t: Throwable) {
                Log.e(TAG, "Error loading customer", t)
            }
        })
    }
}
