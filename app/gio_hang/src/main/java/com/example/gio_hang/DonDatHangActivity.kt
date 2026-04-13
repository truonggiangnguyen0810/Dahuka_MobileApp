package com.example.gio_hang

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class DonDatHangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_don_dat_hang)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val layoutChuyenKhoan = findViewById<LinearLayout>(R.id.layoutChuyenKhoan)
        val layoutTienMat = findViewById<LinearLayout>(R.id.layoutTienMat)
        val rbChuyenKhoan = findViewById<RadioButton>(R.id.rbChuyenKhoan)
        val rbTienMat = findViewById<RadioButton>(R.id.rbTienMat)

        layoutChuyenKhoan.setOnClickListener {
            rbChuyenKhoan.isChecked = true
            rbTienMat.isChecked = false
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_normal)
        }

        layoutTienMat.setOnClickListener {
            rbTienMat.isChecked = true
            rbChuyenKhoan.isChecked = false
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_normal)
        }

        findViewById<Button>(R.id.btnXacNhan).setOnClickListener {
            if (rbChuyenKhoan.isChecked) {
                startActivity(Intent(this, ThanhToanQrActivity::class.java))
            } else {
                startActivity(Intent(this, DatHangThanhCongActivity::class.java))
            }
        }
    }
}
