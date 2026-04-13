package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quan_ly_thong_tin_ca_nhan.databinding.DoiMatKhauBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: DoiMatKhauBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoiMatKhauBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.updateButton.setBounceClickEffect {
            showSuccessToast("Đã cập nhật mật khẩu")
        }
    }
}
