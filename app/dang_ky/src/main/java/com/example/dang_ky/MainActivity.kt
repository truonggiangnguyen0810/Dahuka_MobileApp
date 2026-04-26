package com.example.dang_ky

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.man_hinh_xac_minh_sdt)

        val edtSoDienThoai = findViewById<EditText>(R.id.edtSoDienThoai)
        val btnTiepTuc = findViewById<Button>(R.id.btnTiepTuc)

        btnTiepTuc.setOnClickListener {
            val soDienThoai = edtSoDienThoai.text.toString().trim()

            when {
                soDienThoai.isEmpty() -> {
                    edtSoDienThoai.error = "Vui lòng nhập số điện thoại"
                    edtSoDienThoai.requestFocus()
                }

                !soDienThoai.matches(Regex("^(0|84)?[0-9]{9,10}$")) -> {
                    edtSoDienThoai.error = "Số điện thoại không hợp lệ"
                    edtSoDienThoai.requestFocus()
                }

                else -> {
                    val intent = Intent(this, ManHinhDangKyTrang2Activity::class.java)
                    intent.putExtra("so_dien_thoai", soDienThoai)
                    startActivity(intent)
                }
            }
        }
    }
}
