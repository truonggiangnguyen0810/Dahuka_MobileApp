package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.common.model.KhachHang
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentId: String = "69d923c97922bf3246b90ba1"
    private var currentMaKH: String = ""

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        loadKhachHangInfo()
    }

    private fun loadKhachHangInfo() {
        KhachHangApiService.api.getAllKhachHang().enqueue(object : Callback<List<KhachHang>> {
            override fun onResponse(call: Call<List<KhachHang>>, response: Response<List<KhachHang>>) {
                if (response.isSuccessful) {
                    val khachHang = response.body()?.find { it.get_id() == currentId }
                    if (khachHang != null) {
                        currentMaKH = khachHang.getMaKhachHang() ?: ""
                        displayKhachHangInfo(khachHang)
                    }
                } else {
                    Log.e(TAG, "API Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<KhachHang>>, t: Throwable) {
                Log.e(TAG, "Network Error", t)
            }
        })
    }

    private fun displayKhachHangInfo(khachHang: KhachHang) {
        try {
            val profileCard = binding.root.findViewById<TextView>(R.id.userName)
            profileCard?.text = khachHang.getTenKhachHang() ?: "Khách hàng"
        } catch (e: Exception) {
            Log.e(TAG, "Could not update user name", e)
        }

        try {
            val idText = binding.root.findViewById<TextView>(R.id.memberId)
            idText?.text = "ID: ${khachHang.getMaKhachHang()}"
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
