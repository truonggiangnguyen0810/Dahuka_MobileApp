# TÀI LIỆU API QUẢN LÝ ĐƠN HÀNG

## Base URL
```
https://boney-unspoiled-thesis.ngrok-free.dev/
```

---

## 📦 API ĐƠN HÀNG (DON-HANG)

### 1. GET - Lấy danh sách tất cả đơn hàng
**Endpoint:** `GET /api/don-hang`

**Response:**
```json
[
  {
    "id": "DH001",
    "date": "2024-01-15",
    "customerName": "Nguyễn Văn A",
    "totalAmount": 500000,
    "status": "Placed",
    "address": "123 Đường ABC, Quận 1, TP.HCM",
    "phone": "0901234567",
    "products": []
  }
]
```

**Sử dụng trong code:**
```java
repository.getAllOrders(new OrderRepository.OrderCallback<List<Order>>() {
    @Override
    public void onSuccess(List<Order> orders) {
        // Xử lý danh sách đơn hàng
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 2. GET - Lấy thông tin chi tiết một đơn hàng
**Endpoint:** `GET /api/don-hang/{id}`

**Parameters:**
- `id` (path): ID của đơn hàng

**Response:**
```json
{
  "id": "DH001",
  "date": "2024-01-15",
  "customerName": "Nguyễn Văn A",
  "totalAmount": 500000,
  "status": "Placed",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "phone": "0901234567",
  "products": [
    {
      "name": "iPhone 15 Pro Max",
      "model": "256GB - Titan Tự Nhiên",
      "quantity": 2,
      "price": 29990000
    }
  ]
}
```

**Sử dụng trong code:**
```java
repository.getOrderById("DH001", new OrderRepository.OrderCallback<Order>() {
    @Override
    public void onSuccess(Order order) {
        // Xử lý thông tin đơn hàng
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 3. POST - Tạo đơn hàng mới
**Endpoint:** `POST /api/don-hang`

**Request Body:**
```json
{
  "date": "2024-01-15",
  "customerName": "Nguyễn Văn A",
  "totalAmount": 500000,
  "status": "Placed",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "phone": "0901234567"
}
```

**Response:**
```json
{
  "id": "DH001",
  "date": "2024-01-15",
  "customerName": "Nguyễn Văn A",
  "totalAmount": 500000,
  "status": "Placed",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "phone": "0901234567",
  "products": []
}
```

**Sử dụng trong code:**
```java
Order newOrder = new Order(null, "2024-01-15", "Nguyễn Văn A", 500000, "Placed");
newOrder.setAddress("123 Đường ABC, Quận 1, TP.HCM");
newOrder.setPhone("0901234567");

repository.createOrder(newOrder, new OrderRepository.OrderCallback<Order>() {
    @Override
    public void onSuccess(Order createdOrder) {
        // Xử lý đơn hàng đã tạo
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 4. PUT - Cập nhật thông tin đơn hàng
**Endpoint:** `PUT /api/don-hang/{id}`

**Parameters:**
- `id` (path): ID của đơn hàng cần cập nhật

**Request Body:**
```json
{
  "id": "DH001",
  "date": "2024-01-15",
  "customerName": "Nguyễn Văn A",
  "totalAmount": 500000,
  "status": "Confirmed",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "phone": "0901234567"
}
```

**Response:**
```json
{
  "id": "DH001",
  "date": "2024-01-15",
  "customerName": "Nguyễn Văn A",
  "totalAmount": 500000,
  "status": "Confirmed",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "phone": "0901234567",
  "products": []
}
```

**Sử dụng trong code:**
```java
order.setStatus("Confirmed");

repository.updateOrder("DH001", order, new OrderRepository.OrderCallback<Order>() {
    @Override
    public void onSuccess(Order updatedOrder) {
        // Xử lý đơn hàng đã cập nhật
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 5. DELETE - Xóa đơn hàng
**Endpoint:** `DELETE /api/don-hang/{id}`

**Parameters:**
- `id` (path): ID của đơn hàng cần xóa

**Response:** `204 No Content` (thành công)

**Sử dụng trong code:**
```java
repository.deleteOrder("DH001", new OrderRepository.OrderCallback<Void>() {
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

---

## 📋 API CHI TIẾT ĐƠN HÀNG (CHI-TIET-DON-HANG)

### 6. GET - Lấy tất cả chi tiết đơn hàng
**Endpoint:** `GET /api/chi-tiet-don-hang`

**Response:**
```json
[
  {
    "name": "iPhone 15 Pro Max",
    "model": "256GB - Titan Tự Nhiên",
    "quantity": 2,
    "price": 29990000
  },
  {
    "name": "Samsung Galaxy S24 Ultra",
    "model": "512GB - Đen",
    "quantity": 1,
    "price": 31990000
  }
]
```

**Sử dụng trong code:**
```java
repository.getAllOrderDetails(new OrderRepository.OrderCallback<List<Product>>() {
    @Override
    public void onSuccess(List<Product> products) {
        // Xử lý danh sách sản phẩm
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 7. GET - Lấy chi tiết đơn hàng theo ID đơn hàng
**Endpoint:** `GET /api/chi-tiet-don-hang/{orderId}`

**Parameters:**
- `orderId` (path): ID của đơn hàng

**Response:**
```json
[
  {
    "name": "iPhone 15 Pro Max",
    "model": "256GB - Titan Tự Nhiên",
    "quantity": 2,
    "price": 29990000
  }
]
```

**Sử dụng trong code:**
```java
repository.getOrderDetails("DH001", new OrderRepository.OrderCallback<List<Product>>() {
    @Override
    public void onSuccess(List<Product> products) {
        // Xử lý danh sách sản phẩm của đơn hàng
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 8. POST - Thêm sản phẩm vào đơn hàng
**Endpoint:** `POST /api/chi-tiet-don-hang`

**Request Body:**
```json
{
  "orderId": "DH001",
  "productId": "PROD001",
  "productName": "iPhone 15 Pro Max",
  "model": "256GB - Titan Tự Nhiên",
  "quantity": 2,
  "price": 29990000
}
```

**Response:**
```json
{
  "name": "iPhone 15 Pro Max",
  "model": "256GB - Titan Tự Nhiên",
  "quantity": 2,
  "price": 29990000
}
```

**Sử dụng trong code:**
```java
OrderDetailRequest request = new OrderDetailRequest(
    "DH001",
    "PROD001",
    "iPhone 15 Pro Max",
    "256GB - Titan Tự Nhiên",
    2,
    29990000
);

repository.addOrderDetail(request, new OrderRepository.OrderCallback<Product>() {
    @Override
    public void onSuccess(Product product) {
        // Xử lý sản phẩm đã thêm
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 9. PUT - Cập nhật chi tiết đơn hàng
**Endpoint:** `PUT /api/chi-tiet-don-hang/{id}`

**Parameters:**
- `id` (path): ID của chi tiết đơn hàng cần cập nhật

**Request Body:**
```json
{
  "name": "iPhone 15 Pro Max",
  "model": "256GB - Titan Tự Nhiên",
  "quantity": 3,
  "price": 29990000
}
```

**Response:**
```json
{
  "name": "iPhone 15 Pro Max",
  "model": "256GB - Titan Tự Nhiên",
  "quantity": 3,
  "price": 29990000
}
```

**Sử dụng trong code:**
```java
Product updatedProduct = new Product(
    "iPhone 15 Pro Max",
    "256GB - Titan Tự Nhiên",
    3,
    29990000
);

repository.updateOrderDetail("DETAIL001", updatedProduct, new OrderRepository.OrderCallback<Product>() {
    @Override
    public void onSuccess(Product product) {
        // Xử lý sản phẩm đã cập nhật
    }
    
    @Override
    public void onError(String errorMessage) {
        // Xử lý lỗi
    }
});
```

---

### 10. DELETE - Xóa sản phẩm khỏi đơn hàng
**Endpoint:** `DELETE /api/chi-tiet-don-hang/{id}`

**Parameters:**
- `id` (path): ID của chi tiết đơn hàng cần xóa

**Response:** `204 No Content` (thành công)

**Sử dụng trong code:**
```java
repository.deleteOrderDetail("DETAIL001", new OrderRepository.OrderCallback<Void>() {
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

---

## 📊 TRẠNG THÁI ĐƠN HÀNG

Các trạng thái có thể có của đơn hàng:

- **Placed**: Đơn hàng đã được đặt
- **Confirmed**: Đơn hàng đã được xác nhận
- **Shipping**: Đơn hàng đang được giao
- **Delivered**: Đơn hàng đã được giao thành công
- **Cancelled**: Đơn hàng đã bị hủy

---

## 🔧 CÁC CLASS CHÍNH

### 1. OrderApiService.java
Interface định nghĩa tất cả các API endpoints

### 2. OrderRepository.java
Class quản lý các API calls với callback xử lý response

### 3. Order.java
Model class cho đơn hàng

### 4. Product.java
Model class cho sản phẩm

### 5. OrderDetailRequest.java
Request body class cho việc thêm sản phẩm vào đơn hàng

### 6. ApiUsageExample.java
File chứa các ví dụ sử dụng tất cả các API methods

---

## 📝 LƯU Ý

1. Tất cả các API đều sử dụng Retrofit để gọi
2. Response được xử lý thông qua callback interface
3. Cần xử lý cả trường hợp thành công (onSuccess) và lỗi (onError)
4. Base URL có thể thay đổi trong file `RetrofitClient.java`
5. Tất cả các API đều hỗ trợ JSON format

---

## 🚀 CÁCH SỬ DỤNG NHANH

```java
// Khởi tạo repository
OrderRepository repository = new OrderRepository();

// Lấy danh sách đơn hàng
repository.getAllOrders(callback);

// Tạo đơn hàng mới
repository.createOrder(newOrder, callback);

// Cập nhật đơn hàng
repository.updateOrder(orderId, order, callback);

// Xóa đơn hàng
repository.deleteOrder(orderId, callback);

// Thêm sản phẩm vào đơn hàng
repository.addOrderDetail(request, callback);
```

Xem file `ApiUsageExample.java` để biết thêm chi tiết và ví dụ cụ thể!
