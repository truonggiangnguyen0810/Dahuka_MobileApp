# SCRIPT TEST API VỚI DỮ LIỆU ẢO

## Base URL
```
https://boney-unspoiled-thesis.ngrok-free.dev
```

---

## 1️⃣ TEST POST - TẠO ĐƠN HÀNG MỚI

### Tạo đơn hàng 1
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

### Tạo đơn hàng 2
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

### Tạo đơn hàng 3
```bash
curl -X POST https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2024-01-17",
    "customerName": "Lê Văn C",
    "totalAmount": 45000000,
    "status": "Shipping",
    "address": "789 Đường Trần Hưng Đạo, Quận 5, TP.HCM",
    "phone": "0923456789",
    "products": [
      {
        "name": "MacBook Pro M3",
        "model": "14 inch - 16GB RAM - 512GB SSD",
        "quantity": 1,
        "price": 45000000
      }
    ]
  }'
```

### Tạo đơn hàng 4 (Đã giao)
```bash
curl -X POST https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2024-01-10",
    "customerName": "Phạm Thị D",
    "totalAmount": 25000000,
    "status": "Delivered",
    "address": "321 Đường Võ Văn Tần, Quận 10, TP.HCM",
    "phone": "0934567890",
    "products": [
      {
        "name": "iPad Pro 12.9",
        "model": "256GB - WiFi + Cellular",
        "quantity": 1,
        "price": 25000000
      }
    ]
  }'
```

### Tạo đơn hàng 5 (Đã hủy)
```bash
curl -X POST https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2024-01-12",
    "customerName": "Hoàng Văn E",
    "totalAmount": 18000000,
    "status": "Cancelled",
    "cancelDate": "2024-01-13",
    "cancelReason": "Khách hàng đổi ý",
    "address": "555 Đường Cách Mạng Tháng 8, Quận Tân Bình, TP.HCM",
    "phone": "0945678901",
    "products": [
      {
        "name": "Apple Watch Ultra 2",
        "model": "Titanium - GPS + Cellular",
        "quantity": 2,
        "price": 9000000
      }
    ]
  }'
```

---

## 2️⃣ TEST GET - LẤY DANH SÁCH ĐƠN HÀNG

```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang
```

---

## 3️⃣ TEST GET BY ID - LẤY ĐƠN HÀNG THEO ID

Thay `{id}` bằng ID thực tế từ response của POST
```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id}
```

Ví dụ:
```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/DH001
```

---

## 4️⃣ TEST PUT - CẬP NHẬT ĐƠN HÀNG

### Cập nhật trạng thái đơn hàng
```bash
curl -X PUT https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "id": "{id}",
    "date": "2024-01-15",
    "customerName": "Nguyễn Văn A",
    "totalAmount": 59980000,
    "status": "Confirmed",
    "address": "123 Đường Lê Lợi, Quận 1, TP.HCM",
    "phone": "0901234567"
  }'
```

### Hủy đơn hàng
```bash
curl -X PUT https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "id": "{id}",
    "date": "2024-01-15",
    "customerName": "Nguyễn Văn A",
    "totalAmount": 59980000,
    "status": "Cancelled",
    "cancelDate": "2024-01-18",
    "cancelReason": "Khách hàng yêu cầu hủy",
    "address": "123 Đường Lê Lợi, Quận 1, TP.HCM",
    "phone": "0901234567"
  }'
```

---

## 5️⃣ TEST DELETE - XÓA ĐƠN HÀNG

```bash
curl -X DELETE https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id}
```

Ví dụ:
```bash
curl -X DELETE https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/DH001
```

---

## 6️⃣ TEST CHI TIẾT ĐƠN HÀNG

### GET - Lấy tất cả chi tiết đơn hàng
```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/chi-tiet-don-hang
```

### GET - Lấy chi tiết theo ID đơn hàng
```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/chi-tiet-don-hang/{orderId}
```

### POST - Thêm sản phẩm vào đơn hàng
```bash
curl -X POST https://boney-unspoiled-thesis.ngrok-free.dev/api/chi-tiet-don-hang \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "{orderId}",
    "productId": "PROD002",
    "productName": "AirPods Pro 2",
    "model": "USB-C",
    "quantity": 1,
    "price": 6490000
  }'
```

### PUT - Cập nhật chi tiết đơn hàng
```bash
curl -X PUT https://boney-unspoiled-thesis.ngrok-free.dev/api/chi-tiet-don-hang/{detailId} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "AirPods Pro 2",
    "model": "USB-C",
    "quantity": 2,
    "price": 6490000
  }'
```

