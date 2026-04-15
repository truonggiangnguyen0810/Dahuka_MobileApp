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
    private var currentId: String = "69d923c97922bf3246b90b79"

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
        // The profile card uses a string resource, but we can find and update the TextView
        try {
            val profileCard = binding.root.findViewById<TextView>(R.id.userName)
            profileCard?.text = khachHang.tenKhachHang ?: "Khách hàng"
        } catch (e: Exception) {
            // fallback: find by content description or other means
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
        // Reload data when returning from PersonalInfoActivity
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
            val intent = Intent(this, PurchaseHistoryActivity::class.java)
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
