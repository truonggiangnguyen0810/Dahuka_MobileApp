//package com.example.dang_ky
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//
//class ManHinhDangKyTrang2Activity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.man_hinh_dang_ky_trang_2)
//    }
//}

package com.example.dang_ky

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ManHinhDangKyTrang2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_dang_ky_trang_2)

        val btnDangKy = findViewById<Button>(R.id.btnDangKy)

        btnDangKy.setOnClickListener {
            hienPopupDangKyThanhCong()
        }
    }

    private fun hienPopupDangKyThanhCong() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_dang_ky_thanh_cong)
        dialog.setCancelable(true)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnTiepTucPopup = dialog.findViewById<Button>(R.id.btnTiepTucPopup)

        btnTiepTucPopup.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}