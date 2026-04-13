package com.example.quen_mk

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

object PopupQuenMatKhau {

    fun hienPopup(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_quen_mat_khau)
        dialog.setCancelable(true)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val edtSoDienThoai = dialog.findViewById<EditText>(R.id.edtSoDienThoaiQuenMk)
        val btnXacNhan = dialog.findViewById<Button>(R.id.btnXacNhanPopupQuenMk)

        btnXacNhan.setOnClickListener {
            val soDienThoai = edtSoDienThoai.text.toString().trim()

            if (soDienThoai.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }

        dialog.show()
    }
}