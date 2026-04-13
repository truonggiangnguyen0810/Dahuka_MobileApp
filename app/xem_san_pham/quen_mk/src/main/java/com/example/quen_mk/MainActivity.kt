//package com.example.quen_mk
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.man_hinh_quen_mat_khau)
//    }
//}

//package com.example.quen_mk
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.man_hinh_quen_mat_khau)
//    }
//}

//package com.example.quen_mk
//
//import android.app.Dialog
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.view.ViewGroup
//import android.view.Window
//import android.widget.Button
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.man_hinh_quen_mat_khau)
//
//        val btnXacNhan = findViewById<Button>(R.id.btnXacNhan)
//
//        btnXacNhan.setOnClickListener {
//            hienPopupDoiMatKhauThanhCong()
//        }
//    }
//
//    private fun hienPopupDoiMatKhauThanhCong() {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.popup_doi_mat_khau_thanh_cong)
//        dialog.setCancelable(true)
//
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        val btnTiepTucPopup = dialog.findViewById<Button>(R.id.btnTiepTucPopupDoiMk)
//
//        btnTiepTucPopup.setOnClickListener {
//            dialog.dismiss()
//        }//Sau này muốn chuyển từ nút tiếp tục sang màn hình đăng nhập, thì thêm finish() vào đây để kết thúc MainActivity, rồi trong Intent chuyển sang ManHinhDangNhapActivity
//
//        dialog.show()
//    }
//}

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
        setContentView(R.layout.man_hinh_quen_mat_khau)

        val btnXacNhan = findViewById<Button>(R.id.btnXacNhan)

        // 🔥 THÊM PHẦN NÀY
        val edtMatKhau = findViewById<EditText>(R.id.edtMatKhauMoi)
        val imgMatKhau = findViewById<ImageView>(R.id.imgAnHienMatKhau)

        val edtXacNhan = findViewById<EditText>(R.id.edtXacNhanMatKhau)
        val imgXacNhan = findViewById<ImageView>(R.id.imgAnHienXacNhanMatKhau)

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

        // 🔥 CODE CŨ CỦA T
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