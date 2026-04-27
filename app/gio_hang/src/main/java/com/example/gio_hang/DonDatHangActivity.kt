package com.example.gio_hang

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.common.UserManager
import com.example.common.model.CartItem
import com.example.common.model.DonHang
import com.example.common.network.RetrofitClient
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class DonDatHangActivity : AppCompatActivity() {

    private var isChuyenKhoan = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_don_dat_hang)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        @Suppress("UNCHECKED_CAST")
        val selectedItems = intent.getSerializableExtra("selectedItems") as? ArrayList<CartItem> ?: arrayListOf()
        val selectedMaSP = intent.getStringArrayListExtra("selectedMaSP") ?: arrayListOf()
        val maKhachHang = intent.getStringExtra("maKhachHang") ?: UserManager.getMaKhachHang(this) ?: "2"

        val containerItems = findViewById<LinearLayout>(R.id.containerOrderItems)
        containerItems.removeAllViews()
        val fmt = NumberFormat.getNumberInstance(Locale("vi", "VN"))
        selectedItems.forEach { item ->
            val card = layoutInflater.inflate(R.layout.item_order_don_hang, containerItems, false)
            card.findViewById<TextView>(R.id.tvItemName).text = item.getTenSanPham()
            card.findViewById<TextView>(R.id.tvItemQty).text = "Số lượng: ${item.getSoLuong()}"
            card.findViewById<TextView>(R.id.tvItemPrice).text = "${fmt.format(item.getDonGia())}đ"
            containerItems.addView(card)
        }

        val total = selectedItems.sumOf { it.getDonGia() * it.getSoLuong() }
        findViewById<TextView>(R.id.tvTamTinh).text = "${fmt.format(total)}đ"

        val layoutChuyenKhoan = findViewById<LinearLayout>(R.id.layoutChuyenKhoan)
        val layoutTienMat = findViewById<LinearLayout>(R.id.layoutTienMat)
        val rbChuyenKhoan = findViewById<RadioButton>(R.id.rbChuyenKhoan)
        val rbTienMat = findViewById<RadioButton>(R.id.rbTienMat)

        fun selectChuyenKhoan() {
            isChuyenKhoan = true; rbChuyenKhoan.isChecked = true; rbTienMat.isChecked = false
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_normal)
        }
        fun selectTienMat() {
            isChuyenKhoan = false; rbTienMat.isChecked = true; rbChuyenKhoan.isChecked = false
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_normal)
        }

        layoutChuyenKhoan.setOnClickListener { selectChuyenKhoan() }
        rbChuyenKhoan.setOnClickListener { selectChuyenKhoan() }
        layoutTienMat.setOnClickListener { selectTienMat() }
        rbTienMat.setOnClickListener { selectTienMat() }
        selectChuyenKhoan()

        findViewById<MaterialButton>(R.id.btnXacNhan).setOnClickListener {
            datHang(maKhachHang, selectedItems, selectedMaSP, total)
        }
    }

    // POST đơn hàng lên server
    private fun datHang(maKH: String, selectedItems: ArrayList<CartItem>, selectedMaSP: ArrayList<String>, total: Long) {
        val maDonHang = "DH_${System.currentTimeMillis() % 100000}"
        val donHang = DonHang().apply {
            setMaDonHang(maDonHang)
            setMaKhachHang(maKH)
            setTongSoLuong(selectedItems.sumOf { it.getSoLuong() })
            setTongThanhTien(total.toDouble())
            setTongThanhToan(total.toDouble())
            setTrangThaiDonHang("Chờ xác nhận")
            setPhuongThucVanChuyen(if (isChuyenKhoan) "Chuyển khoản" else "Tiền mặt")
            setGhiChu("")
        }

        RetrofitClient.getApiService().createDonHang(donHang).enqueue(object : Callback<DonHang> {
            override fun onResponse(call: Call<DonHang>, response: Response<DonHang>) {
                if (response.isSuccessful) {
                    // DELETE các sản phẩm đã đặt khỏi giỏ hàng
                    xoaKhoiGioHang(maKH, selectedMaSP)
                } else {
                    Toast.makeText(this@DonDatHangActivity, "Lỗi đặt hàng: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DonHang>, t: Throwable) {
                Toast.makeText(this@DonDatHangActivity, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // DELETE từng sản phẩm đã đặt khỏi giỏ hàng
    private fun xoaKhoiGioHang(maKH: String, selectedMaSP: ArrayList<String>) {
        if (selectedMaSP.isEmpty()) { chuyenManHinh(); return }
        val api = RetrofitClient.getApiService()
        var done = 0
        selectedMaSP.forEach { maSP ->
            api.deleteChiTietGioHang(maKH, maSP).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, r: Response<Void>) {
                    if (++done >= selectedMaSP.size) chuyenManHinh()
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    if (++done >= selectedMaSP.size) chuyenManHinh()
                }
            })
        }
    }

    private fun chuyenManHinh() {
        runOnUiThread {
            // Báo GioHangActivity reload
            setResult(RESULT_OK)
            if (isChuyenKhoan) {
                startActivity(Intent(this, ThanhToanQrActivity::class.java))
            } else {
                startActivity(Intent(this, DatHangThanhCongActivity::class.java))
            }
            finish()
        }
    }
}
