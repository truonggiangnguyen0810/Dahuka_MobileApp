package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.example.quan_ly_thong_tin_ca_nhan.databinding.ThongTinCaNhanBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.LayoutInflater

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var binding: ThongTinCaNhanBinding

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
            showSuccessToast("Đã lưu thay đổi")
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
