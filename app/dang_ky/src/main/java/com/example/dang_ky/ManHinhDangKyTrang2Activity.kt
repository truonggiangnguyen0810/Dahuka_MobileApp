package com.example.dang_ky

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ManHinhDangKyTrang2Activity : AppCompatActivity() {

    private lateinit var edtHoTen: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtNgaySinh: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var edtXacNhanMatKhau: EditText
    private lateinit var chkDongY: CheckBox
    private lateinit var btnDangKy: Button
    private lateinit var imgAnHienMatKhau: ImageView
    private lateinit var imgAnHienXacNhanMatKhau: ImageView

    private var dangHienMatKhau = false
    private var dangHienXacNhanMatKhau = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_hinh_dang_ky_trang_2)

        anhXaView()
        xuLyAnHienMatKhau()
        xuLyDangKy()
    }

    private fun anhXaView() {
        edtHoTen = findViewById(R.id.edtHoTen)
        edtEmail = findViewById(R.id.edtEmail)
        edtNgaySinh = findViewById(R.id.edtNgaySinh)
        edtMatKhau = findViewById(R.id.edtMatKhau)
        edtXacNhanMatKhau = findViewById(R.id.edtXacNhanMatKhau)
        chkDongY = findViewById(R.id.chkDongY)
        btnDangKy = findViewById(R.id.btnDangKy)
        imgAnHienMatKhau = findViewById(R.id.imgAnHienMatKhau)
        imgAnHienXacNhanMatKhau = findViewById(R.id.imgAnHienXacNhanMatKhau)
    }

    private fun xuLyAnHienMatKhau() {
        imgAnHienMatKhau.setOnClickListener {
            dangHienMatKhau = !dangHienMatKhau
            edtMatKhau.inputType =
                if (dangHienMatKhau)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            edtMatKhau.setSelection(edtMatKhau.text.length)
        }

        imgAnHienXacNhanMatKhau.setOnClickListener {
            dangHienXacNhanMatKhau = !dangHienXacNhanMatKhau
            edtXacNhanMatKhau.inputType =
                if (dangHienXacNhanMatKhau)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            edtXacNhanMatKhau.setSelection(edtXacNhanMatKhau.text.length)
        }
    }

    private fun xuLyDangKy() {
        btnDangKy.setOnClickListener {
            val hoTen = edtHoTen.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val ngaySinh = edtNgaySinh.text.toString().trim()
            val matKhau = edtMatKhau.text.toString().trim()
            val xacNhanMatKhau = edtXacNhanMatKhau.text.toString().trim()

            when {
                hoTen.isEmpty() -> {
                    edtHoTen.error = "Vui lòng nhập họ tên"
                    edtHoTen.requestFocus()
                }

                email.isEmpty() -> {
                    edtEmail.error = "Vui lòng nhập email"
                    edtEmail.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    edtEmail.error = "Email không hợp lệ"
                    edtEmail.requestFocus()
                }

                ngaySinh.isEmpty() -> {
                    edtNgaySinh.error = "Vui lòng nhập ngày sinh"
                    edtNgaySinh.requestFocus()
                }

                matKhau.isEmpty() -> {
                    edtMatKhau.error = "Vui lòng nhập mật khẩu"
                    edtMatKhau.requestFocus()
                }

                !kiemTraMatKhau(matKhau) -> {
                    edtMatKhau.error = "Mật khẩu chưa đúng định dạng"
                    edtMatKhau.requestFocus()
                }

                xacNhanMatKhau.isEmpty() -> {
                    edtXacNhanMatKhau.error = "Vui lòng nhập xác nhận mật khẩu"
                    edtXacNhanMatKhau.requestFocus()
                }

                xacNhanMatKhau != matKhau -> {
                    edtXacNhanMatKhau.error = "Mật khẩu không khớp"
                    edtXacNhanMatKhau.requestFocus()
                }

                !chkDongY.isChecked -> {
                    Toast.makeText(this, "Bạn phải đồng ý điều khoản", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    hienPopupThanhCong()
                }
            }
        }
    }

    private fun kiemTraMatKhau(matKhau: String): Boolean {
        return matKhau.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\\$!%*?&]).{10,}$".toRegex())
    }

    private fun hienPopupThanhCong() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_dang_ky_thanh_cong)
        dialog.setCancelable(false)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnTiepTucPopup = dialog.findViewById<Button>(R.id.btnTiepTucPopup)
        btnTiepTucPopup.setOnClickListener {
            dialog.dismiss()
            finishAffinity()
        }

        dialog.show()
    }
}