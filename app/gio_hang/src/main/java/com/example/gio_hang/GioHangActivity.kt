package com.example.gio_hang

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.common.UserManager
import com.example.common.model.CartItem
import com.example.common.model.ChiTietGioHang
import com.example.common.model.SanPham
import com.example.common.network.RetrofitClient
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class GioHangActivity : AppCompatActivity() {

    private val fmt = NumberFormat.getNumberInstance(Locale("vi", "VN"))
    private val products = mutableListOf<SanPham>()
    private val cardViews = mutableListOf<CardView>()
    private val checkBoxes = mutableListOf<CheckBox>()
    private val quantities = mutableListOf<Int>()
    private val gioHangMaKH = mutableListOf<String>()
    private val gioHangMaSP = mutableListOf<String>()

    private lateinit var tvTongSanPham: TextView
    private lateinit var tvTongTien: TextView
    private lateinit var tvTenSoDienThoai: TextView
    private lateinit var tvDiaChiNhanHang: TextView
    private lateinit var container: LinearLayout

    private val chinhSuaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            tvTenSoDienThoai.text = "${data?.getStringExtra("hoTen")} | ${data?.getStringExtra("soDienThoai")} |"
            tvDiaChiNhanHang.text = "${data?.getStringExtra("tenDuong")}, ${data?.getStringExtra("diaChi")}"
            showSuccessToast()
        }
    }

    // Launcher nhận kết quả từ DonDatHangActivity — reload giỏ hàng sau khi đặt
    private val datHangLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            loadGioHangFromApi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gio_hang)

        tvTongSanPham = findViewById(R.id.tvTongSanPham)
        tvTongTien = findViewById(R.id.tvTongTien)
        tvTenSoDienThoai = findViewById(R.id.tvTenSoDienThoai)
        tvDiaChiNhanHang = findViewById(R.id.tvDiaChiNhanHang)
        container = findViewById(R.id.containerProducts)

        // TEST: bypass login — xóa sau khi tích hợp đăng nhập thật
        UserManager.saveLogin(this, 2, "test", "user")
        UserManager.saveMaKhachHang(this, "2")

        findViewById<ImageButton>(R.id.btnChinhSuaDiaChi).setOnClickListener {
            chinhSuaLauncher.launch(Intent(this, SoDiaChiActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.btnMuaNgay).setOnClickListener {
            val selectedItems = ArrayList<CartItem>()
            val selectedMaSP = ArrayList<String>()
            cardViews.forEachIndexed { i, card ->
                val cb = checkBoxes.getOrNull(i) ?: return@forEachIndexed
                if (card.visibility == View.VISIBLE && cb.isChecked) {
                    val sp = products[i]
                    val giaNum = sp.getGia()?.replace("[^0-9]".toRegex(), "")?.toLongOrNull() ?: 0L
                    selectedItems.add(CartItem(sp.getTenSP(), giaNum, quantities[i]))
                    selectedMaSP.add(gioHangMaSP[i])
                }
            }
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            datHangLauncher.launch(Intent(this, DonDatHangActivity::class.java).apply {
                putExtra("selectedItems", selectedItems)
                putStringArrayListExtra("selectedMaSP", selectedMaSP)
                putExtra("maKhachHang", UserManager.getMaKhachHang(this@GioHangActivity))
            })
        }

        updateTotal()
        loadGioHangFromApi()
        loadDiaChiMacDinh()
    }

    // GET địa chỉ mặc định hiển thị lên giỏ hàng
    private fun loadDiaChiMacDinh() {
        val maKH = UserManager.getMaKhachHang(this) ?: return
        RetrofitClient.getApiService().getAllSoDiaChi().enqueue(object : Callback<List<com.example.common.model.SoDiaChi>> {
            override fun onResponse(call: Call<List<com.example.common.model.SoDiaChi>>, response: Response<List<com.example.common.model.SoDiaChi>>) {
                if (!response.isSuccessful) return
                val diaChi = response.body()?.firstOrNull {
                    val kh = it.getMaKhachHang()
                    val match = kh?.toString() == maKH || (kh is Double && kh.toInt().toString() == maKH) || (kh is Int && kh.toString() == maKH)
                    match && it.getDiaChiMacDinh() == 1
                } ?: response.body()?.firstOrNull {
                    val kh = it.getMaKhachHang()
                    kh?.toString() == maKH || (kh is Double && kh.toInt().toString() == maKH) || (kh is Int && kh.toString() == maKH)
                } ?: return
                runOnUiThread {
                    tvTenSoDienThoai.text = "${diaChi.getTenNguoiNhan()} | ${diaChi.getEmail()} |"
                    tvDiaChiNhanHang.text = "${diaChi.getDiaChiCuThe()}, ${diaChi.getPhuongXa()}, ${diaChi.getQuanHuyen()}, ${diaChi.getThanhPho()}"
                }
            }
            override fun onFailure(call: Call<List<com.example.common.model.SoDiaChi>>, t: Throwable) {}
        })
    }

    // GET danh sách giỏ hàng từ API
    fun loadGioHangFromApi() {
        val maKhachHang = UserManager.getMaKhachHang(this) ?: return
        val api = RetrofitClient.getApiService()
        api.getAllChiTietGioHang().enqueue(object : Callback<List<ChiTietGioHang>> {
            override fun onResponse(call: Call<List<ChiTietGioHang>>, response: Response<List<ChiTietGioHang>>) {
                if (!response.isSuccessful) return
                val myCart = (response.body() ?: return).filter { it.getMaKhachHang() == maKhachHang }
                if (myCart.isEmpty()) {
                    runOnUiThread {
                        while (container.childCount > 1) container.removeViewAt(1)
                        products.clear(); cardViews.clear(); checkBoxes.clear()
                        quantities.clear(); gioHangMaKH.clear(); gioHangMaSP.clear()
                        tvTongSanPham.text = "Giỏ hàng trống"
                        tvTongTien.text = "0đ"
                    }
                    return
                }
                api.getAllSanPham().enqueue(object : Callback<List<SanPham>> {
                    override fun onResponse(call: Call<List<SanPham>>, response: Response<List<SanPham>>) {
                        if (!response.isSuccessful) return
                        val sanPhamMap = response.body()?.associateBy { it.getMaSP() ?: "" } ?: emptyMap()

                        // Load ảnh sản phẩm
                        api.getAllHinhAnh().enqueue(object : Callback<List<com.example.common.model.HinhAnhSanPham>> {
                            override fun onResponse(call: Call<List<com.example.common.model.HinhAnhSanPham>>, res2: Response<List<com.example.common.model.HinhAnhSanPham>>) {
                                val hinhAnhMap = res2.body()?.associateBy { it.getMaSP() ?: "" } ?: emptyMap()
                                val loaded = myCart.mapNotNull { item ->
                                    val sp = sanPhamMap[item.getMaTietPham()] ?: return@mapNotNull null
                                    val anhUrl = hinhAnhMap[item.getMaTietPham()]?.getAnhChinh()
                                    Triple(sp, item.getSoLuong(), item.getMaTietPham()) to anhUrl
                                }
                                runOnUiThread { renderProducts(loaded, maKhachHang) }
                            }
                            override fun onFailure(call: Call<List<com.example.common.model.HinhAnhSanPham>>, t: Throwable) {
                                // Nếu load ảnh lỗi, vẫn hiện sản phẩm không có ảnh
                                val loaded = myCart.mapNotNull { item ->
                                    val sp = sanPhamMap[item.getMaTietPham()] ?: return@mapNotNull null
                                    Triple(sp, item.getSoLuong(), item.getMaTietPham()) to null
                                }
                                runOnUiThread { renderProducts(loaded, maKhachHang) }
                            }
                        })
                    }
                    override fun onFailure(call: Call<List<SanPham>>, t: Throwable) {}
                })
            }
            override fun onFailure(call: Call<List<ChiTietGioHang>>, t: Throwable) {
                runOnUiThread { Toast.makeText(this@GioHangActivity, "Lỗi tải giỏ hàng", Toast.LENGTH_SHORT).show() }
            }
        })
    }

    private fun renderProducts(loaded: List<Pair<Triple<SanPham, Int, String>, String?>>, maKhachHang: String) {
        while (container.childCount > 1) container.removeViewAt(1)
        products.clear(); cardViews.clear(); checkBoxes.clear()
        quantities.clear(); gioHangMaKH.clear(); gioHangMaSP.clear()

        loaded.forEach { (triple, anhUrl) ->
            val (sp, qty, maTietPham) = triple
            val idx = products.size
            products.add(sp); quantities.add(qty)
            gioHangMaKH.add(maKhachHang); gioHangMaSP.add(maTietPham)

            val card = layoutInflater.inflate(R.layout.item_product_cart, container, false) as CardView
            val cb = card.findViewById<CheckBox>(R.id.cbProductApi)
            val tvName = card.findViewById<TextView>(R.id.tvProductNameApi)
            val tvQty = card.findViewById<TextView>(R.id.tvQtyApi)
            val tvPrice = card.findViewById<TextView>(R.id.tvPriceApi)
            val btnMinus = card.findViewById<TextView>(R.id.btnMinusApi)
            val btnPlus = card.findViewById<TextView>(R.id.btnPlusApi)
            val btnDel = card.findViewById<ImageButton>(R.id.btnDeleteApi)
            val imgProduct = card.findViewById<android.widget.ImageView>(R.id.imgProductApi)
            val giaNum = sp.getGia()?.replace("[^0-9]".toRegex(), "")?.toLongOrNull() ?: 0L

            tvName.text = sp.getTenSP()
            tvQty.text = quantities[idx].toString()
            tvPrice.text = "${fmt.format(giaNum * quantities[idx])}đ"

            // Load ảnh sản phẩm bằng Glide
            if (!anhUrl.isNullOrEmpty()) {
                com.bumptech.glide.Glide.with(this)
                    .load(anhUrl)
                    .placeholder(R.drawable.anhsanpham)
                    .error(R.drawable.anhsanpham)
                    .into(imgProduct)
            }

            cardViews.add(card); checkBoxes.add(cb)
            cb.setOnCheckedChangeListener { _, _ -> updateTotal() }

            // PUT: tăng số lượng
            btnPlus.setOnClickListener {
                quantities[idx]++
                tvQty.text = quantities[idx].toString()
                tvPrice.text = "${fmt.format(giaNum * quantities[idx])}đ"
                updateTotal()
                capNhatSoLuong(gioHangMaKH[idx], gioHangMaSP[idx], quantities[idx])
            }

            // PUT: giảm số lượng
            btnMinus.setOnClickListener {
                if (quantities[idx] > 1) {
                    quantities[idx]--
                    tvQty.text = quantities[idx].toString()
                    tvPrice.text = "${fmt.format(giaNum * quantities[idx])}đ"
                    updateTotal()
                    capNhatSoLuong(gioHangMaKH[idx], gioHangMaSP[idx], quantities[idx])
                }
            }

            // DELETE: xóa sản phẩm
            btnDel.setOnClickListener {
                showDeleteDialog(card) {
                    xoaKhoiGioHang(gioHangMaKH[idx], gioHangMaSP[idx])
                    updateTotal()
                }
            }
            container.addView(card)
        }
        updateTotal()
    }

    private fun updateTotal() {
        var total = 0L; var count = 0
        cardViews.forEachIndexed { i, card ->
            val cb = checkBoxes.getOrNull(i) ?: return@forEachIndexed
            if (card.visibility == View.VISIBLE && cb.isChecked) {
                val giaNum = products[i].getGia()?.replace("[^0-9]".toRegex(), "")?.toLongOrNull() ?: 0L
                total += giaNum * quantities[i]; count += quantities[i]
            }
        }
        tvTongSanPham.text = "Tổng cộng $count sản phẩm"
        tvTongTien.text = if (total == 0L) "0đ" else "${fmt.format(total)}đ"
    }

    // PUT: cập nhật số lượng lên server
    private fun capNhatSoLuong(maKH: String, maSP: String, soLuong: Int) {
        val body = ChiTietGioHang().apply { setSoLuong(soLuong) }
        RetrofitClient.getApiService().updateChiTietGioHang(maKH, maSP, body)
            .enqueue(object : Callback<ChiTietGioHang> {
                override fun onResponse(call: Call<ChiTietGioHang>, response: Response<ChiTietGioHang>) {
                    if (!response.isSuccessful)
                        Toast.makeText(this@GioHangActivity, "Lỗi cập nhật số lượng", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ChiTietGioHang>, t: Throwable) {
                    Toast.makeText(this@GioHangActivity, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // DELETE: xóa sản phẩm khỏi giỏ hàng
    private fun xoaKhoiGioHang(maKH: String, maSP: String) {
        RetrofitClient.getApiService().deleteChiTietGioHang(maKH, maSP)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (!response.isSuccessful)
                        Toast.makeText(this@GioHangActivity, "Lỗi xóa sản phẩm", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@GioHangActivity, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showDeleteDialog(cardView: CardView, onDeleted: (() -> Unit)? = null) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_xac_nhan_xoa)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout((resources.displayMetrics.widthPixels * 0.85).toInt(), android.view.ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.findViewById<TextView>(R.id.btnDongY).setOnClickListener {
            cardView.visibility = View.GONE
            onDeleted?.invoke()
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.btnHuy).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun showSuccessToast() {
        val layout = LayoutInflater.from(this).inflate(R.layout.toast_cap_nhat_thanh_cong, null)
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT; view = layout
            setGravity(Gravity.CENTER, 0, 0); show()
        }
    }
}
