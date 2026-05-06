package com.example.gio_hang

// Import các thư viện cần thiết
import android.app.Activity // Để dùng RESULT_OK
import android.content.Intent // Để chuyển màn hình và truyền dữ liệu
import android.os.Bundle // Để lưu trạng thái Activity
import android.widget.EditText // Ô nhập liệu
import android.widget.ImageButton // Nút hình ảnh (nút Back)
import android.widget.LinearLayout // Container chứa view
import android.widget.Switch // Công tắc bật/tắt
import androidx.appcompat.app.AppCompatActivity // Base class cho Activity
import com.google.android.material.button.MaterialButton // Nút Material Design

/**
 * Activity để thêm hoặc sửa địa chỉ giao hàng
 * Chức năng:
 * - Nhập thông tin liên hệ (họ tên, SĐT)
 * - Nhập địa chỉ đầy đủ (tỉnh, quận, phường, tên đường)
 * - Chọn loại địa chỉ (Nhà Riêng / Văn Phòng)
 * - Đặt làm địa chỉ mặc định
 * - Lưu địa chỉ vào SharedPreferences
 */
class ChinhSuaDiaChiActivity : AppCompatActivity() {

    // Biến đánh dấu loại địa chỉ đang được chọn
    // true = Nhà Riêng, false = Văn Phòng
    // Mặc định là Nhà Riêng khi mới vào màn hình
    private var isNhaRieng = true

    /**
     * Hàm được gọi khi Activity được tạo
     * Khởi tạo giao diện và xử lý sự kiện
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Load layout từ file XML
        setContentView(R.layout.activity_chinh_sua_dia_chi)

        // ===== NÚT BACK =====
        // Tìm nút Back trong layout
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { 
            finish() // Đóng Activity này, quay về màn hình trước
        }

        // ===== CHỌN LOẠI ĐỊA CHỈ =====
        // Tìm 2 layout để chọn loại địa chỉ
        val layoutNhaRieng = findViewById<LinearLayout>(R.id.layoutNhaRieng)
        val layoutVanPhong = findViewById<LinearLayout>(R.id.layoutVanPhong)

        // Xử lý khi bấm vào "Nhà Riêng"
        layoutNhaRieng.setOnClickListener {
            isNhaRieng = true // Đánh dấu đang chọn Nhà Riêng
            
            // Đổi background của "Nhà Riêng" thành màu xanh (đã chọn)
            layoutNhaRieng.setBackgroundResource(R.drawable.bg_address_type_selected)
            
            // Đổi background của "Văn Phòng" thành màu xám (chưa chọn)
            layoutVanPhong.setBackgroundResource(R.drawable.bg_address_type_normal)
        }

        // Xử lý khi bấm vào "Văn Phòng"
        layoutVanPhong.setOnClickListener {
            isNhaRieng = false // Đánh dấu đang chọn Văn Phòng
            
            // Đổi background của "Văn Phòng" thành màu xanh (đã chọn)
            layoutVanPhong.setBackgroundResource(R.drawable.bg_address_type_selected)
            
            // Đổi background của "Nhà Riêng" thành màu xám (chưa chọn)
            layoutNhaRieng.setBackgroundResource(R.drawable.bg_address_type_normal)
        }

        // ===== SWITCH ĐẶT LÀM MẶC ĐỊNH =====
        // Tìm Switch trong layout
        val switchMacDinh = findViewById<Switch>(R.id.switchMacDinh)
        
        // Xử lý khi Switch thay đổi trạng thái (bật/tắt)
        switchMacDinh.setOnCheckedChangeListener { _, isChecked ->
            // Đổi màu thanh trượt của Switch
            switchMacDinh.trackTintList = android.content.res.ColorStateList.valueOf(
                if (isChecked) 
                    0xFF1B7A4A.toInt() // Nếu bật → Màu xanh lá
                else 
                    0xFFCCCCCC.toInt() // Nếu tắt → Màu xám
            )
        }

        // ===== NÚT HOÀN THÀNH =====
        // Tìm nút "HOÀN THÀNH" trong layout
        findViewById<MaterialButton>(R.id.btnHoanThanh).setOnClickListener {
            
            // ===== LẤY DỮ LIỆU TỪ CÁC Ô NHẬP LIỆU =====
            
            // Lấy họ tên từ EditText, loại bỏ khoảng trắng thừa
            val hoTen = findViewById<EditText>(R.id.etHoTen).text.toString().trim()
            
            // Lấy số điện thoại từ EditText, loại bỏ khoảng trắng thừa
            val sdt = findViewById<EditText>(R.id.etSoDienThoai).text.toString().trim()
            
            // Lấy tỉnh/thành phố từ EditText
            val tinhThanh = findViewById<EditText>(R.id.etTinhThanh).text.toString().trim()
            
            // Lấy quận/huyện từ EditText
            val quanHuyen = findViewById<EditText>(R.id.etQuanHuyen).text.toString().trim()
            
            // Lấy phường/xã từ EditText
            val phuongXa = findViewById<EditText>(R.id.etPhuongXa).text.toString().trim()
            
            // Lấy tên đường, số nhà từ EditText
            val tenDuong = findViewById<EditText>(R.id.etTenDuong).text.toString().trim()

            // ===== GHÉP ĐỊA CHỈ ĐẦY ĐỦ =====
            // Tạo danh sách [phường, quận, tỉnh]
            // filter { it.isNotEmpty() } → Loại bỏ các phần tử rỗng
            // joinToString(", ") → Nối các phần tử bằng dấu phẩy
            // VD: "Phường Phú Quý, Quận Ngũ Hành Sơn, Đà Nẵng"
            val diaChiDayDu = listOf(phuongXa, quanHuyen, tinhThanh)
                .filter { it.isNotEmpty() } // Chỉ lấy phần tử không rỗng
                .joinToString(", ") // Nối bằng dấu phẩy

            // ===== LƯU ĐỊA CHỈ VÀO SHAREDPREFERENCES =====
            // Gọi hàm saveAddress() từ DiaChiPrefs để lưu địa chỉ
            DiaChiPrefs.saveAddress(
                this, // Context
                DiaChiItem( // Tạo object DiaChiItem
                    hoTen = hoTen, // Họ tên
                    soDienThoai = sdt, // Số điện thoại
                    tenDuong = tenDuong, // Tên đường, số nhà
                    diaChi = diaChiDayDu, // Địa chỉ đầy đủ (phường, quận, tỉnh)
                    isMacDinh = switchMacDinh.isChecked // true nếu Switch bật, false nếu tắt
                )
            )

            // ===== TRẢ KẾT QUẢ VỀ ACTIVITY TRƯỚC =====
            // Tạo Intent để truyền dữ liệu về Activity trước (SoDiaChiActivity hoặc GioHangActivity)
            val intent = Intent().apply {
                putExtra("hoTen", hoTen) // Truyền họ tên
                putExtra("soDienThoai", sdt) // Truyền số điện thoại
                putExtra("diaChi", diaChiDayDu) // Truyền địa chỉ đầy đủ
                putExtra("tenDuong", tenDuong) // Truyền tên đường
            }
            
            // Đặt kết quả thành công (RESULT_OK) và truyền Intent
            setResult(Activity.RESULT_OK, intent)
            
            // Đóng Activity này, quay về màn hình trước
            finish()
        }
    }
}
