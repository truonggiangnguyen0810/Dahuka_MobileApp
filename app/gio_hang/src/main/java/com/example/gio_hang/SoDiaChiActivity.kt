package com.example.gio_hang

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class SoDiaChiActivity : AppCompatActivity() {

    private val defaultList = listOf( //giá trị mặc định thôi
        DiaChiItem("Trần Thị Thùy Trinh", "0999777666", "Đặng Hối Xuân",
            "Phường Phú Quý, Quận Ngũ Hành, Đà Nẵng", true),
        DiaChiItem("Nguyễn Văn An", "0999777666", "Đặng Hối Xuân",
            "Phường Phú Quý, Quận Ngũ Hành, Đà Nẵng", false)
    )

    private lateinit var adapter: DiaChiAdapter
//Hãy tưởng tượng bạn đang ở màn hình Sổ địa chỉ (A). Bạn bấm nút "Thêm địa chỉ" để sang màn hình Chỉnh sửa/Thêm địa chỉ (B).

//Khi bạn làm xong ở màn hình (B) và bấm "Lưu", màn hình (B) đóng lại và bạn quay về màn hình (A).

//Lúc này, màn hình (A) cần phải biết là bạn đã thêm thành công để nó tải lại danh sách mới. Cái themDiaChiLauncher chính là "tai mắt" để nghe ngóng kết quả đó.
    private val themDiaChiLauncher = registerForActivityResult( //launcher là để đợi hành động sau khi bấm xác nhận
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) { //kiểm tra kết quả ok r thì lưu vào A
            // Chỉ refresh list, KHÔNG đóng SoDiaChiActivity
            // Địa chỉ đã được lưu vào SharedPreferences bởi ChinhSuaDiaChiActivity
            adapter.updateList(getList())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_so_dia_chi)

        val rv = findViewById<RecyclerView>(R.id.rvDiaChi) //thiết lập giao diện danh sách
        rv.layoutManager = LinearLayoutManager(this) //LinearLayout là một layout trong Android dùng để xếp các view theo một hàng thẳng.
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL)) //đường kẻ phân cách

        adapter = DiaChiAdapter(getList()) { item -> //đây là 1 lambda function
            val intent = Intent().apply { //tạo gói hàng, nhét tất cả thông tin vào 
                putExtra("hoTen", item.hoTen)
                putExtra("soDienThoai", item.soDienThoai)
                putExtra("tenDuong", item.tenDuong)
                putExtra("diaChi", item.diaChi)
            } 
            setResult(Activity.RESULT_OK, intent) //**Chọn địa chỉ** 
            finish()
        }
        rv.adapter = adapter

        // Swipe to delete
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) { //0 là ko cho phép kép thả để sx vị trí
            private val paint = Paint().apply { color = Color.parseColor("#E53E3E") } //màu 
            private val textPaint = Paint().apply {
                color = Color.WHITE
                textSize = 42f
                isAntiAlias = true
            }
            //vuốt qua để xóa

            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                //vuốt qua hoàn tất

                // Restore item trước (tránh item biến mất trước khi xác nhận)
                adapter.notifyItemChanged(pos) //xác nhận

                // Hiện dialog xác nhận
                android.app.AlertDialog.Builder(this@SoDiaChiActivity)
                    .setTitle("Xóa địa chỉ")
                    .setMessage("Bạn có chắc muốn xóa địa chỉ này không?")
                    .setPositiveButton("Xóa") { _, _ ->
                        val saved = DiaChiPrefs.getAddresses(this@SoDiaChiActivity)
                        if (saved.isNotEmpty()) {
                            DiaChiPrefs.removeAddress(this@SoDiaChiActivity, pos)
                        }
                        adapter.updateList(getList()) //chọn xóa thì remove và lưu, hủy thì thôi 
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
            }
            //đoạn code này giúp thêm nền đỏ và chữ xóa vào khoảng trắng khi vuốt qua
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                if (dX < 0) {
                    // Nền đỏ
                    val bg = RectF(
                        itemView.right + dX, itemView.top.toFloat(),
                        itemView.right.toFloat(), itemView.bottom.toFloat()
                    )
                    c.drawRoundRect(bg, 0f, 0f, paint)
                    // Chữ "Xóa"
                    val text = "🗑 Xóa"
                    val textWidth = textPaint.measureText(text)
                    val x = itemView.right - textWidth - 48f
                    val y = itemView.top + (itemView.height / 2f) + (textPaint.textSize / 3f)
                    c.drawText(text, x, y, textPaint)
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(rv)

        findViewById<MaterialButton>(R.id.btnThemDiaChi).setOnClickListener {
            themDiaChiLauncher.launch(Intent(this, ChinhSuaDiaChiActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateList(getList())
    } //resum lại màn hình danh sách sổ địa chỉ khi mà chọn chỉnh sửa hay thêm địa chỉ, để ko bị tình trạng ko lưu dc địa chỉ mới

    private fun getList(): MutableList<DiaChiItem> { //nhập địa chỉ xác nhẩn rồi get list lấy địa chỉ đó quay về mh sổ địa chỉ
        val saved = DiaChiPrefs.getAddresses(this)
        return if (saved.isEmpty()) defaultList.toMutableList() else saved.toMutableList()
    }
}

// ── Adapter ──────────────────────────────────────────────
class DiaChiAdapter( // đoạn code này giúp hiển thị đẹp, chuyên nghiệp, 1oo địa chỉ thì hiện view vừa mh thôi, vuốt xuống sẽ mất đi lòi cái khác
    private var list: MutableList<DiaChiItem>,
    private val onClick: (DiaChiItem) -> Unit
) : RecyclerView.Adapter<DiaChiAdapter.VH>() { //recycleview là sanh danh sách địa chỉ, và adapter là lấy đủ vừa màn hình
//list là kho chứa hiện tại
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvHoTen: TextView = view.findViewById(R.id.tvHoTen)
        val tvSdt: TextView = view.findViewById(R.id.tvSdt)
        val tvTenDuong: TextView = view.findViewById(R.id.tvTenDuong)
        val tvDiaChi: TextView = view.findViewById(R.id.tvDiaChi)
        val tvMacDinh: TextView = view.findViewById(R.id.tvMacDinh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_dia_chi, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        holder.tvHoTen.text = item.hoTen
        holder.tvSdt.text = "(+84) ${item.soDienThoai.removePrefix("0")}"
        holder.tvTenDuong.text = item.tenDuong
        holder.tvDiaChi.text = item.diaChi
        holder.tvMacDinh.visibility = if (item.isMacDinh) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener { onClick(item) }
    }

    fun updateList(newList: MutableList<DiaChiItem>) {
        list = newList
        notifyDataSetChanged()
    }
}
