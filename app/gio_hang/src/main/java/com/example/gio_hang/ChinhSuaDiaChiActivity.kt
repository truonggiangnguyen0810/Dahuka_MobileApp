package com.example.gio_hang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.common.UserManager
import com.example.common.model.SoDiaChi
import com.example.common.network.RetrofitClient
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChinhSuaDiaChiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chinh_sua_dia_chi)

        val switchMacDinh = findViewById<Switch>(R.id.switchMacDinh)
        val layoutNhaRieng = findViewById<LinearLayout>(R.id.layoutNhaRieng)
        val layoutVanPhong = findViewById<LinearLayout>(R.id.layoutVanPhong)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }
        layoutNhaRieng.setOnClickListener {
            layoutNhaRieng.setBackgroundResource(R.drawable.bg_address_type_selected)
            layoutVanPhong.setBackgroundResource(R.drawable.bg_address_type_normal)
        }
        layoutVanPhong.setOnClickListener {
            layoutVanPhong.setBackgroundResource(R.drawable.bg_address_type_selected)
            layoutNhaRieng.setBackgroundResource(R.drawable.bg_address_type_normal)
        }
        switchMacDinh.setOnCheckedChangeListener { _, isChecked ->
            switchMacDinh.trackTintList = android.content.res.ColorStateList.valueOf(
                if (isChecked) 0xFF1B7A4A.toInt() else 0xFFCCCCCC.toInt()
            )
        }

        val editId = intent.getStringExtra("id")
        if (editId != null) {
            findViewById<EditText>(R.id.etHoTen).setText(intent.getStringExtra("hoTen"))
            findViewById<EditText>(R.id.etSoDienThoai).setText(intent.getStringExtra("sdt"))
            findViewById<EditText>(R.id.etTinhThanh).setText(intent.getStringExtra("thanhPho"))
            findViewById<EditText>(R.id.etQuanHuyen).setText(intent.getStringExtra("quanHuyen"))
            findViewById<EditText>(R.id.etPhuongXa).setText(intent.getStringExtra("phuongXa"))
            findViewById<EditText>(R.id.etTenDuong).setText(intent.getStringExtra("tenDuong"))
            switchMacDinh.isChecked = intent.getBooleanExtra("macDinh", false)
        }

        findViewById<MaterialButton>(R.id.btnHoanThanh).setOnClickListener {
            val hoTen = findViewById<EditText>(R.id.etHoTen).text.toString().trim()
            val sdt = findViewById<EditText>(R.id.etSoDienThoai).text.toString().trim()
            val tinhThanh = findViewById<EditText>(R.id.etTinhThanh).text.toString().trim()
            val quanHuyen = findViewById<EditText>(R.id.etQuanHuyen).text.toString().trim()
            val phuongXa = findViewById<EditText>(R.id.etPhuongXa).text.toString().trim()
            val tenDuong = findViewById<EditText>(R.id.etTenDuong).text.toString().trim()
            val macDinh = switchMacDinh.isChecked
            val diaChiDayDu = listOf(phuongXa, quanHuyen, tinhThanh).filter { it.isNotEmpty() }.joinToString(", ")
            val maKH = UserManager.getMaKhachHang(this) ?: "2"

            val body = SoDiaChi().apply {
                setMaKhachHang(maKH); setTenNguoiNhan(hoTen); setEmail(sdt)
                setThanhPho(tinhThanh); setQuanHuyen(quanHuyen); setPhuongXa(phuongXa)
                setDiaChiCuThe(tenDuong); setDiaChiMacDinh(if (macDinh) 1 else 0)
            }

            if (macDinh) {
                boMacDinhCu(maKH, editId) {
                    if (editId != null) thucHienPut(editId, body, hoTen, sdt, diaChiDayDu, tenDuong)
                    else thucHienPost(body, hoTen, sdt, diaChiDayDu, tenDuong)
                }
            } else {
                if (editId != null) thucHienPut(editId, body, hoTen, sdt, diaChiDayDu, tenDuong)
                else thucHienPost(body, hoTen, sdt, diaChiDayDu, tenDuong)
            }
        }
    }

    private fun boMacDinhCu(maKH: String, skipId: String?, onDone: () -> Unit) {
        RetrofitClient.getApiService().getAllSoDiaChi().enqueue(object : Callback<List<SoDiaChi>> {
            override fun onResponse(call: Call<List<SoDiaChi>>, response: Response<List<SoDiaChi>>) {
                val list = response.body()?.filter { dc ->
                    val kh = dc.getMaKhachHang()
                    val match = kh?.toString() == maKH || (kh is Double && kh.toInt().toString() == maKH) || (kh is Int && kh.toString() == maKH)
                    match && dc.getDiaChiMacDinh() == 1 && dc.get_id() != skipId
                } ?: run { onDone(); return }
                if (list.isEmpty()) { onDone(); return }
                var done = 0
                list.forEach { dc ->
                    val reset = SoDiaChi().apply {
                        setMaKhachHang(dc.getMaKhachHang()?.toString() ?: maKH)
                        setTenNguoiNhan(dc.getTenNguoiNhan()); setEmail(dc.getEmail())
                        setThanhPho(dc.getThanhPho()); setQuanHuyen(dc.getQuanHuyen())
                        setPhuongXa(dc.getPhuongXa()); setDiaChiCuThe(dc.getDiaChiCuThe())
                        setDiaChiMacDinh(0)
                    }
                    RetrofitClient.getApiService().updateSoDiaChi(dc.get_id(), reset)
                        .enqueue(object : Callback<SoDiaChi> {
                            override fun onResponse(call: Call<SoDiaChi>, r: Response<SoDiaChi>) { if (++done >= list.size) onDone() }
                            override fun onFailure(call: Call<SoDiaChi>, t: Throwable) { if (++done >= list.size) onDone() }
                        })
                }
            }
            override fun onFailure(call: Call<List<SoDiaChi>>, t: Throwable) { onDone() }
        })
    }

    private fun thucHienPut(id: String, body: SoDiaChi, hoTen: String, sdt: String, diaChi: String, tenDuong: String) {
        RetrofitClient.getApiService().updateSoDiaChi(id, body).enqueue(object : Callback<SoDiaChi> {
            override fun onResponse(call: Call<SoDiaChi>, response: Response<SoDiaChi>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ChinhSuaDiaChiActivity, "Da cap nhat dia chi", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra("hoTen", hoTen); putExtra("soDienThoai", sdt)
                        putExtra("diaChi", diaChi); putExtra("tenDuong", tenDuong)
                    }); finish()
                } else Toast.makeText(this@ChinhSuaDiaChiActivity, "Loi: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<SoDiaChi>, t: Throwable) {
                Toast.makeText(this@ChinhSuaDiaChiActivity, "Loi ket noi", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun thucHienPost(body: SoDiaChi, hoTen: String, sdt: String, diaChi: String, tenDuong: String) {
        RetrofitClient.getApiService().createSoDiaChi(body).enqueue(object : Callback<SoDiaChi> {
            override fun onResponse(call: Call<SoDiaChi>, response: Response<SoDiaChi>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ChinhSuaDiaChiActivity, "Da luu dia chi", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra("hoTen", hoTen); putExtra("soDienThoai", sdt)
                        putExtra("diaChi", diaChi); putExtra("tenDuong", tenDuong)
                    }); finish()
                } else Toast.makeText(this@ChinhSuaDiaChiActivity, "Loi: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<SoDiaChi>, t: Throwable) {
                Toast.makeText(this@ChinhSuaDiaChiActivity, "Loi ket noi", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
