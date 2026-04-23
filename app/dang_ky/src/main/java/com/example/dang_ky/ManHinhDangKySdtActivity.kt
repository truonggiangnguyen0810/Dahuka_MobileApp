package com.example.dang_ky

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ManHinhDangKySdtActivity : AppCompatActivity() {

    private lateinit var edtSoDienThoai: EditText
    private lateinit var btnTiepTuc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_dang_ky_sdt)

        edtSoDienThoai = findViewById(R.id.edtSoDienThoai)
        btnTiepTuc = findViewById(R.id.btnTiepTuc)

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