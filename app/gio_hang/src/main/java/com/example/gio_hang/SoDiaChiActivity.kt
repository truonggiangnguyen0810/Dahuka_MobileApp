package com.example.gio_hang

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.UserManager
import com.example.common.model.SoDiaChi
import com.example.common.network.RetrofitClient
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SoDiaChiActivity : AppCompatActivity() {

    private lateinit var adapter: SoDiaChiApiAdapter
    private val danhSach = mutableListOf<SoDiaChi>()

    private val themDiaChiLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result -> if (result.resultCode == RESULT_OK) loadDiaChiFromApi() }

    private val suaDiaChiLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result -> if (result.resultCode == RESULT_OK) loadDiaChiFromApi() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_so_dia_chi)

        val rv = findViewById<RecyclerView>(R.id.rvDiaChi)
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter = SoDiaChiApiAdapter(danhSach,
            onClick = { item ->
                // Chọn địa chỉ → trả về GioHangActivity
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra("hoTen", item.getTenNguoiNhan())
                    putExtra("soDienThoai", item.getEmail())
                    putExtra("tenDuong", item.getDiaChiCuThe())
                    putExtra("diaChi", "${item.getPhuongXa()}, ${item.getQuanHuyen()}, ${item.getThanhPho()}")
                })
                finish()
            },
            onEdit = { item ->
                // Sửa địa chỉ → PUT
                suaDiaChiLauncher.launch(Intent(this, ChinhSuaDiaChiActivity::class.java).apply {
                    putExtra("id", item.get_id())
                    putExtra("hoTen", item.getTenNguoiNhan())
                    putExtra("sdt", item.getEmail())
                    putExtra("thanhPho", item.getThanhPho())
                    putExtra("quanHuyen", item.getQuanHuyen())
                    putExtra("phuongXa", item.getPhuongXa())
                    putExtra("tenDuong", item.getDiaChiCuThe())
                    putExtra("macDinh", item.getDiaChiMacDinh() == 1)
                })
            }
        )
        rv.adapter = adapter

        // Swipe trái → DELETE
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private val paint = Paint().apply { color = Color.parseColor("#E53E3E") }
            private val textPaint = Paint().apply { color = Color.WHITE; textSize = 42f; isAntiAlias = true }

            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                adapter.notifyItemChanged(pos)
                AlertDialog.Builder(this@SoDiaChiActivity)
                    .setTitle("Xóa địa chỉ")
                    .setMessage("Bạn có chắc muốn xóa địa chỉ này không?")
                    .setPositiveButton("Xóa") { _, _ -> xoaDiaChi(pos) }
                    .setNegativeButton("Hủy", null)
                    .show()
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val v = viewHolder.itemView
                if (dX < 0) {
                    c.drawRoundRect(RectF(v.right + dX, v.top.toFloat(), v.right.toFloat(), v.bottom.toFloat()), 0f, 0f, paint)
                    val text = "🗑 Xóa"
                    c.drawText(text, v.right - textPaint.measureText(text) - 48f, v.top + v.height / 2f + textPaint.textSize / 3f, textPaint)
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(rv)

        findViewById<MaterialButton>(R.id.btnThemDiaChi).setOnClickListener {
            themDiaChiLauncher.launch(Intent(this, ChinhSuaDiaChiActivity::class.java))
        }

        loadDiaChiFromApi()
    }

    // GET danh sách địa chỉ của khách hàng
    private fun loadDiaChiFromApi() {
        val maKH = UserManager.getMaKhachHang(this) ?: return
        RetrofitClient.getApiService().getAllSoDiaChi().enqueue(object : Callback<List<SoDiaChi>> {
            override fun onResponse(call: Call<List<SoDiaChi>>, response: Response<List<SoDiaChi>>) {
                if (!response.isSuccessful) return
                val list = response.body()?.filter {
                    val kh = it.getMaKhachHang()
                    kh?.toString() == maKH || (kh is Double && kh.toInt().toString() == maKH) || (kh is Int && kh.toString() == maKH)
                } ?: return
                danhSach.clear(); danhSach.addAll(list)
                runOnUiThread { adapter.notifyDataSetChanged() }
            }
            override fun onFailure(call: Call<List<SoDiaChi>>, t: Throwable) {
                Toast.makeText(this@SoDiaChiActivity, "Lỗi tải địa chỉ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // DELETE địa chỉ
    private fun xoaDiaChi(pos: Int) {
        val id = danhSach.getOrNull(pos)?.get_id() ?: return
        RetrofitClient.getApiService().deleteSoDiaChi(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    danhSach.removeAt(pos)
                    runOnUiThread { adapter.notifyDataSetChanged() }
                    Toast.makeText(this@SoDiaChiActivity, "Đã xóa địa chỉ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SoDiaChiActivity, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@SoDiaChiActivity, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

class SoDiaChiApiAdapter(
    private val list: MutableList<SoDiaChi>,
    private val onClick: (SoDiaChi) -> Unit,
    private val onEdit: (SoDiaChi) -> Unit
) : RecyclerView.Adapter<SoDiaChiApiAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvHoTen: TextView = view.findViewById(R.id.tvHoTen)
        val tvSdt: TextView = view.findViewById(R.id.tvSdt)
        val tvTenDuong: TextView = view.findViewById(R.id.tvTenDuong)
        val tvDiaChi: TextView = view.findViewById(R.id.tvDiaChi)
        val tvMacDinh: TextView = view.findViewById(R.id.tvMacDinh)
        val btnSua: ImageButton = view.findViewById(R.id.btnSuaDiaChi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_dia_chi, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        holder.tvHoTen.text = item.getTenNguoiNhan() ?: ""
        holder.tvSdt.text = item.getEmail() ?: ""
        holder.tvTenDuong.text = item.getDiaChiCuThe() ?: ""
        holder.tvDiaChi.text = "${item.getPhuongXa() ?: ""}, ${item.getQuanHuyen() ?: ""}, ${item.getThanhPho() ?: ""}"
        holder.tvMacDinh.visibility = if (item.getDiaChiMacDinh() == 1) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener { onClick(item) }
        holder.btnSua.setOnClickListener { onEdit(item) }
    }
}
