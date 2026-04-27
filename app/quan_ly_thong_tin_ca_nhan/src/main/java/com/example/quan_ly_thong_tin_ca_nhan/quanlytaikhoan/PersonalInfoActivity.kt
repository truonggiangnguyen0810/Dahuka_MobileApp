package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.common.UserManager
import com.example.common.model.KhachHang
import com.example.common.network.RetrofitClient
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalInfoActivity : AppCompatActivity() {

    private var currentKhachHang: KhachHang? = null

    private lateinit var nameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var dobText: TextView
    private lateinit var dobLayout: LinearLayout
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private lateinit var radioOther: RadioButton
    private lateinit var saveButton: MaterialButton
    private lateinit var editAvatarButton: View

    companion object {
        const val EXTRA_KHACH_HANG_ID = "KHACH_HANG_ID"
        private const val TAG = "PersonalInfoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thong_tin_ca_nhan)

        anhXaView()
        setupEvents()
        loadKhachHangData()
    }

    private fun anhXaView() {
        nameInput = findViewById(R.id.nameInput)
        phoneInput = findViewById(R.id.phoneInput)
        emailInput = findViewById(R.id.emailInput)
        dobText = findViewById(R.id.dobText)
        dobLayout = findViewById(R.id.dobLayout)
        radioMale = findViewById(R.id.radioMale)
        radioFemale = findViewById(R.id.radioFemale)
        radioOther = findViewById(R.id.radioOther)
        saveButton = findViewById(R.id.saveButton)
        editAvatarButton = findViewById(R.id.editAvatarButton)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupEvents() {
        editAvatarButton.setBounceClickEffect { showAvatarChangeOptions() }
        dobLayout.setOnClickListener { showDatePicker() }
        saveButton.setBounceClickEffect { saveCustomerInfo() }
    }

    private fun loadKhachHangData() {
        val userId = UserManager.getUserId(this)
        if (userId < 0) {
            showSuccessToast("Không tìm thấy thông tin người dùng")
            return
        }
        Log.d(TAG, "loadKhachHangData: userId=$userId")

        RetrofitClient.getApiService().getAllKhachHang().enqueue(object : Callback<List<KhachHang>> {
            override fun onResponse(call: Call<List<KhachHang>>, response: Response<List<KhachHang>>) {
                if (response.isSuccessful) {
                    val khachHang = response.body()?.find { it.getId() == userId }
                    if (khachHang != null) {
                        currentKhachHang = khachHang
                        displayKhachHangData(khachHang)
                    } else {
                        showSuccessToast("Không tìm thấy khách hàng")
                    }
                } else {
                    Log.e(TAG, "API Error: ${response.code()}")
                    showSuccessToast("Lỗi tải dữ liệu: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<KhachHang>>, t: Throwable) {
                Log.e(TAG, "Network Error", t)
                showSuccessToast("Lỗi kết nối: ${t.localizedMessage}")
            }
        })
    }

    private fun displayKhachHangData(khachHang: KhachHang) {
        nameInput.setText(khachHang.getTenKhachHang() ?: "")

        val phoneNumber = khachHang.getSdt().toString()
        val formattedPhone = if (phoneNumber.length == 9) "0$phoneNumber" else phoneNumber
        phoneInput.setText(formattedPhone)

        emailInput.setText(khachHang.getEmail() ?: "")

        val ngaySinh = khachHang.getNgaySinh()
        if (!ngaySinh.isNullOrEmpty()) {
            try {
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
                    dobText.text = "$day/$month/$year"
                }
            } catch (e: Exception) {
                Log.e(TAG, "Date parse error", e)
                dobText.text = ngaySinh
            }
        }

        when (khachHang.getGioiTinh()) {
            "Nam" -> radioMale.isChecked = true
            "Nữ" -> radioFemale.isChecked = true
            "Khác" -> radioOther.isChecked = true
        }
    }

    private fun saveCustomerInfo() {
        val khachHangId = currentKhachHang?.getId()?.toString() ?: return

        val selectedGender = when {
            radioMale.isChecked -> "Nam"
            radioFemale.isChecked -> "Nữ"
            radioOther.isChecked -> "Khác"
            else -> "Nam"
        }

        val updatedKhachHang = KhachHang().apply {
            setId(currentKhachHang?.getId() ?: 0)
            setTenKhachHang(nameInput.text.toString())
            setEmail(emailInput.text.toString())
            setSdt(phoneInput.text.toString().replace(" ", "").toIntOrNull() ?: 0)
            setGioiTinh(selectedGender)
            setNgaySinh(convertToISODate(dobText.text.toString()))
        }

        KhachHangApiService.api.updateKhachHang(khachHangId, updatedKhachHang)
            .enqueue(object : Callback<KhachHang> {
                override fun onResponse(call: Call<KhachHang>, response: Response<KhachHang>) {
                    if (response.isSuccessful) {
                        currentKhachHang = response.body() ?: updatedKhachHang
                        showSuccessToast("Đã lưu thay đổi thành công!")
                    } else {
                        Log.e(TAG, "Save Error: ${response.code()}")
                        showSuccessToast("Lỗi lưu dữ liệu: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<KhachHang>, t: Throwable) {
                    Log.e(TAG, "Save Network Error", t)
                    showSuccessToast("Lỗi kết nối khi lưu: ${t.localizedMessage}")
                }
            })
    }

    private fun convertToISODate(dateStr: String): String? {
        if (dateStr.isBlank()) return null
        return try {
            val parts = dateStr.split("/")
            if (parts.size == 3) {
                "${parts[2]}-${parts[1]}-${parts[0]}T00:00:00.000Z"
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Date convert error", e)
            null
        }
    }

    private fun showAvatarChangeOptions() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.doi_anh_dai_dien, null)
        bottomSheetDialog.setContentView(view)

        view.findViewById<View>(R.id.optionCamera).setOnClickListener {
            showSuccessToast("Mở camera...")
            bottomSheetDialog.dismiss()
        }

        view.findViewById<View>(R.id.optionGallery).setOnClickListener {
            showSuccessToast("Mở thư viện ảnh...")
            bottomSheetDialog.dismiss()
        }

        view.findViewById<View>(R.id.optionDelete).setOnClickListener {
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
                dobText.text = date
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
