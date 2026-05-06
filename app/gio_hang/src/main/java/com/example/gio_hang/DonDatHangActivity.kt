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
//code này xử lý truyền dữ liệu giữa 2 màn hình
    private var isChuyenKhoan = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_don_dat_hang)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        // Nhận danh sách sản phẩm từ GioHangActivity
        @Suppress("UNCHECKED_CAST") //dòng này để tắt cảnh báo
        val selectedItems = intent.getSerializableExtra("selectedItems") as? ArrayList<CartItem>
            ?: arrayListOf()
            //intent là như cái xe vận chuyển dữ liệu, lấy từ giỏ hàng sang trang đặt hàng
        //đoạn code này là dùng để hiển thị danh sách các món hàng lên màn hình sau đó tính tổng tiền, ở trang đặt hàng
        val containerItems = findViewById<LinearLayout>(R.id.containerOrderItems)
        containerItems.removeAllViews() //này là dọn dẹp sạch cái LinearLayout, mỗi lần reload sẽ ko bị dồn sp
        //dùng cách thủ công là inflate các view vào LinearLayout
        val fmt = NumberFormat.getNumberInstance(Locale("vi", "VN"))
        selectedItems.forEach { item ->
            val card = layoutInflater.inflate(R.layout.item_order_don_hang, containerItems, false)
            card.findViewById<TextView>(R.id.tvItemName).text = item.getTenSanPham()
            card.findViewById<TextView>(R.id.tvItemQty).text = "Số lượng: ${item.getSoLuong()}"
            card.findViewById<TextView>(R.id.tvItemPrice).text = "${fmt.format(item.getDonGia())}đ"
            containerItems.addView(card)
        }
        //item_order_don_hang là cái khay, là cái form chưa có j -> đẩy từng dữ liệu 1 vào form rồi quay lại đẩy tiếp đến hết
        //đây là giao diện hiển thị còn ở trên là truyền dữ liệu lưu vào ram
        val total = selectedItems.sumOf { it.getDonGia() * it.getSoLuong() }
        findViewById<TextView>(R.id.tvTamTinh).text = "${fmt.format(total)}đ"

        // Phương thức thanh toán //khung khai báo giao diện 
        val layoutChuyenKhoan = findViewById<LinearLayout>(R.id.layoutChuyenKhoan)
        val layoutTienMat = findViewById<LinearLayout>(R.id.layoutTienMat)
        val rbChuyenKhoan = findViewById<RadioButton>(R.id.rbChuyenKhoan) //radio button
        val rbTienMat = findViewById<RadioButton>(R.id.rbTienMat) //radio button

        fun selectChuyenKhoan() { //hàm này được gọi khi ng dùng muốn thanh toán bằng chuyển khoản
            isChuyenKhoan = true
            rbChuyenKhoan.isChecked = true
            rbTienMat.isChecked = false
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_normal) // thay đổi màu sắc 
        }

        fun selectTienMat() {
            isChuyenKhoan = false
            rbTienMat.isChecked = true
            rbChuyenKhoan.isChecked = false
            layoutTienMat.setBackgroundResource(R.drawable.bg_payment_selected)
            layoutChuyenKhoan.setBackgroundResource(R.drawable.bg_payment_normal)
        }
        //chốt chặn cuối cùng, gán sự kiện click 
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
