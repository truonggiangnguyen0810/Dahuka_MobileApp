package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.ThongTinCaNhanBinding
import com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api.KhachHang
import com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan.api.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.LayoutInflater
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var binding: ThongTinCaNhanBinding
    private var currentKhachHang: KhachHang? = null

    companion object {
        const val EXTRA_KHACH_HANG_ID = "KHACH_HANG_ID"
        private const val TAG = "PersonalInfoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ThongTinCaNhanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.editAvatarButton.setBounceClickEffect {
            showAvatarChangeOptions()
        }

        binding.dobLayout.setOnClickListener {
            showDatePicker()
        }

        binding.saveButton.setBounceClickEffect {
            saveCustomerInfo()
        }

        // Load customer data from API
        val khachHangId = intent.getStringExtra(EXTRA_KHACH_HANG_ID) ?: "69d923c97922bf3246b90b79"
        loadKhachHangData(khachHangId)
    }

    private fun loadKhachHangData(khachHangId: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.khachHangApi.getAllKhachHang()
                if (response.isSuccessful) {
                    val khachHangList = response.body()
                    val khachHang = khachHangList?.find { it.id == khachHangId }
                    if (khachHang != null) {
                        currentKhachHang = khachHang
                        displayKhachHangData(khachHang)
                    } else {
                        showSuccessToast("Không tìm thấy khách hàng: $khachHangId")
                    }
                } else {
                    Log.e(TAG, "API Error: ${response.code()} - ${response.message()}")
                    showSuccessToast("Lỗi tải dữ liệu: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network Error", e)
                showSuccessToast("Lỗi kết nối: ${e.localizedMessage}")
            }
        }
    }

    private fun displayKhachHangData(khachHang: KhachHang) {
        binding.nameInput.setText(khachHang.tenKhachHang ?: "")

        // Format phone number with leading zero
        val phoneNumber = khachHang.sdt?.toString() ?: ""
        val formattedPhone = if (phoneNumber.length == 9) "0$phoneNumber" else phoneNumber
        binding.phoneInput.setText(formattedPhone)

        binding.emailInput.setText(khachHang.email ?: "")

        // Parse and format birth date
        val ngaySinh = khachHang.ngaySinh
        if (!ngaySinh.isNullOrEmpty()) {
            try {
                // API returns format like "Fri Jun 10 1988 07:00:00 GMT+0700 (Indochina Time)"
                // Extract just the date part
                val parts = ngaySinh.split(" ")
                if (parts.size >= 4) {
                    val monthStr = parts[1]
                    val day = parts[2]
                    val year = parts[3]
                    val monthMap = mapOf(
                        "Jan" to "01", "Feb" to "02", "Mar" to "03", "Apr" to "04",
                        "May" to "05", "Jun" to "06", "Jul" to "07", "Aug" to "08",
                        "Sep" to "09", "Oct" to "10", "Nov" to "11", "Dec" to "12"
                    )
                    val month = monthMap[monthStr] ?: "01"
                    binding.dobText.text = "$day/$month/$year"
                }
            } catch (e: Exception) {
                Log.e(TAG, "Date parse error", e)
                binding.dobText.text = ngaySinh
            }
        }

        // Set gender radio button
        when (khachHang.gioiTinh) {
            "Nam" -> binding.radioMale.isChecked = true
            "Nữ" -> binding.radioFemale.isChecked = true
            "Khác" -> binding.radioOther.isChecked = true
        }
    }

    private fun saveCustomerInfo() {
        val maKH = currentKhachHang?.maKhachHang ?: return

        val selectedGender = when (binding.genderGroup.checkedRadioButtonId) {
            R.id.radioMale -> "Nam"
            R.id.radioFemale -> "Nữ"
            R.id.radioOther -> "Khác"
            else -> "Nam"
        }

        val updatedKhachHang = currentKhachHang?.copy(
            tenKhachHang = binding.nameInput.text.toString(),
            email = binding.emailInput.text.toString(),
            sdt = binding.phoneInput.text.toString().replace(" ", "").toLongOrNull(),
            gioiTinh = selectedGender
        )

        if (updatedKhachHang == null) {
            showSuccessToast("Không có dữ liệu để lưu")
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.khachHangApi.updateKhachHang(maKH, updatedKhachHang)
                if (response.isSuccessful) {
                    currentKhachHang = response.body() ?: updatedKhachHang
                    showSuccessToast("Đã lưu thay đổi thành công!")
                } else {
                    Log.e(TAG, "Save Error: ${response.code()}")
                    showSuccessToast("Lỗi lưu dữ liệu: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Save Network Error", e)
                showSuccessToast("Lỗi kết nối khi lưu: ${e.localizedMessage}")
            }
        }
    }

    private fun showAvatarChangeOptions() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.doi_anh_dai_dien, null)
        bottomSheetDialog.setContentView(view)

        view.findViewById<android.view.View>(R.id.optionCamera).setOnClickListener {
            showSuccessToast("Mở camera...")
            bottomSheetDialog.dismiss()
        }

        view.findViewById<android.view.View>(R.id.optionGallery).setOnClickListener {
            showSuccessToast("Mở thư viện ảnh...")
            bottomSheetDialog.dismiss()
        }

        view.findViewById<android.view.View>(R.id.optionDelete).setOnClickListener {
            showSuccessToast("Đã gỡ ảnh đại diện")
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun showDatePicker() {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = android.app.DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                binding.dobText.text = date
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
