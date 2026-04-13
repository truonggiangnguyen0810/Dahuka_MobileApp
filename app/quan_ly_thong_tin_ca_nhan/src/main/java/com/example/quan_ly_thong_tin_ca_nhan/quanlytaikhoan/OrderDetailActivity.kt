package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quan_ly_thong_tin_ca_nhan.databinding.XemChiTietDonHangBinding

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: XemChiTietDonHangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = XemChiTietDonHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val productName = intent.getStringExtra("PRODUCT_NAME")
        val modelName = intent.getStringExtra("MODEL_NAME")
        val quantity = intent.getStringExtra("QUANTITY")
        val price = intent.getStringExtra("PRICE")
        val imageRes = intent.getIntExtra("IMAGE_RES", 0)

        binding.productName.text = productName
        binding.modelName.text = modelName
        binding.quantity.text = "Số lượng: $quantity"
        binding.price.text = price
        if (imageRes != 0) {
            binding.productImage.setImageResource(imageRes)
        }
    }
}
