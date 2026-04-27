# 🧪 HƯỚNG DẪN TEST API QUẢN LÝ ĐƠN HÀNG

## 📋 Tổng quan

Module này cung cấp đầy đủ các API methods (GET, POST, PUT, DELETE) cho:
- **Đơn hàng** (`/api/don-hang`)
- **Chi tiết đơn hàng** (`/api/chi-tiet-don-hang`)

---

## 🚀 CÁCH 1: TEST BẰNG ANDROID APP (Khuyến nghị)

### Bước 1: Chạy TestApiActivity

1. Build và chạy module `ql_don_hang`
2. App sẽ tự động mở **TestApiActivity**
3. Giao diện hiển thị các button test

### Bước 2: Tạo dữ liệu mẫu

Nhấn button **"🎲 TẠO 5 ĐƠN HÀNG MẪU"**

App sẽ tự động tạo 5 đơn hàng với:
- ✅ Đơn hàng 1: iPhone 15 Pro Max - **Placed** (Đã đặt)
- ✅ Đơn hàng 2: Samsung Galaxy S24 Ultra - **Confirmed** (Đã xác nhận)
- ✅ Đơn hàng 3: MacBook Pro M3 - **Shipping** (Đang giao)
- ✅ Đơn hàng 4: iPad Pro - **Delivered** (Đã giao)
- ✅ Đơn hàng 5: Apple Watch Ultra 2 - **Cancelled** (Đã hủy)

### Bước 3: Test các operations

#### Test từng operation:
- **GET All**: Lấy danh sách tất cả đơn hàng
- **POST Tạo mới**: Tạo đơn hàng mới
- **GET by ID**: Lấy chi tiết đơn hàng theo ID
- **PUT Update**: Cập nhật trạng thái đơn hàng
- **DELETE**: Xóa đơn hàng

#### Test chi tiết đơn hàng:
- **GET Details**: Lấy danh sách sản phẩm trong đơn hàng
- **POST Thêm SP**: Thêm sản phẩm vào đơn hàng
- **PUT Update SP**: Cập nhật số lượng/giá sản phẩm
- **DELETE SP**: Xóa sản phẩm khỏi đơn hàng

#### Test tự động:
- **▶ TEST TẤT CẢ**: Chạy toàn bộ flow POST → GET → PUT tự động

### Bước 4: Xem kết quả

- Kết quả hiển thị trong **Log Output** ở dưới màn hình
- Toast notification hiển thị khi có thành công/lỗi
- Log chi tiết trong **Logcat** (tag: `TestApiActivity`)

---

## 🔧 CÁCH 2: TEST BẰNG CURL COMMANDS

### Tạo đơn hàng mẫu

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

### Lấy danh sách đơn hàng

```bash
curl -X GET https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang
```

### Cập nhật đơn hàng

```bash
curl -X PUT https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "id": "{id}",
    "status": "Confirmed",
    ...
  }'
```

### Xóa đơn hàng

```bash
curl -X DELETE https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang/{id}
```

**Xem thêm:** File `test_api_script.md` có đầy đủ các curl commands

---

## 💻 CÁCH 3: SỬ DỤNG TRONG CODE

### Khởi tạo Repository

```java
OrderRepository repository = new OrderRepository();
```

### Tạo đơn hàng mới

