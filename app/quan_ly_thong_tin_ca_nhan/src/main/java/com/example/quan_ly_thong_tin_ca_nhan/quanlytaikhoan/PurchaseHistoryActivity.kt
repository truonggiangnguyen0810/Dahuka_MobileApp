package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.model.ChiTietDonHang
import com.example.common.model.DonHang
import com.example.common.model.HinhAnhSanPham
import com.example.common.model.SanPham
import com.example.common.network.RetrofitClient
import com.example.quan_ly_thong_tin_ca_nhan.databinding.LichSuMuaHangBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseHistoryActivity : AppCompatActivity() {

    private lateinit var binding: LichSuMuaHangBinding
    private var fullOrders: List<DonHang> = emptyList()
    private var productNameMap: Map<String, String> = emptyMap()
    private var productImageMap: Map<String, String> = emptyMap()

    companion object {
        const val EXTRA_MA_KHACH_HANG = "MA_KHACH_HANG"
        private const val TAG = "PurchaseHistory"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LichSuMuaHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.orderRecyclerView.layoutManager = LinearLayoutManager(this)

        setupTabLayout()

        val maKhachHang = intent.getStringExtra(EXTRA_MA_KHACH_HANG)
        loadOrders(maKhachHang)
    }

    private fun loadOrders(maKhachHang: String?) {
        val api = RetrofitClient.getApiService()

        api.getAllDonHang().enqueue(object : Callback<List<DonHang>> {
            override fun onResponse(call: Call<List<DonHang>>, response: Response<List<DonHang>>) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "API Error: ${response.code()}")
                    return
                }

                val allOrders = response.body() ?: emptyList<DonHang>()
                fullOrders = if (!maKhachHang.isNullOrEmpty()) {
                    allOrders.filter { it.maKhachHang == maKhachHang }
                } else {
                    allOrders
                }

                api.getAllChiTietDonHang().enqueue(object : Callback<List<ChiTietDonHang>> {
                    override fun onResponse(call: Call<List<ChiTietDonHang>>, response: Response<List<ChiTietDonHang>>) {
                        val chiTietList = response.body() ?: emptyList<ChiTietDonHang>()
                        val groupedChiTiet = chiTietList.groupBy { it.maDonHang ?: "" }

                        api.getAllSanPham().enqueue(object : Callback<List<SanPham>> {
                            override fun onResponse(call: Call<List<SanPham>>, response: Response<List<SanPham>>) {
                                val sanPhamMap = (response.body() ?: emptyList<SanPham>())
                                    .associateBy { it.maSP ?: "" }

                                api.getAllHinhAnh().enqueue(object : Callback<List<HinhAnhSanPham>> {
                                    override fun onResponse(call: Call<List<HinhAnhSanPham>>, response: Response<List<HinhAnhSanPham>>) {
                                        val imagesByProduct = (response.body() ?: emptyList<HinhAnhSanPham>())
                                            .groupBy { it.maSP ?: "" }
                                            .mapValues { (_, images) ->
                                                images.firstOrNull()?.anhChinh
                                            }

                                        productNameMap = groupedChiTiet.mapValues { (_, items) ->
                                            val firstMaSP = items.firstOrNull()?.maSanPham ?: ""
                                            sanPhamMap[firstMaSP]?.tenSP ?: firstMaSP
                                        }

                                        productImageMap = groupedChiTiet.mapValues { (_, items) ->
                                            val firstMaSP = items.firstOrNull()?.maSanPham ?: ""
                                            imagesByProduct[firstMaSP] ?: ""
                                        }

                                        binding.orderRecyclerView.adapter =
                                            OrderAdapter(this@PurchaseHistoryActivity, fullOrders, productNameMap, productImageMap)
                                        Log.d(TAG, "Loaded ${fullOrders.size} orders")
                                    }

                                    override fun onFailure(call: Call<List<HinhAnhSanPham>>, t: Throwable) {
                                        Log.e(TAG, "Image API Error", t)
                                        binding.orderRecyclerView.adapter =
                                            OrderAdapter(this@PurchaseHistoryActivity, fullOrders, emptyMap(), emptyMap())
                                    }
                                })
                            }

                            override fun onFailure(call: Call<List<SanPham>>, t: Throwable) {
                                Log.e(TAG, "SanPham API Error", t)
                            }
                        })
                    }

                    override fun onFailure(call: Call<List<ChiTietDonHang>>, t: Throwable) {
                        Log.e(TAG, "ChiTiet API Error", t)
                    }
                })
            }

            override fun onFailure(call: Call<List<DonHang>>, t: Throwable) {
                Log.e(TAG, "Network Error", t)
            }
        })
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
