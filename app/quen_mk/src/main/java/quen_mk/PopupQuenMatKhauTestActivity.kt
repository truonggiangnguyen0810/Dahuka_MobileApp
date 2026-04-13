package com.example.quen_mk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PopupQuenMatKhauTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PopupQuenMatKhau.hienPopup(this)
    }
}