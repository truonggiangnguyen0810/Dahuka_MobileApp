# 🎲 HƯỚNG DẪN TẠO DỮ LIỆU MẪU

## 📋 Tổng quan

Hiện tại server chưa có dữ liệu đơn hàng. Bạn cần POST dữ liệu mẫu lên server để test các thao tác GET, PUT, DELETE.

---

## 🚀 CÁCH 1: Chạy Script Tự Động (Khuyến nghị)

### Trên Windows (PowerShell):

1. Mở PowerShell trong thư mục `app/ql_don_hang`
2. Chạy lệnh:

```powershell
.\create_sample_data.ps1
```

### Trên Linux/Mac (Bash):

1. Mở Terminal trong thư mục `app/ql_don_hang`
2. Cấp quyền thực thi:

```bash
chmod +x create_sample_data.sh
```

3. Chạy script:

```bash
./create_sample_data.sh
```

Script sẽ tự động tạo **5 đơn hàng mẫu** với các trạng thái khác nhau.

---

## 🔧 CÁCH 2: POST Thủ Công Bằng Curl

### Tạo đơn hàng 1:

```bash
curl -X POST https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2024-01-15",
    "customerName": "Nguyễn Văn A",
    "totalAmount": 59980000,
    "status": "Placed",
    "address": "123 Đường Lê Lợi, Quận 1, TP.HCM",
    "phone": "0901234567",
    "products": [
      {
        "name": "iPhone 15 Pro Max",
        "model": "256GB - Titan Tự Nhiên",
        "quantity": 2,
        "price": 29990000
      }
    ]
  }'
```

### Tạo đơn hàng 2:

```bash
curl -X POST https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2024-01-16",
    "customerName": "Trần Thị B",
    "totalAmount": 31990000,
    "status": "Confirmed",
    "address": "456 Đường Nguyễn Huệ, Quận 3, TP.HCM",
    "phone": "0912345678",
    "products": [
      {
        "name": "Samsung Galaxy S24 Ultra",
        "model": "512GB - Đen",
        "quantity": 1,
        "price": 31990000
      }
    ]
  }'
```

**Xem thêm:** File `test_api_script.md` có đầy đủ 5 đơn hàng mẫu

---

## 📱 CÁCH 3: Sử Dụng App Android

### Bước 1: Chạy TestApiActivity

1. Build và chạy module `ql_don_hang`
2. App sẽ mở **TestApiActivity**

### Bước 2: Tạo dữ liệu

Nhấn button **"🎲 TẠO 5 ĐƠN HÀNG MẪU"**

App sẽ tự động POST 5 đơn hàng lên server.

---

## ✅ Kiểm Tra Dữ Liệu Đã Tạo

### Cách 1: Dùng Curl

```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang
```

### Cách 2: Dùng App

1. Mở TestApiActivity
2. Nhấn button **"GET All"**
3. Xem log output

### Cách 3: Dùng Browser/Postman

Truy cập: `https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang`

---

## 📊 Dữ Liệu Mẫu Sẽ Được Tạo

| # | Sản phẩm | Khách hàng | Giá | Trạng thái |
|---|----------|------------|-----|------------|
| 1 | iPhone 15 Pro Max | Nguyễn Văn A | 59,980,000đ | Placed |
| 2 | Samsung Galaxy S24 Ultra | Trần Thị B | 31,990,000đ | Confirmed |
| 3 | MacBook Pro M3 | Lê Văn C | 45,000,000đ | Shipping |
| 4 | iPad Pro 12.9 | Phạm Thị D | 25,000,000đ | Delivered |
| 5 | Apple Watch Ultra 2 | Hoàng Văn E | 18,000,000đ | Cancelled |

---

## 🎯 Sau Khi Tạo Dữ Liệu

Bạn có thể test các operations:

### ✅ GET - Lấy danh sách
```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang
```

### ✅ GET by ID - Lấy chi tiết
```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id}
```

### ✅ PUT - Cập nhật
```bash
curl -X PUT https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id} \
  -H "Content-Type: application/json" \
  -d '{...}'
```

### ✅ DELETE - Xóa
```bash
curl -X DELETE https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id}
```

---

## 🐛 Xử Lý Lỗi

### Lỗi: "Connection refused"
- Kiểm tra server có đang chạy không
- Kiểm tra URL có đúng không

### Lỗi: "404 Not Found"
- Kiểm tra endpoint `/api/don-hang` có tồn tại không
- Kiểm tra server có implement POST method không

### Lỗi: "500 Internal Server Error"
- Kiểm tra format JSON có đúng không
- Xem log server để biết chi tiết

---

## 📞 Lưu Ý

- Mỗi lần chạy script sẽ tạo thêm 5 đơn hàng mới
- ID đơn hàng được tạo tự động bởi server
- Dữ liệu được lưu trên server, không phải local
- Có thể xóa dữ liệu bằng DELETE API

---

## 🎉 Hoàn Thành

Sau khi tạo dữ liệu thành công, bạn có thể:
1. ✅ Test GET để xem danh sách
2. ✅ Test PUT để cập nhật trạng thái
3. ✅ Test DELETE để xóa đơn hàng
4. ✅ Tích hợp vào app thực tế

**Chúc bạn test thành công! 🚀**
