package com.example.dang_ky

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.common.UserManager
import com.example.common.model.AuthUser
import com.example.common.model.RegisterResponse
import com.example.common.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ManHinhDangKySdtActivity : AppCompatActivity() {

    private lateinit var edtSoDienThoai: EditText
    private lateinit var edtHoTen: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtNgaySinh: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var edtXacNhanMatKhau: EditText
    private lateinit var btnDangKy: Button
    private lateinit var imgAnHienMatKhau: ImageView
    private lateinit var imgAnHienXacNhanMatKhau: ImageView

    private var dangHienMatKhau = false
    private var dangHienXacNhanMatKhau = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhap_thong_tin_ca_nhan)

        anhXaView()
        xuLyChonNgaySinh()
        xuLyAnHienMatKhau()
        xuLyDangKy()
    }

    private fun anhXaView() {
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai)
        edtHoTen = findViewById(R.id.edtHoTen)
        edtEmail = findViewById(R.id.edtEmail)
        edtNgaySinh = findViewById(R.id.edtNgaySinh)
        edtMatKhau = findViewById(R.id.edtMatKhau)
        edtXacNhanMatKhau = findViewById(R.id.edtXacNhanMatKhau)
        btnDangKy = findViewById(R.id.btnDangKy)
        imgAnHienMatKhau = findViewById(R.id.imgAnHienMatKhau)
        imgAnHienXacNhanMatKhau = findViewById(R.id.imgAnHienXacNhanMatKhau)
    }

    private fun xuLyChonNgaySinh() {
        edtNgaySinh.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val cal = Calendar.getInstance()
                    cal.set(selectedYear, selectedMonth, selectedDay)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    edtNgaySinh.setText(dateFormat.format(cal.time))
                },
                year, month, day
            )
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }
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
            val soDienThoai = edtSoDienThoai.text.toString().trim()
            val hoTen = edtHoTen.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val ngaySinh = edtNgaySinh.text.toString().trim()
            val matKhau = edtMatKhau.text.toString().trim()
            val xacNhanMatKhau = edtXacNhanMatKhau.text.toString().trim()

            when {
                soDienThoai.isEmpty() -> {
                    edtSoDienThoai.error = "Vui lòng nhập số điện thoại"
                    edtSoDienThoai.requestFocus()
                }

                !soDienThoai.matches(Regex("^(0|84)?[0-9]{9,10}$")) -> {
                    edtSoDienThoai.error = "Số điện thoại không hợp lệ"
                    edtSoDienThoai.requestFocus()
                }

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

                else -> {
                    goiApiDangKy(soDienThoai, hoTen, email, ngaySinh, matKhau)
                }
            }
        }
    }

    private fun kiemTraMatKhau(matKhau: String): Boolean {
        return matKhau.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\\$!%*?&]).{10,}$".toRegex())
    }

    private fun goiApiDangKy(
        soDienThoai: String,
        hoTen: String,
        email: String,
        ngaySinh: String,
        matKhau: String
    ) {
        btnDangKy.isEnabled = false
        btnDangKy.text = "Đang đăng ký..."

        val authUser = AuthUser()
        authUser.setUsername(soDienThoai)
        authUser.setPassword(matKhau)
        authUser.setRole("user")
        authUser.setIsStaff("false")
        authUser.setIsActive("true")
        authUser.setIsSuperuser("false")
        authUser.setTenKhachHang(hoTen)
        authUser.setEmail(email)
        if (ngaySinh.isNotEmpty()) {
            authUser.setNgaySinh(ngaySinh)
        }

        val apiService = RetrofitClient.getApiService()
        apiService.register(authUser).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                btnDangKy.isEnabled = true
                btnDangKy.text = "Đăng ký"

                val registerResponse = response.body()
                if (response.isSuccessful && registerResponse != null) {
                    UserManager.saveLogin(
                        this@ManHinhDangKySdtActivity,
                        registerResponse.id,
                        soDienThoai,
                        "user"
                    )

                    hienPopupThanhCong()
                } else {
                    val errorMsg = when (response.code()) {
                        400 -> "Tài khoản đã tồn tại"
                        else -> "Đăng ký thất bại (mã ${response.code()})"
                    }
                    Toast.makeText(this@ManHinhDangKySdtActivity, errorMsg, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                btnDangKy.isEnabled = true
                btnDangKy.text = "Đăng ký"
                Toast.makeText(
                    this@ManHinhDangKySdtActivity,
                    "Lỗi kết nối: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun hienPopupThanhCong() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_dang_ky_thanh_cong)
        dialog.setCancelable(false)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnTiepTucPopup = dialog.findViewById<Button>(R.id.btnTiepTucPopup)
        btnTiepTucPopup.setOnClickListener {
            dialog.dismiss()
            try {
                // Use string-based intent to avoid circular dependency
                val intent = Intent()
                intent.setClassName(this, "com.example.xem_san_pham.ManHinhDanhSachSanPhamActivity")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finishAffinity()
            } catch (e: Exception) {
                Toast.makeText(this, "Không thể mở màn hình sản phẩm", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
