package com.example.gio_hang

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
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
            showSuccessToast()
            finish()
        }
    }

    private fun showSuccessToast() {
        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.toast_cap_nhat_thanh_cong, null)
        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 80)
        toast.show()
    }
}
