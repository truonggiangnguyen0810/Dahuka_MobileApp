package com.example.quen_mk

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_xac_minh_lai_mat_khau)

        val btnXacNhan = findViewById<Button>(R.id.btnXacNhan)

        // Cập nhật ID đúng từ layout man_hinh_xac_minh_lai_mat_khau.xml
        val edtMatKhau = findViewById<EditText>(R.id.edtPassword)
        val imgMatKhau = findViewById<ImageView>(R.id.imgShowPass1)

        val edtXacNhan = findViewById<EditText>(R.id.edtRePassword)
        val imgXacNhan = findViewById<ImageView>(R.id.imgShowPass2)

        var dangHienMatKhau = false
        var dangHienXacNhan = false

        imgMatKhau.setOnClickListener {
            if (dangHienMatKhau) {
                edtMatKhau.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                edtMatKhau.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            edtMatKhau.setSelection(edtMatKhau.text.length)
            dangHienMatKhau = !dangHienMatKhau
        }

        imgXacNhan.setOnClickListener {
            if (dangHienXacNhan) {
                edtXacNhan.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                edtXacNhan.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            edtXacNhan.setSelection(edtXacNhan.text.length)
            dangHienXacNhan = !dangHienXacNhan
        }

        btnXacNhan.setOnClickListener {
            hienPopupDoiMatKhauThanhCong()
        }
    }

    private fun hienPopupDoiMatKhauThanhCong() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_doi_mat_khau_thanh_cong)
        dialog.setCancelable(true)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnTiepTucPopup = dialog.findViewById<Button>(R.id.btnTiepTucPopupDoiMk)

        btnTiepTucPopup.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
