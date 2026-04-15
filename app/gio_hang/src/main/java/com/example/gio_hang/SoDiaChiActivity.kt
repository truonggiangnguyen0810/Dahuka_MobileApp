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

    private val defaultList = listOf(
        DiaChiItem("Trần Thị Thùy Trinh", "0999777666", "Đặng Hối Xuân",
            "Phường Phú Quý, Quận Ngũ Hành, Đà Nẵng", true),
        DiaChiItem("Nguyễn Văn An", "0999777666", "Đặng Hối Xuân",
            "Phường Phú Quý, Quận Ngũ Hành, Đà Nẵng", false)
    )

    private lateinit var adapter: DiaChiAdapter

    private val themDiaChiLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Chỉ refresh list, KHÔNG đóng SoDiaChiActivity
            // Địa chỉ đã được lưu vào SharedPreferences bởi ChinhSuaDiaChiActivity
            adapter.updateList(getList())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_so_dia_chi)

        val rv = findViewById<RecyclerView>(R.id.rvDiaChi)
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter = DiaChiAdapter(getList()) { item ->
            val intent = Intent().apply {
                putExtra("hoTen", item.hoTen)
                putExtra("soDienThoai", item.soDienThoai)
                putExtra("tenDuong", item.tenDuong)
                putExtra("diaChi", item.diaChi)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        rv.adapter = adapter

        // Swipe to delete
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private val paint = Paint().apply { color = Color.parseColor("#E53E3E") }
            private val textPaint = Paint().apply {
                color = Color.WHITE
                textSize = 42f
                isAntiAlias = true
            }

            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition

                // Restore item trước (tránh item biến mất trước khi xác nhận)
                adapter.notifyItemChanged(pos)

                // Hiện dialog xác nhận
                android.app.AlertDialog.Builder(this@SoDiaChiActivity)
                    .setTitle("Xóa địa chỉ")
                    .setMessage("Bạn có chắc muốn xóa địa chỉ này không?")
                    .setPositiveButton("Xóa") { _, _ ->
                        val saved = DiaChiPrefs.getAddresses(this@SoDiaChiActivity)
                        if (saved.isNotEmpty()) {
                            DiaChiPrefs.removeAddress(this@SoDiaChiActivity, pos)
                        }
                        adapter.updateList(getList())
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
            }

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
    }

    private fun getList(): MutableList<DiaChiItem> {
        val saved = DiaChiPrefs.getAddresses(this)
        return if (saved.isEmpty()) defaultList.toMutableList() else saved.toMutableList()
    }
}

// ── Adapter ──────────────────────────────────────────────
class DiaChiAdapter(
    private var list: MutableList<DiaChiItem>,
    private val onClick: (DiaChiItem) -> Unit
) : RecyclerView.Adapter<DiaChiAdapter.VH>() {

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