### DELETE - Xóa sản phẩm khỏi đơn hàng
```bash
curl -X DELETE https://boney-unspoiled-thesis.ngrok-free.dev/api/chi-tiet-don-hang/{detailId}
```

---

## 🔥 SCRIPT NHANH - TẠO 5 ĐƠN HÀNG MẪU

Chạy script này để tạo nhanh 5 đơn hàng với các trạng thái khác nhau:

```bash
#!/bin/bash

BASE_URL="https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang"

echo "Đang tạo đơn hàng 1..."
curl -X POST $BASE_URL -H "Content-Type: application/json" -d '{"date":"2024-01-15","customerName":"Nguyễn Văn A","totalAmount":59980000,"status":"Placed","address":"123 Đường Lê Lợi, Q1, HCM","phone":"0901234567","products":[{"name":"iPhone 15 Pro Max","model":"256GB","quantity":2,"price":29990000}]}'

echo -e "\n\nĐang tạo đơn hàng 2..."
curl -X POST $BASE_URL -H "Content-Type: application/json" -d '{"date":"2024-01-16","customerName":"Trần Thị B","totalAmount":31990000,"status":"Confirmed","address":"456 Đường Nguyễn Huệ, Q3, HCM","phone":"0912345678","products":[{"name":"Samsung Galaxy S24 Ultra","model":"512GB","quantity":1,"price":31990000}]}'

echo -e "\n\nĐang tạo đơn hàng 3..."
curl -X POST $BASE_URL -H "Content-Type: application/json" -d '{"date":"2024-01-17","customerName":"Lê Văn C","totalAmount":45000000,"status":"Shipping","address":"789 Đường Trần Hưng Đạo, Q5, HCM","phone":"0923456789","products":[{"name":"MacBook Pro M3","model":"14 inch","quantity":1,"price":45000000}]}'

echo -e "\n\nĐang tạo đơn hàng 4..."
curl -X POST $BASE_URL -H "Content-Type: application/json" -d '{"date":"2024-01-10","customerName":"Phạm Thị D","totalAmount":25000000,"status":"Delivered","address":"321 Đường Võ Văn Tần, Q10, HCM","phone":"0934567890","products":[{"name":"iPad Pro 12.9","model":"256GB","quantity":1,"price":25000000}]}'

echo -e "\n\nĐang tạo đơn hàng 5..."
curl -X POST $BASE_URL -H "Content-Type: application/json" -d '{"date":"2024-01-12","customerName":"Hoàng Văn E","totalAmount":18000000,"status":"Cancelled","cancelDate":"2024-01-13","cancelReason":"Khách đổi ý","address":"555 Đường CMT8, Q.Tân Bình, HCM","phone":"0945678901","products":[{"name":"Apple Watch Ultra 2","model":"Titanium","quantity":2,"price":9000000}]}'

echo -e "\n\n✅ Hoàn thành tạo 5 đơn hàng mẫu!"
```

Lưu script trên vào file `create_sample_orders.sh` và chạy:
```bash
chmod +x create_sample_orders.sh
./create_sample_orders.sh
```

---

## 📱 SỬ DỤNG TRONG ANDROID APP

Sau khi tạo dữ liệu ảo bằng curl, bạn có thể:

1. **Mở TestApiActivity** trong app để test các operations
2. **Nhấn "GET All"** để xem danh sách đơn hàng vừa tạo
3. **Nhấn "▶ TEST TẤT CẢ"** để chạy toàn bộ flow: POST → GET → PUT
4. **Test từng button** để kiểm tra từng API method

---

## 🎯 CHECKLIST TEST

- [ ] POST - Tạo được đơn hàng mới
- [ ] GET - Lấy được danh sách đơn hàng
- [ ] GET by ID - Lấy được chi tiết đơn hàng
- [ ] PUT - Cập nhật được trạng thái đơn hàng
- [ ] DELETE - Xóa được đơn hàng
- [ ] GET Details - Lấy được chi tiết sản phẩm
- [ ] POST Detail - Thêm được sản phẩm vào đơn hàng
- [ ] PUT Detail - Cập nhật được chi tiết sản phẩm
- [ ] DELETE Detail - Xóa được sản phẩm khỏi đơn hàng

---

## 💡 LƯU Ý

1. Thay `{id}`, `{orderId}`, `{detailId}` bằng ID thực tế từ response
2. Kiểm tra response status code (200, 201, 204, 404, 500...)
3. Xem log trong Android Studio để debug
4. Đảm bảo server đang chạy và có thể truy cập được
