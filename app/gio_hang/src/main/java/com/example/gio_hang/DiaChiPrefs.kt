package com.example.gio_hang

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class DiaChiItem(
    val hoTen: String,
    val soDienThoai: String,
    val tenDuong: String,
    val diaChi: String,
    val isMacDinh: Boolean = false
)
//Code này dùng để lưu trữ danh sách địa chỉ nhận hàng vào bộ nhớ máy điện thoại.
object DiaChiPrefs {
    private const val PREF_NAME = "dia_chi_prefs"
    private const val KEY_LIST = "danh_sach_dia_chi"
//hàm lưu địa chỉ mới
    fun saveAddress(context: Context, item: DiaChiItem) {
        val list = getAddresses(context).toMutableList()

        // Nếu đặt mặc định -> bỏ mặc định của các item khác
        val updated = if (item.isMacDinh) {
            list.map { it.copy(isMacDinh = false) }.toMutableList()
        } else list

        // Tránh trùng hoàn toàn
        val exists = updated.any {
            it.hoTen == item.hoTen && it.soDienThoai == item.soDienThoai &&
            it.tenDuong == item.tenDuong && it.diaChi == item.diaChi
        }
        if (!exists) {
            if (item.isMacDinh) updated.add(0, item) else updated.add(item)
        }

        saveList(context, updated)
    }
//hàm cho lệnh xóa và cập nhật 
    fun removeAddress(context: Context, index: Int) {
        val list = getAddresses(context).toMutableList()//lấy danh sách địa chỉ ra
        if (index in list.indices) { //rồi kiểm tra địa chỉ muốn xóa có tồn tại trong danh sách ko, click xóa
            list.removeAt(index)
            saveList(context, list)
        }
    }
//hàm get địa chỉ này đóng vai trò là 1 người phiên dịch, là lấy chuỗi biến thành đối tượng địa chỉ danh sách có ý nghĩa
    fun getAddresses(context: Context): List<DiaChiItem> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_LIST, null) ?: return emptyList() //kiểm tra nếu ng dùng lần đầu ch có địa chỉ thì trả về ds rỗng
        val result = mutableListOf<DiaChiItem>()
        val arr = JSONArray(json)
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            result.add(DiaChiItem(
                hoTen = obj.getString("hoTen"),
                soDienThoai = obj.getString("soDienThoai"),
                tenDuong = obj.getString("tenDuong"),
                diaChi = obj.getString("diaChi"),
                isMacDinh = obj.optBoolean("isMacDinh", false)
            ))
        }
        return result
    }
//đóng gói và cất vào kho >< với công việc ở hàm getaddress
//biến đối tuowjgn thành văn bản lưu vào điện thoại
    private fun saveList(context: Context, list: List<DiaChiItem>) {
        val arr = JSONArray()
        list.forEach { item ->
            arr.put(JSONObject().apply {
                put("hoTen", item.hoTen)
                put("soDienThoai", item.soDienThoai)
                put("tenDuong", item.tenDuong)
                put("diaChi", item.diaChi)
                put("isMacDinh", item.isMacDinh)
            })
        }
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) //mở file lưu trưx
            .edit().putString(KEY_LIST, arr.toString()).apply() //tôi chbi sửa hệ thống
    }
}
