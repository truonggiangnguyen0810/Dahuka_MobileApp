# ⚡ TẠO DỮ LIỆU NHANH

## ✅ ĐÃ TẠO THÀNH CÔNG 5 ĐƠN HÀNG MẪU!

Các đơn hàng sau đã được tạo trên server:

1. **DonHang3** - iPhone 15 Pro Max - Placed
2. **DonHang4** - Samsung Galaxy S24 Ultra - Confirmed  
3. **DonHang5** - MacBook Pro M3 - Shipping
4. **DonHang6** - iPad Pro - Delivered
5. **DonHang7** - Apple Watch Ultra 2 - Cancelled

---

## 🚀 CÁCH KIỂM TRA DỮ LIỆU

### Cách 1: Dùng App Android

1. Build và chạy module `ql_don_hang`
2. App sẽ tự động load danh sách đơn hàng từ server
3. Bạn sẽ thấy 5 đơn hàng vừa tạo

### Cách 2: Dùng Postman

1. Mở Postman
2. Tạo request GET: `https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang`
3. Click Send
4. Xem danh sách đơn hàng

### Cách 3: Dùng Browser

1. Mở trình duyệt
2. Truy cập: `https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang`
3. Xem JSON response

---

## 🔧 TEST CÁC OPERATIONS

### ✅ GET - Lấy danh sách (Đã có dữ liệu)

```
GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang
```

### ✅ GET by ID - Lấy chi tiết

```
GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/DonHang3
```

### ✅ PUT - Cập nhật trạng thái

```
PUT https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/DonHang3
Body: {
  "status": "Confirmed",
  ...
}
```

### ✅ DELETE - Xóa đơn hàng

```
DELETE https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/DonHang3
```

---

## 📱 SỬ DỤNG TRONG APP

Bây giờ bạn có thể:

1. ✅ Chạy app và xem danh sách đơn hàng
2. ✅ Click vào từng đơn hàng để xem chi tiết
3. ✅ Test cập nhật trạng thái
4. ✅ Test hủy đơn hàng
5. ✅ Test xóa đơn hàng

---

## 🎯 TẠO THÊM DỮ LIỆU

Nếu muốn tạo thêm đơn hàng, dùng TestApiActivity:

1. Mở TestApiActivity trong app
2. Nhấn button **"POST Tạo mới"**
3. Hoặc nhấn **"🎲 TẠO 5 ĐƠN HÀNG MẪU"** để tạo thêm 5 đơn hàng nữa

---

## 📊 THÔNG TIN CÁC ĐƠN HÀNG ĐÃ TẠO

| ID | Khách hàng | Sản phẩm | Giá | Trạng thái |
|----|------------|----------|-----|------------|
| DonHang3 | Nguyen Van A | iPhone 15 Pro Max | 59,980,000đ | Placed |
| DonHang4 | Tran Thi B | Samsung Galaxy S24 Ultra | 31,990,000đ | Confirmed |
| DonHang5 | Le Van C | MacBook Pro M3 | 45,000,000đ | Shipping |
| DonHang6 | Pham Thi D | iPad Pro 12.9 | 25,000,000đ | Delivered |
| DonHang7 | Hoang Van E | Apple Watch Ultra 2 | 18,000,000đ | Cancelled |

---

## ✨ HOÀN THÀNH!

Dữ liệu mẫu đã sẵn sàng để test! 🎉

Bạn có thể bắt đầu test các thao tác:
- ✅ GET (Lấy danh sách)
- ✅ POST (Tạo mới)
- ✅ PUT (Cập nhật)
- ✅ DELETE (Xóa)

**Chúc bạn test thành công! 🚀**
