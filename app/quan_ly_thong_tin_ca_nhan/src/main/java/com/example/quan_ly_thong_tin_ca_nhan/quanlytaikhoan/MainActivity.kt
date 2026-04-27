package com.example.quan_ly_thong_tin_ca_nhan.quanlytaikhoan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.common.UserManager
import com.example.common.model.DonHang
import com.example.common.model.KhachHang
import com.example.common.network.RetrofitClient
import com.example.quan_ly_thong_tin_ca_nhan.R
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var currentId: String = ""
    private var currentMaKH: String = ""

    private lateinit var tvUserName: TextView
    private lateinit var tvSoDonHang: TextView
    private lateinit var personalInfoLayout: LinearLayout
    private lateinit var purchaseHistoryLayout: LinearLayout
    private lateinit var changePasswordLayout: LinearLayout
    private lateinit var addressBookLayout: LinearLayout
    private lateinit var customerSupportLayout: LinearLayout
    private lateinit var logoutButton: MaterialButton

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: started")

        val userId = UserManager.getUserId(this)
        Log.d(TAG, "onCreate: userId=$userId, isLoggedIn=${UserManager.isLoggedIn(this)}")
        if (userId < 0) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        currentId = intent.getStringExtra("khach_hang_id") ?: ""

        setContentView(R.layout.activity_quan_ly_tai_khoan)
        Log.d(TAG, "onCreate: setContentView done")

        anhXaView()
        setupClickListeners()
        loadKhachHangInfo()
    }

    private fun anhXaView() {
        tvUserName = findViewById(R.id.userName)
        tvSoDonHang = findViewById(R.id.tvSoDonHang)
        personalInfoLayout = findViewById(R.id.personalInfoLayout)
        purchaseHistoryLayout = findViewById(R.id.purchaseHistoryLayout)
        changePasswordLayout = findViewById(R.id.changePasswordLayout)
        addressBookLayout = findViewById(R.id.addressBookLayout)
        customerSupportLayout = findViewById(R.id.customerSupportLayout)
        logoutButton = findViewById(R.id.logoutButton)
    }

    private fun loadKhachHangInfo() {
        val userId = UserManager.getUserId(this)
        if (userId < 0) return
        val maKH = String.format("KH_%03d", userId)
        RetrofitClient.getApiService().getAllKhachHang().enqueue(object : Callback<List<KhachHang>> {
            override fun onResponse(call: Call<List<KhachHang>>, response: Response<List<KhachHang>>) {
                if (response.isSuccessful) {
                    val khachHang = response.body()?.find { it.getId() == userId }
                    if (khachHang != null) {
                        currentId = khachHang.get_id() ?: ""
                        currentMaKH = maKH
                        displayKhachHangInfo(khachHang)
                        loadSoDonHang()
                    }
                } else {
                    Log.e(TAG, "API Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<KhachHang>>, t: Throwable) {
                Log.e(TAG, "Network Error", t)
            }
        })
    }

    private fun displayKhachHangInfo(khachHang: KhachHang) {
        tvUserName.text = khachHang.getTenKhachHang() ?: "Khách hàng"
    }

    override fun onResume() {
        super.onResume()
        loadKhachHangInfo()
    }

    private fun setupClickListeners() {
        personalInfoLayout.setBounceClickEffect {
            val intent = Intent(this, PersonalInfoActivity::class.java).apply {
                putExtra(PersonalInfoActivity.EXTRA_KHACH_HANG_ID, currentId)
            }
            startActivity(intent)
        }

        purchaseHistoryLayout.setBounceClickEffect {
            val intent = Intent(this, PurchaseHistoryActivity::class.java).apply {
                putExtra(PurchaseHistoryActivity.EXTRA_MA_KHACH_HANG, currentMaKH)
            }
            startActivity(intent)
        }

        changePasswordLayout.setBounceClickEffect {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        addressBookLayout.setBounceClickEffect {
            showSuccessToast("Tính năng Sổ địa chỉ đang phát triển")
        }

        customerSupportLayout.setBounceClickEffect {
            showSuccessToast("Tính năng Hỗ trợ khách hàng đang phát triển")
        }

        logoutButton.setBounceClickEffect {
            UserManager.logout(this)
            showSuccessToast("Đã đăng xuất")
            try {
                val clazz = Class.forName("com.example.common.TrangChuActivity")
                val intent = Intent(this, clazz)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } catch (e: ClassNotFoundException) {
                finish()
            }
        }
    }

    private fun loadSoDonHang() {
        if (currentMaKH.isEmpty()) return
        RetrofitClient.getApiService().getAllDonHang()
            .enqueue(object : Callback<List<DonHang>> {
                override fun onResponse(
                    call: Call<List<DonHang>>,
                    response: Response<List<DonHang>>
                ) {
                    if (!response.isSuccessful || response.body() == null) return
                    val count = response.body()!!
                        .count { it.getMaKhachHang() == currentMaKH }
                    tvSoDonHang.text = count.toString()
                }

                override fun onFailure(call: Call<List<DonHang>>, t: Throwable) {
                    Log.e(TAG, "Lỗi tải đơn hàng: ${t.message}")
                }
            })
    }
}
