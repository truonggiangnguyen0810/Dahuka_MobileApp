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
    private var currentUserId: Int = -1

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
        currentUserId = userId

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
        RetrofitClient.getApiService().getAllKhachHang().enqueue(object : Callback<List<KhachHang>> {
            override fun onResponse(call: Call<List<KhachHang>>, response: Response<List<KhachHang>>) {
                if (response.isSuccessful) {
                    val khachHang = response.body()?.find { it.getId() == userId }
                    if (khachHang != null) {
                        currentId = khachHang.get_id() ?: ""
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
        personalInfoLayout.setOnClickListener {
            val intent = Intent(this, PersonalInfoActivity::class.java).apply {
                putExtra(PersonalInfoActivity.EXTRA_KHACH_HANG_ID, currentId)
            }
            startActivity(intent)
        }

        purchaseHistoryLayout.setOnClickListener {
            try {
                val intent = Intent(this, com.example.ql_don_hang.MainActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Không thể mở Lịch sử mua hàng", Toast.LENGTH_SHORT).show()
            }
        }

        changePasswordLayout.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        addressBookLayout.setOnClickListener {
            try {
                val intent = Intent(this, com.example.so_dia_chi.MainActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Không thể mở Sổ địa chỉ", Toast.LENGTH_SHORT).show()
            }
        }

        customerSupportLayout.setOnClickListener {
            Toast.makeText(this, "Tính năng Hỗ trợ khách hàng đang phát triển", Toast.LENGTH_SHORT).show()
        }

        logoutButton.setOnClickListener {
            UserManager.logout(this)
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show()
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
        if (currentUserId < 0) return
        RetrofitClient.getApiService().getAllDonHang()
            .enqueue(object : Callback<List<DonHang>> {
                override fun onResponse(
                    call: Call<List<DonHang>>,
                    response: Response<List<DonHang>>
                ) {
                    if (!response.isSuccessful || response.body() == null) return
                    val count = response.body()!!
                        .count { it.getMaKhachHang() == currentUserId }
                    tvSoDonHang.text = count.toString()
                }

                override fun onFailure(call: Call<List<DonHang>>, t: Throwable) {
                    Log.e(TAG, "Lỗi tải đơn hàng: ${t.message}")
                }
            })
    }
}
