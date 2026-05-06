package com.example.gio_hang

// Import các thư viện cần thiết
import android.content.Intent // Để chuyển màn hình
import android.os.Bundle // Để lưu trạng thái Activity
import android.os.CountDownTimer // Để đếm ngược thời gian
import android.widget.ImageButton // Nút hình ảnh (nút X đóng)
import android.widget.TextView // Hiển thị text (phút, giây)
import androidx.appcompat.app.AppCompatActivity // Base class cho Activity
import com.google.android.material.button.MaterialButton // Nút Material Design

/**
 * Activity hiển thị mã QR thanh toán
 * Chức năng:
 * - Hiển thị mã QR để quét thanh toán
 * - Đếm ngược 15 phút (900 giây)
 * - Xác nhận đã thanh toán → Chuyển sang màn Đặt Hàng Thành Công
 * - Đóng màn hình → Quay về màn Đặt Hàng
 */
class ThanhToanQrActivity : AppCompatActivity() {

    // Biến lưu đối tượng CountDownTimer để đếm ngược
    // Khai báo nullable (?) vì ban đầu chưa khởi tạo
    private var countDownTimer: CountDownTimer? = null

    /**
     * Hàm được gọi khi Activity được tạo
     * Khởi tạo giao diện, bắt đầu đếm ngược
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Load layout từ file XML
        setContentView(R.layout.activity_thanh_toan_qr)

        // ===== NÚT ĐÓNG (X) =====
        // Tìm nút X trong layout
        findViewById<ImageButton>(R.id.btnClose).setOnClickListener {
            finish() // Đóng Activity này, quay về DonDatHangActivity
        }

        // ===== NÚT XÁC NHẬN ĐÃ THANH TOÁN =====
        // Tìm nút "Xác nhận đã thanh toán" trong layout
        findViewById<MaterialButton>(R.id.btnXacNhanThanhToan).setOnClickListener {
            goToSuccess() // Gọi hàm chuyển sang màn Đặt Hàng Thành Công
        }

        // ===== HIỂN THỊ THỜI GIAN ĐẾM NGƯỢC =====
        // Tìm TextView hiển thị phút (VD: "14")
        val tvMinutes = findViewById<TextView>(R.id.tvMinutes)
        
        // Tìm TextView hiển thị giây (VD: "59")
        val tvSeconds = findViewById<TextView>(R.id.tvSeconds)

        // ===== TẠO VÀ BẮT ĐẦU ĐẾM NGƯỢC =====
        // Tạo CountDownTimer với:
        // - Tổng thời gian: 5 phút = 5 * 60 * 1000 milliseconds = 300.000 ms
        // - Interval: 1000 ms = 1 giây (cập nhật mỗi giây)
        countDownTimer = object : CountDownTimer(5 * 60 * 1000L, 1000L) {
            
            /**
             * Hàm được gọi MỖI GIÂY (mỗi 1000ms)
             * @param millisUntilFinished: Số milliseconds còn lại
             */
            override fun onTick(millisUntilFinished: Long) {
                // Chuyển milliseconds → giây
                // VD: 299000 ms / 1000 = 299 giây
                val totalSeconds = millisUntilFinished / 1000
                
                // Tính số phút: 299 giây / 60 = 4 phút (lấy phần nguyên)
                // String.format("%02d", ...) → Format thành 2 chữ số (VD: 04)
                tvMinutes.text = String.format("%02d", totalSeconds / 60)
                
                // Tính số giây còn lại: 299 giây % 60 = 59 giây
                // String.format("%02d", ...) → Format thành 2 chữ số (VD: 59)
                tvSeconds.text = String.format("%02d", totalSeconds % 60)
            }

            /**
             * Hàm được gọi khi hết thời gian (0 giây)
             */
            override fun onFinish() {
                tvMinutes.text = "00" // Hiển thị 00 phút
                tvSeconds.text = "00" // Hiển thị 00 giây
            }
        }.start() // Bắt đầu đếm ngược ngay lập tức
    }

    /**
     * Hàm chuyển sang màn Đặt Hàng Thành Công
     * Được gọi khi user bấm "Xác nhận đã thanh toán"
     */
    private fun goToSuccess() {
        // Dừng đếm ngược (nếu đang chạy)
        // ?. = Safe call: Chỉ gọi cancel() nếu countDownTimer không null
        countDownTimer?.cancel()
        
        // Tạo Intent để chuyển sang DatHangThanhCongActivity
        startActivity(Intent(this, DatHangThanhCongActivity::class.java))
        
        // Đóng Activity này (không cho quay lại màn QR)
        finish()
    }

    /**
     * Hàm được gọi khi Activity bị hủy
     * VD: User bấm Back, hoặc finish() được gọi
     */
    override fun onDestroy() {
        super.onDestroy() // Gọi hàm cha (BẮT BUỘC)
        
        // Dừng đếm ngược để tránh memory leak
        // Nếu không dừng, CountDownTimer vẫn chạy ngầm → Tốn bộ nhớ
        countDownTimer?.cancel()
    }
}
