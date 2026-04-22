package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quan_ly_thong_tin_ca_nhan.databinding.LichSuMuaHangBinding
import com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api.DonHang
import com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PurchaseHistoryActivity : AppCompatActivity() {

    private lateinit var binding: LichSuMuaHangBinding
    private var fullOrders: List<DonHang> = emptyList()
    // MaDonHang -> TenSP đầu tiên
    private var productNameMap: Map<String, String> = emptyMap()
    // MaDonHang -> DuongDanHinhAnh đầu tiên
    private var productImageMap: Map<String, String> = emptyMap()

    companion object {
        const val EXTRA_MA_KHACH_HANG = "MA_KHACH_HANG"
        private const val TAG = "PurchaseHistory"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LichSuMuaHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.orderRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this)

        setupTabLayout()

        val maKhachHang = intent.getStringExtra(EXTRA_MA_KHACH_HANG)
        loadOrders(maKhachHang)
    }

    private fun loadOrders(maKhachHang: String?) {
        lifecycleScope.launch {
            try {
                // Parallel fetch all 4 APIs
                val ordersDeferred = async { RetrofitClient.khachHangApi.getAllDonHang() }
                val chiTietDeferred = async { RetrofitClient.khachHangApi.getAllChiTietDonHang() }
                val sanPhamDeferred = async { RetrofitClient.khachHangApi.getAllSanPham() }
                val hinhAnhDeferred = async { RetrofitClient.khachHangApi.getAllHinhAnhSanPham() }

                val ordersResponse = ordersDeferred.await()
                val chiTietResponse = chiTietDeferred.await()
                val sanPhamResponse = sanPhamDeferred.await()
                val hinhAnhResponse = hinhAnhDeferred.await()

                if (ordersResponse.isSuccessful) {
                    val allOrders = ordersResponse.body() ?: emptyList()
                    fullOrders = if (!maKhachHang.isNullOrEmpty()) {
                        allOrders.filter { it.maKhachHang == maKhachHang }
                    } else {
                        allOrders
                    }

                    // Build MaSP -> TenSP map
                    val sanPhamMap = if (sanPhamResponse.isSuccessful) {
                        (sanPhamResponse.body() ?: emptyList())
                            .associateBy { it.maSP ?: "" }
                    } else emptyMap()

                    // Build MaSP -> HinhAnh (tên ảnh chính thường là Anhchinh...)
                    val imagesByProduct = if (hinhAnhResponse.isSuccessful) {
                        (hinhAnhResponse.body() ?: emptyList())
                            .groupBy { it.maSP ?: "" }
                            .mapValues { (_, images) ->
                                images.firstOrNull()?.duongDanHinhAnh
                            }
                    } else emptyMap()

                    // Build MaDonHang -> TenSP and Image đầu tiên
                    if (chiTietResponse.isSuccessful) {
                        val chiTietList = chiTietResponse.body() ?: emptyList()
                        val groupedChiTiet = chiTietList.groupBy { it.maDonHang ?: "" }
                        
                        productNameMap = groupedChiTiet.mapValues { (_, items) ->
                            val firstMaSP = items.firstOrNull()?.maSanPham ?: ""
                            sanPhamMap[firstMaSP]?.tenSP ?: firstMaSP
                        }

                        productImageMap = groupedChiTiet.mapValues { (_, items) ->
                            val firstMaSP = items.firstOrNull()?.maSanPham ?: ""
                            imagesByProduct[firstMaSP] ?: ""
                        }
                    }

                    binding.orderRecyclerView.adapter =
                        OrderAdapter(this@PurchaseHistoryActivity, fullOrders, productNameMap, productImageMap)
                    Log.d(TAG, "Loaded ${fullOrders.size} orders")
                } else {
                    Log.e(TAG, "API Error: ${ordersResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network Error", e)
            }
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object :
            com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                filterOrders(tab.position)
            }
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
        })
    }

    private fun filterOrders(position: Int) {
        val filteredList = when (position) {
            0 -> fullOrders
            1 -> fullOrders.filter {
                it.trangThaiDonHang == "Chờ xác nhận" || it.trangThaiDonHang == "Đang xử lý"
            }
            2 -> fullOrders.filter { it.trangThaiDonHang == "Đang giao" }
            3 -> fullOrders.filter { it.trangThaiDonHang == "Đã giao" }
            4 -> fullOrders.filter { it.trangThaiDonHang == "Đã hủy" }
            else -> fullOrders
        }
        binding.orderRecyclerView.adapter =
            OrderAdapter(this, filteredList, productNameMap, productImageMap)
    }
}
