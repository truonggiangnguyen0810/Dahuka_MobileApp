package com.example.xem_san_pham;                           // Khai báo package, file nằm trong module xem_san_pham

public class SanPham {                                      // Tạo class SanPham — đại diện cho 1 sản phẩm

    private String maSP;                                    // Mã sản phẩm, ví dụ: "SP001" — dùng để tìm hình ảnh trong collection HinhAnhSanPham
    private String ten;                                     // Tên sản phẩm, ví dụ: "Máy lọc nước RO 12 cấp"
    private String gia;                                     // Giá sản phẩm dạng chuỗi, ví dụ: "5,790,000đ"
    private String hinhAnhUrl;                              // Đường dẫn URL ảnh từ API, ví dụ: "https://example.com/images/sp1.jpg"

    // Constructor — hàm khởi tạo, được gọi khi tạo object mới
    // Ví dụ: new SanPham("SP001", "Máy lọc", "5,790,000đ", "https://...")
    public SanPham(String maSP, String ten, String gia, String hinhAnhUrl) {
        this.maSP = maSP;                                   // this.maSP = biến của class ← maSP = tham số truyền vào
        this.ten = ten;                                     // this.ten = biến của class ← ten = tham số truyền vào
        this.gia = gia;                                     // this.gia = biến của class ← gia = tham số truyền vào
        this.hinhAnhUrl = hinhAnhUrl;                       // this.hinhAnhUrl = biến của class ← hinhAnhUrl = tham số truyền vào
    }

    // Getter — hàm lấy giá trị ra dùng ở nơi khác
    // Ví dụ: String m = sp.getMaSP(); → m = "SP001"
    public String getMaSP() {
        return maSP;                                        // Trả về giá trị maSP
    }

    public String getTen() {
        return ten;                                         // Trả về giá trị ten — dùng để hiển thị lên TextView
    }

    public String getGia() {
        return gia;                                         // Trả về giá trị gia — dùng để hiển thị lên TextView
    }

    public String getHinhAnhUrl() {
        return hinhAnhUrl;                                  // Trả về URL ảnh — Glide sẽ dùng URL này để load ảnh
    }
}