package com.example.dang_ky

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.common.model.AuthUser
import com.example.common.model.KhachHang
import com.example.common.model.RegisterResponse
import com.example.common.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ManHinhDangKyTrang2Activity : AppCompatActivity() {

    private lateinit var edtHoTen: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtNgaySinh: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var edtXacNhanMatKhau: EditText
    private lateinit var btnDangKy: Button
    private lateinit var imgAnHienMatKhau: ImageView
    private lateinit var imgAnHienXacNhanMatKhau: ImageView
    private lateinit var progressBar: ProgressBar

    private var dangHienMatKhau = false
    private var dangHienXacNhanMatKhau = false
    private var soDienThoai: String = ""
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhap_thong_tin_ca_nhan)

        soDienThoai = intent.getStringExtra("so_dien_thoai") ?: ""

        anhXaView()
        xuLyChonNgaySinh()
        xuLyAnHienMatKhau()
        xuLyDangKy()
    }

    private fun anhXaView() {
        edtHoTen = findViewById(R.id.edtHoTen)
        edtEmail = findViewById(R.id.edtEmail)
        edtNgaySinh = findViewById(R.id.edtNgaySinh)
        edtMatKhau = findViewById(R.id.edtMatKhau)
        edtXacNhanMatKhau = findViewById(R.id.edtXacNhanMatKhau)
        btnDangKy = findViewById(R.id.btnDangKy)
        imgAnHienMatKhau = findViewById(R.id.imgAnHienMatKhau)
        imgAnHienXacNhanMatKhau = findViewById(R.id.imgAnHienXacNhanMatKhau)
        progressBar = ProgressBar(this).apply {
            visibility = View.GONE
        }
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
                    selectedDate = dateFormat.format(cal.time)
                    edtNgaySinh.setText(selectedDate)
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
                    goiApiDangKy(hoTen, email, ngaySinh, matKhau)
                }
            }
        }
    }

    private fun kiemTraMatKhau(matKhau: String): Boolean {
        return matKhau.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\\$!%*?&]).{10,}$".toRegex())
    }

    private fun goiApiDangKy(hoTen: String, email: String, ngaySinh: String, matKhau: String) {
        btnDangKy.isEnabled = false
        btnDangKy.text = "Đang đăng ký..."

        val authUser = AuthUser()
        authUser.setUsername(soDienThoai)
        authUser.setPassword(matKhau)
        authUser.setRole("user")
        authUser.setStaff(false)
        authUser.setActive(true)
        authUser.setSuperuser(false)

        val apiService = RetrofitClient.getApiService()
        apiService.register(authUser).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    taoKhachHang(hoTen, email, ngaySinh)
                } else {
                    btnDangKy.isEnabled = true
                    btnDangKy.text = "Đăng ký"
                    val errorMsg = when (response.code()) {
                        400 -> "Tài khoản đã tồn tại"
                        else -> "Đăng ký thất bại (mã ${response.code()})"
                    }
                    Toast.makeText(this@ManHinhDangKyTrang2Activity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                btnDangKy.isEnabled = true
                btnDangKy.text = "Đăng ký"
                Toast.makeText(
                    this@ManHinhDangKyTrang2Activity,
                    "Lỗi kết nối: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun taoKhachHang(hoTen: String, email: String, ngaySinh: String) {
        val khachHang = KhachHang()
        khachHang.setTenKhachHang(hoTen)
        khachHang.setEmail(email)
        khachHang.setSdt(soDienThoai.toIntOrNull() ?: 0)
        if (ngaySinh.isNotEmpty()) {
            khachHang.setNgaySinh(ngaySinh)
        }

        val apiService = RetrofitClient.getApiService()
        apiService.createKhachHang(khachHang).enqueue(object : Callback<KhachHang> {
            override fun onResponse(call: Call<KhachHang>, response: Response<KhachHang>) {
                btnDangKy.isEnabled = true
                btnDangKy.text = "Đăng ký"
                hienPopupThanhCong()
            }

            override fun onFailure(call: Call<KhachHang>, t: Throwable) {
                btnDangKy.isEnabled = true
                btnDangKy.text = "Đăng ký"
                hienPopupThanhCong()
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
            finishAffinity()
        }

        dialog.show()
    }
}
