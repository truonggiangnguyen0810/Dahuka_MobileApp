package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.ActivityMainBinding
import com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api.KhachHang
import com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentId: String = "69d923c97922bf3246b90ba1"
    private var currentMaKH: String = ""

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        loadKhachHangInfo()
    }

    private fun loadKhachHangInfo() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.khachHangApi.getAllKhachHang()
                if (response.isSuccessful) {
                    val khachHangList = response.body()
                    val khachHang = khachHangList?.find { it.id == currentId }
                    if (khachHang != null) {
                        currentMaKH = khachHang.maKhachHang ?: ""
                        displayKhachHangInfo(khachHang)
                    }
                } else {
                    Log.e(TAG, "API Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network Error", e)
            }
        }
    }

    private fun displayKhachHangInfo(khachHang: KhachHang) {
        // Update the user name in the profile card
        try {
            val profileCard = binding.root.findViewById<TextView>(R.id.userName)
            profileCard?.text = khachHang.tenKhachHang ?: "Khách hàng"
        } catch (e: Exception) {
            Log.e(TAG, "Could not update user name", e)
        }

        // Update member ID
        try {
            val idText = binding.root.findViewById<TextView>(R.id.memberId)
            idText?.text = "ID: ${khachHang.maKhachHang}"
        } catch (e: Exception) {
            Log.e(TAG, "Could not update member ID", e)
        }
    }

    override fun onResume() {
        super.onResume()
        loadKhachHangInfo()
    }

    private fun setupClickListeners() {
        binding.personalInfoLayout.setBounceClickEffect {
            val intent = Intent(this, PersonalInfoActivity::class.java).apply {
                putExtra(PersonalInfoActivity.EXTRA_KHACH_HANG_ID, currentId)
            }
            startActivity(intent)
        }

        binding.purchaseHistoryLayout.setBounceClickEffect {
            val intent = Intent(this, PurchaseHistoryActivity::class.java).apply {
                putExtra(PurchaseHistoryActivity.EXTRA_MA_KHACH_HANG, currentMaKH)
            }
            startActivity(intent)
        }

        binding.changePasswordLayout.setBounceClickEffect {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.addressBookLayout.setBounceClickEffect {
            showSuccessToast("Tính năng Sổ địa chỉ đang phát triển")
        }

        binding.customerSupportLayout.setBounceClickEffect {
            showSuccessToast("Tính năng Hỗ trợ khách hàng đang phát triển")
        }

        binding.logoutButton.setBounceClickEffect {
            showSuccessToast("Đã đăng xuất")
        }
    }
}
