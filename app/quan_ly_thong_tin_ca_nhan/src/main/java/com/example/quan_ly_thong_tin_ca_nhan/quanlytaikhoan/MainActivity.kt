package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.quan_ly_thong_tin_ca_nhan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.personalInfoLayout.setBounceClickEffect {
            val intent = Intent(this, PersonalInfoActivity::class.java)
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

