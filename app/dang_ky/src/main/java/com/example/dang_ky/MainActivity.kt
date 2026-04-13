package com.example.dang_ky

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.man_hinh_dang_ky_sdt)

        val btnTiepTuc = findViewById<Button>(R.id.btnTiepTuc)

        btnTiepTuc.setOnClickListener {
            val intent = Intent(this, ManHinhDangKyTrang2Activity::class.java)
            startActivity(intent)
        }
    }
}
