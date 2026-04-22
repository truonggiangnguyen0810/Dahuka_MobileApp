package com.example.gio_hang

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ThanhToanQrActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanh_toan_qr)

        // X -> quay về DonDatHangActivity
        findViewById<ImageButton>(R.id.btnClose).setOnClickListener {
            finish()
        }

        // Nút xác nhận đã thanh toán -> đặt hàng thành công
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnXacNhanThanhToan).setOnClickListener {
            goToSuccess()
        }

        val tvMinutes = findViewById<TextView>(R.id.tvMinutes)
        val tvSeconds = findViewById<TextView>(R.id.tvSeconds)

        countDownTimer = object : CountDownTimer(5 * 60 * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val totalSeconds = millisUntilFinished / 1000
                tvMinutes.text = String.format("%02d", totalSeconds / 60)
                tvSeconds.text = String.format("%02d", totalSeconds % 60)
            }

            override fun onFinish() {
                tvMinutes.text = "00"
                tvSeconds.text = "00"
            }
        }.start()
    }

    private fun goToSuccess() {
        countDownTimer?.cancel()
        startActivity(Intent(this, DatHangThanhCongActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
