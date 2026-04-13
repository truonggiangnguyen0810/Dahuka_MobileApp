package com.example.gio_hang

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ThanhToanQrActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanh_toan_qr)

        val tvMinutes = findViewById<TextView>(R.id.tvMinutes)
        val tvSeconds = findViewById<TextView>(R.id.tvSeconds)

        countDownTimer = object : CountDownTimer(5 * 60 * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val totalSeconds = millisUntilFinished / 1000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                tvMinutes.text = String.format("%02d", minutes)
                tvSeconds.text = String.format("%02d", seconds)
            }

            override fun onFinish() {
                tvMinutes.text = "00"
                tvSeconds.text = "00"
                goToSuccess()
            }
        }.start()
    }

    private fun goToSuccess() {
        startActivity(Intent(this, DatHangThanhCongActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
