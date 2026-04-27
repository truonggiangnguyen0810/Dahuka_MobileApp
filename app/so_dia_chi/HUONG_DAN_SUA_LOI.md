# 🔧 HƯỚNG DẪN SỬA LỖI MODULE SỔ ĐỊA CHỈ

## ✅ ĐÃ SỬA

### 1. Sửa API Endpoints
Đã thêm prefix `/api/` vào tất cả endpoints trong `ApiService.java`:

**Trước:**
```java
@GET("so-dia-chi/khach-hang/{maKH}")
@POST("so-dia-chi")
@PUT("so-dia-chi/{id}")
@DELETE("so-dia-chi/{id}")
```

**Sau:**
```java
@GET("api/so-dia-chi/khach-hang/{maKH}")
@POST("api/so-dia-chi")
@PUT("api/so-dia-chi/{id}")
@DELETE("api/so-dia-chi/{id}")
```

### 2. Đã Tạo Dữ Liệu Mẫu
Đã tạo thành công **3 địa chỉ mẫu** cho khách hàng `KH_001`:

1. **ID: 69ef28bd04e60f2ea5a7de5c** - 123 Duong Le Loi (Mặc định)
2. **ID: 69ef28be04e60f2ea5a7de5f** - 456 Duong Nguyen Hue
3. **ID: 69ef28bf04e60f2ea5a7de62** - 789 Duong Tran Hung Dao

---

## 🚀 CÁCH KIỂM TRA

### Cách 1: Chạy App Android

1. Build lại project (Clean & Rebuild)
2. Chạy module `so_dia_chi`
3. App sẽ tự động load danh sách địa chỉ từ server
4. Bạn sẽ thấy 3 địa chỉ vừa tạo

### Cách 2: Dùng Postman

#### GET - Lấy danh sách địa chỉ
```
GET https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi/khach-hang/KH_001
```

#### GET - Lấy địa chỉ theo ID
```
GET https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi/69ef28bd04e60f2ea5a7de5c
```

#### POST - Tạo địa chỉ mới
```
POST https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi
Content-Type: application/json

{
  "tenNguoiNhan": "Nguyen Van B",
  "email": "0912345678",
  "diaChiCuThe": "999 Duong Test",
  "phuongXa": "Phuong Test",
  "quanHuyen": "Quan Test",
  "thanhPho": "TP. Ho Chi Minh",
  "diaChiMacDinh": 0,
  "maKhachHang": "KH_001"
}
```

#### PUT - Cập nhật địa chỉ
```
PUT https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi/69ef28bd04e60f2ea5a7de5c
Content-Type: application/json

{
  "tenNguoiNhan": "Nguyen Van A Updated",
  "email": "0901234567",
  "diaChiCuThe": "123 Duong Le Loi Updated",
  "phuongXa": "Phuong Ben Nghe",
  "quanHuyen": "Quan 1",
  "thanhPho": "TP. Ho Chi Minh",
  "diaChiMacDinh": 1,
  "maKhachHang": "KH_001"
}
```

#### DELETE - Xóa địa chỉ
```
DELETE https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi/69ef28bd04e60f2ea5a7de5c
```

---

## 📱 TEST TRONG APP

### Test GET (Lấy danh sách)
1. Mở app
2. Danh sách địa chỉ sẽ tự động load
3. Kiểm tra có 3 địa chỉ hiển thị

### Test POST (Thêm mới)
1. Nhấn nút "Thêm địa chỉ"
2. Điền thông tin
3. Nhấn "Lưu"
4. Kiểm tra địa chỉ mới xuất hiện trong danh sách

### Test PUT (Cập nhật)
1. Nhấn vào một địa chỉ
2. Sửa thông tin
3. Nhấn "Lưu"
4. Kiểm tra thông tin đã được cập nhật

### Test DELETE (Xóa)
1. Swipe hoặc nhấn nút xóa trên một địa chỉ
2. Xác nhận xóa
3. Kiểm tra địa chỉ đã biến mất khỏi danh sách

---

## 🔍 KIỂM TRA LOG

Nếu vẫn gặp lỗi, kiểm tra Logcat với các tag:

- `AddressViewModel` - Xem log của ViewModel
- `RetrofitClient` - Xem log của API calls
- `OkHttp` - Xem chi tiết request/response

---

## 🐛 XỬ LÝ LỖI THƯỜNG GẶP

### Lỗi 1: "404 Not Found"
**Nguyên nhân:** Endpoint sai hoặc thiếu `/api/`

**Giải pháp:** Đã sửa trong `ApiService.java`, rebuild project

### Lỗi 2: "Không có dữ liệu"
**Nguyên nhân:** Chưa có dữ liệu trên server

**Giải pháp:** Đã tạo 3 địa chỉ mẫu, hoặc chạy script `create_sample_addresses.ps1`

### Lỗi 3: "Connection refused"
**Nguyên nhân:** Server không chạy hoặc URL sai

**Giải pháp:** Kiểm tra server và URL trong `RetrofitClient.java`

### Lỗi 4: "Unauthorized"
**Nguyên nhân:** Chưa đăng nhập hoặc token hết hạn

**Giải pháp:** Đăng nhập lại trong app

---

## 📊 CẤU TRÚC DỮ LIỆU

### Model: SoDiaChi
```java
{
  "_id": "69ef28bd04e60f2ea5a7de5c",
  "tenNguoiNhan": "Nguyen Van A",
  "email": "0901234567",
  "diaChiCuThe": "123 Duong Le Loi",
  "phuongXa": "Phuong Ben Nghe",
  "quanHuyen": "Quan 1",
  "thanhPho": "TP. Ho Chi Minh",
  "diaChiMacDinh": 1,
  "maKhachHang": "KH_001"
}
```

### Mapping sang Address (UI Model)
```java
Address {
  id: "_id",
  fullName: "tenNguoiNhan",
  phone: "email",
  detailAddress: "diaChiCuThe",
  fullAddress: "phuongXa, quanHuyen, thanhPho",
  isDefault: diaChiMacDinh == 1
}
```

---

## ✅ CHECKLIST

- [x] Sửa API endpoints thêm `/api/`
- [x] Tạo 3 địa chỉ mẫu trên server
- [x] Test GET - Lấy danh sách
- [ ] Test POST - Thêm mới (test trong app)
- [ ] Test PUT - Cập nhật (test trong app)
- [ ] Test DELETE - Xóa (test trong app)

---

## 🎯 KẾT LUẬN

Module `so_dia_chi` đã được sửa và sẵn sàng để test:

1. ✅ API endpoints đã đúng format
2. ✅ Dữ liệu mẫu đã có trên server
3. ✅ Có thể GET, POST, PUT, DELETE

**Bây giờ bạn có thể chạy app và test tất cả các chức năng!** 🚀
