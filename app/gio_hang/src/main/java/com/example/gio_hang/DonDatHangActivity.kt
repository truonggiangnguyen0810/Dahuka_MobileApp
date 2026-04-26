package com.example.gio_hang

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.common.model.CartItem
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class DonDatHangActivity : AppCompatActivity() {

    private var isChuyenKhoan = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_don_dat_hang)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        // Nhận danh sách sản phẩm từ GioHangActivity
        @Suppress("UNCHECKED_CAST")
        val selectedItems = intent.getSerializableExtra("selectedItems") as? ArrayList<CartItem>
            ?: arrayListOf()

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

        // Phương thức thanh toán
        val layoutChuyenKhoan = findViewById<LinearLayout>(R.id.layoutChuyenKhoan)
        val layoutTienMat = findViewById<LinearLayout>(R.id.layoutTienMat)
        val rbChuyenKhoan = findViewById<RadioButton>(R.id.rbChuyenKhoan)
        val rbTienMat = findViewById<RadioButton>(R.id.rbTienMat)

        fun selectChuyenKhoan() {
            isChuyenKhoan = true
            rbChuyenKhoan.isChecked = true
            rbTienMat.isChecked = false
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_normal)
        }

        fun selectTienMat() {
            isChuyenKhoan = false
            rbTienMat.isChecked = true
            rbChuyenKhoan.isChecked = false
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_normal)
        }

        layoutChuyenKhoan.setOnClickListener { selectChuyenKhoan() }
        rbChuyenKhoan.setOnClickListener { selectChuyenKhoan() }
        layoutTienMat.setOnClickListener { selectTienMat() }
        rbTienMat.setOnClickListener { selectTienMat() }

        selectChuyenKhoan()

        findViewById<MaterialButton>(R.id.btnXacNhan).setOnClickListener {
            if (isChuyenKhoan) {
                startActivity(Intent(this, ThanhToanQrActivity::class.java))
            } else {
                startActivity(Intent(this, DatHangThanhCongActivity::class.java))
            }
        }
    }
}
