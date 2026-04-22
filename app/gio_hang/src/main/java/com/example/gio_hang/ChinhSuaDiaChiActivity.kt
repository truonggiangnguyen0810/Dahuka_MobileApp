package com.example.gio_hang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ChinhSuaDiaChiActivity : AppCompatActivity() {

    private var isNhaRieng = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chinh_sua_dia_chi)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val layoutNhaRieng = findViewById<LinearLayout>(R.id.layoutNhaRieng)
        val layoutVanPhong = findViewById<LinearLayout>(R.id.layoutVanPhong)

        layoutNhaRieng.setOnClickListener {
            isNhaRieng = true
            layoutNhaRieng.setBackgroundResource(R.drawable.bg_address_type_selected)
            layoutVanPhong.setBackgroundResource(R.drawable.bg_address_type_normal)
        }

        layoutVanPhong.setOnClickListener {
            isNhaRieng = false
            layoutVanPhong.setBackgroundResource(R.drawable.bg_address_type_selected)
            layoutNhaRieng.setBackgroundResource(R.drawable.bg_address_type_normal)
        }

        val switchMacDinh = findViewById<Switch>(R.id.switchMacDinh)
        switchMacDinh.setOnCheckedChangeListener { _, isChecked ->
            switchMacDinh.trackTintList = android.content.res.ColorStateList.valueOf(
                if (isChecked) 0xFF1B7A4A.toInt() else 0xFFCCCCCC.toInt()
            )
        }

        findViewById<MaterialButton>(R.id.btnHoanThanh).setOnClickListener {
            val hoTen = findViewById<EditText>(R.id.etHoTen).text.toString().trim()
            val sdt = findViewById<EditText>(R.id.etSoDienThoai).text.toString().trim()
            val tinhThanh = findViewById<EditText>(R.id.etTinhThanh).text.toString().trim()
            val quanHuyen = findViewById<EditText>(R.id.etQuanHuyen).text.toString().trim()
            val phuongXa = findViewById<EditText>(R.id.etPhuongXa).text.toString().trim()
            val tenDuong = findViewById<EditText>(R.id.etTenDuong).text.toString().trim()

            val diaChiDayDu = listOf(phuongXa, quanHuyen, tinhThanh)
                .filter { it.isNotEmpty() }
                .joinToString(", ")

            val switchMacDinh = findViewById<Switch>(R.id.switchMacDinh)

            // Luôn lưu vào sổ địa chỉ, switch chỉ đánh dấu mặc định
            DiaChiPrefs.saveAddress(this, DiaChiItem(
                hoTen = hoTen,
                soDienThoai = sdt,
                tenDuong = tenDuong,
                diaChi = diaChiDayDu,
                isMacDinh = switchMacDinh.isChecked
            ))

            val intent = Intent().apply {
                putExtra("hoTen", hoTen)
                putExtra("soDienThoai", sdt)
                putExtra("diaChi", diaChiDayDu)
                putExtra("tenDuong", tenDuong)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
