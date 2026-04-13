package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.LichSuMuaHangBinding

class PurchaseHistoryActivity : AppCompatActivity() {

    private lateinit var binding: LichSuMuaHangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LichSuMuaHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRecyclerView()
        setupTabLayout()
    }

    private lateinit var fullOrders: List<Order>

    private fun setupRecyclerView() {
        fullOrders = listOf(
            Order("#DH001", "20/05/2024 - 14:30", "GIAO HÀNG THÀNH CÔNG", "Máy Lọc Nước RO MP-S126", "Mutosi MP-S126", "01 Bộ", "5.790.000đ", "5.790.000đ", R.drawable.product1),
            Order("#DH002", "18/05/2024 - 09:15", "ĐANG GIAO HÀNG", "Máy Lọc Nước Mutosi Pro", "Mutosi Pro X1", "01", "6.250.000đ", "6.250.000đ", R.drawable.product2),
            Order("#DH003", "15/05/2024 - 11:20", "ĐÃ HỦY", "Máy Lọc Nước Mutosi Eco", "Mutosi Eco Slim", "01", "4.450.000đ", "4.450.000đ", R.drawable.product3),
            Order("#DH004", "10/05/2024 - 08:00", "ĐANG XỬ LÝ", "Máy Lọc Nước RO MP-S126", "Mutosi MP-S126", "01 Bộ", "5.790.000đ", "5.790.000đ", R.drawable.product1)
        )

        binding.orderRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.orderRecyclerView.adapter = OrderAdapter(this, fullOrders)
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
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
            1 -> fullOrders.filter { it.status == "ĐANG XỬ LÝ" }
            2 -> fullOrders.filter { it.status == "ĐANG GIAO HÀNG" }
            3 -> fullOrders.filter { it.status == "GIAO HÀNG THÀNH CÔNG" }
            4 -> fullOrders.filter { it.status == "ĐÃ HỦY" }
            else -> fullOrders
        }
        binding.orderRecyclerView.adapter = OrderAdapter(this, filteredList)
    }
}