```java
Order newOrder = new Order(null, "2024-01-15", "Nguyễn Văn A", 500000L, "Placed");
newOrder.setAddress("123 Test Street");
newOrder.setPhone("0901234567");

repository.createOrder(newOrder, new OrderRepository.OrderCallback<Order>() {
    @Override
    public void onSuccess(Order order) {
        // Xử lý thành công
        String orderId = order.getId();
    }

    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

### Lấy danh sách đơn hàng

```java
repository.getAllOrders(new OrderRepository.OrderCallback<List<Order>>() {
    @Override
    public void onSuccess(List<Order> orders) {
        // Xử lý danh sách
        for (Order order : orders) {
            Log.d(TAG, "Order: " + order.getId());
        }
    }

    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

### Cập nhật đơn hàng

```java
order.setStatus("Confirmed");

repository.updateOrder(orderId, order, new OrderRepository.OrderCallback<Order>() {
    @Override
    public void onSuccess(Order updatedOrder) {
        // Cập nhật thành công
    }

    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

### Xóa đơn hàng

```java
repository.deleteOrder(orderId, new OrderRepository.OrderCallback<Void>() {
    @Override
    public void onSuccess(Void data) {
        // Xóa thành công
    }

    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

**Xem thêm:** File `ApiUsageExample.java` có 12 ví dụ chi tiết

---

## 📁 CẤU TRÚC FILES

```
app/ql_don_hang/src/main/java/com/example/ql_don_hang/
├── OrderApiService.java          # Interface định nghĩa API endpoints
├── OrderRepository.java           # Repository với callback handlers
├── Order.java                     # Model đơn hàng
├── Product.java                   # Model sản phẩm
├── OrderDetailRequest.java        # Request body cho chi tiết đơn hàng
├── RetrofitClient.java            # Retrofit configuration
├── TestApiActivity.java           # Activity test API
├── SampleDataGenerator.java       # Tạo dữ liệu mẫu
└── ApiUsageExample.java           # Ví dụ sử dụng API

app/ql_don_hang/
├── API_DOCUMENTATION.md           # Tài liệu API đầy đủ
├── test_api_script.md             # Curl commands để test
└── README_TEST.md                 # File này
```

---

## ✅ CHECKLIST TEST

### Đơn hàng
- [ ] POST - Tạo được đơn hàng mới
- [ ] GET - Lấy được danh sách đơn hàng
- [ ] GET by ID - Lấy được chi tiết đơn hàng
- [ ] PUT - Cập nhật được trạng thái
- [ ] DELETE - Xóa được đơn hàng

### Chi tiết đơn hàng
- [ ] GET - Lấy được danh sách sản phẩm
- [ ] GET by Order ID - Lấy được sản phẩm theo đơn hàng
- [ ] POST - Thêm được sản phẩm vào đơn hàng
- [ ] PUT - Cập nhật được thông tin sản phẩm
- [ ] DELETE - Xóa được sản phẩm

---

## 🎯 CÁC TRẠNG THÁI ĐƠN HÀNG

| Trạng thái | Mô tả | Màu sắc |
|------------|-------|---------|
| **Placed** | Đơn hàng vừa được đặt | 🔵 Xanh dương |
| **Confirmed** | Đã xác nhận đơn hàng | 🟢 Xanh lá |
| **Shipping** | Đang giao hàng | 🟡 Vàng |
| **Delivered** | Đã giao thành công | ✅ Xanh lá đậm |
| **Cancelled** | Đã hủy đơn hàng | 🔴 Đỏ |

---

## 🐛 TROUBLESHOOTING

### Lỗi: "Lỗi kết nối"
- Kiểm tra server có đang chạy không
- Kiểm tra URL trong `RetrofitClient.java`
- Kiểm tra internet connection

### Lỗi: "404 Not Found"
- Kiểm tra endpoint URL có đúng không
- Kiểm tra ID đơn hàng có tồn tại không

### Lỗi: "500 Internal Server Error"
- Kiểm tra dữ liệu gửi lên có đúng format không
- Xem log server để biết chi tiết lỗi

### Không thấy dữ liệu sau khi POST
- Đợi 1-2 giây rồi nhấn GET All
- Kiểm tra response có trả về ID không
- Xem Logcat để debug

---

## 📞 HỖ TRỢ

- Xem **API_DOCUMENTATION.md** để biết chi tiết API
- Xem **ApiUsageExample.java** để xem code mẫu
- Xem **test_api_script.md** để test bằng curl
- Check Logcat với tag `TestApiActivity` hoặc `OrderRepository`

---

## 🎉 HOÀN THÀNH

Sau khi test xong, bạn có thể:
1. Tích hợp API vào các màn hình thực tế
2. Thêm loading indicators
3. Thêm error handling UI
4. Thêm pull-to-refresh
5. Thêm pagination nếu cần

**Chúc bạn test thành công! 🚀**
