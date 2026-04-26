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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gio_hang)

        tvTongSanPham = findViewById(R.id.tvTongSanPham)
        tvTongTien = findViewById(R.id.tvTongTien)
        tvTenSoDienThoai = findViewById(R.id.tvTenSoDienThoai)
        tvDiaChiNhanHang = findViewById(R.id.tvDiaChiNhanHang)
        container = findViewById(R.id.containerProducts)

        findViewById<ImageButton>(R.id.btnChinhSuaDiaChi).setOnClickListener {
            chinhSuaLauncher.launch(Intent(this, SoDiaChiActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.btnMuaNgay).setOnClickListener {
            val selectedItems = ArrayList<CartItem>()
            cardViews.forEachIndexed { i, card ->
                val cb = checkBoxes.getOrNull(i) ?: return@forEachIndexed
                if (card.visibility == View.VISIBLE && cb.isChecked) {
                    val sp = products[i]
                    val giaNum = sp.getGia()?.replace("[^0-9]".toRegex(), "")?.toLongOrNull() ?: 0L
                    selectedItems.add(CartItem(sp.getTenSP(), giaNum, quantities[i]))
                }
            }
            if (selectedItems.isEmpty()) return@setOnClickListener
            startActivity(Intent(this, DonDatHangActivity::class.java).apply {
                putExtra("selectedItems", selectedItems)
            })
        }

        updateTotal()
        loadGioHangFromApi()
    }

    private fun loadGioHangFromApi() {
        val MA_KHACH_HANG = "KH_017"
        val api = RetrofitClient.getApiService()

        api.getAllChiTietGioHang().enqueue(object : Callback<List<ChiTietGioHang>> {
            override fun onResponse(call: Call<List<ChiTietGioHang>>, response: Response<List<ChiTietGioHang>>) {
                if (!response.isSuccessful) {
                    android.util.Log.e("GioHang", "API giỏ hàng lỗi: ${response.code()}")
                    return
                }
                val gioHangList = response.body() ?: return
                val myCart = gioHangList.filter { it.getMaKhachHang() == MA_KHACH_HANG }
                if (myCart.isEmpty()) return

                api.getAllSanPham().enqueue(object : Callback<List<SanPham>> {
                    override fun onResponse(call: Call<List<SanPham>>, response: Response<List<SanPham>>) {
                        if (!response.isSuccessful) {
                            android.util.Log.e("GioHang", "API sản phẩm lỗi: ${response.code()}")
                            return
                        }
                        val sanPhamMap = response.body()?.associateBy { it.getMaSP() ?: "" } ?: emptyMap()

                        val loaded = myCart.mapNotNull { item ->
                            val sp = sanPhamMap[item.getMaTietPham()] ?: return@mapNotNull null
                            sp to item.getSoLuong()
                        }
                        renderProducts(loaded)
                    }

                    override fun onFailure(call: Call<List<SanPham>>, t: Throwable) {
                        android.util.Log.e("GioHang", "Lỗi API sản phẩm: ${t.message}")
                    }
                })
            }

            override fun onFailure(call: Call<List<ChiTietGioHang>>, t: Throwable) {
                android.util.Log.e("GioHang", "Lỗi API giỏ hàng: ${t.message}")
            }
        })
    }

    private fun renderProducts(loaded: List<Pair<SanPham, Int>>) {
        while (container.childCount > 1) container.removeViewAt(1)
        products.clear(); cardViews.clear(); checkBoxes.clear(); quantities.clear()

        loaded.forEach { (sp, qty) ->
            val idx = products.size
            products.add(sp)
            quantities.add(qty)

            val card = layoutInflater.inflate(R.layout.item_product_cart, container, false) as CardView
            val cb = card.findViewById<CheckBox>(R.id.cbProductApi)
            val tvName = card.findViewById<TextView>(R.id.tvProductNameApi)
            val tvQty = card.findViewById<TextView>(R.id.tvQtyApi)
            val tvPrice = card.findViewById<TextView>(R.id.tvPriceApi)
            val btnMinus = card.findViewById<TextView>(R.id.btnMinusApi)
            val btnPlus = card.findViewById<TextView>(R.id.btnPlusApi)
            val btnDel = card.findViewById<ImageButton>(R.id.btnDeleteApi)

            val giaNum = sp.getGia()?.replace("[^0-9]".toRegex(), "")?.toLongOrNull() ?: 0L

            tvName.text = sp.getTenSP()
            tvQty.text = quantities[idx].toString()
            tvPrice.text = "${fmt.format(giaNum * quantities[idx])}đ"

            cardViews.add(card)
            checkBoxes.add(cb)

            cb.setOnCheckedChangeListener { _, _ -> updateTotal() }

            btnMinus.setOnClickListener {
                if (quantities[idx] > 1) {
                    quantities[idx]--
                    tvQty.text = quantities[idx].toString()
                    tvPrice.text = "${fmt.format(giaNum * quantities[idx])}đ"
                    updateTotal()
                }
            }
            btnPlus.setOnClickListener {
                quantities[idx]++
                tvQty.text = quantities[idx].toString()
                tvPrice.text = "${fmt.format(giaNum * quantities[idx])}đ"
                updateTotal()
            }
            btnDel.setOnClickListener {
                showDeleteDialog(card) {
                    updateTotal()
                }
            }

            container.addView(card)
        }
        updateTotal()
    }

    private fun updateTotal() {
        var total = 0L
        var count = 0
        cardViews.forEachIndexed { i, card ->
            val cb = checkBoxes.getOrNull(i) ?: return@forEachIndexed
            if (card.visibility == View.VISIBLE && cb.isChecked) {
                val giaNum = products[i].getGia()?.replace("[^0-9]".toRegex(), "")?.toLongOrNull() ?: 0L
                total += giaNum * quantities[i]
                count += quantities[i]
            }
        }
        tvTongSanPham.text = "Tổng cộng $count sản phẩm"
        tvTongTien.text = if (total == 0L) "0đ" else "${fmt.format(total)}đ"
    }

    private fun showDeleteDialog(cardView: CardView, onDeleted: (() -> Unit)? = null) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_xac_nhan_xoa)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                (resources.displayMetrics.widthPixels * 0.85).toInt(),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
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
        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.toast_cap_nhat_thanh_cong, null)
        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}
