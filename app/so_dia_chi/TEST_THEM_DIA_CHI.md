# 🧪 HƯỚNG DẪN TEST THÊM ĐỊA CHỈ

## ✅ ĐÃ SỬA XONG

### Các thay đổi chính:

1. **AddressViewModel.java**
   - ✅ Sửa `loadAddressesFromApi()` để luôn dùng fallback "KH_001"
   - ✅ Thêm log chi tiết cho mọi bước
   - ✅ Xóa method `fetchByUserId()` không cần thiết
   - ✅ Cải thiện `mapAndSetAddresses()` với log

2. **AddressListFragment.java**
   - ✅ Khởi tạo adapter trước khi observe
   - ✅ Gọi `adapter.updateAddresses()` khi có dữ liệu mới
   - ✅ Thêm log để debug

3. **AddressAdapter.java**
   - ✅ Thay `final` thành biến có thể thay đổi
   - ✅ Thêm method `updateAddresses()` để cập nhật danh sách
   - ✅ Gọi `notifyDataSetChanged()` để refresh UI

---

## 🚀 CÁCH TEST

### Bước 1: Clean & Rebuild
```
Build > Clean Project
Build > Rebuild Project
```

### Bước 2: Chạy App và Mở Logcat

1. Chạy module `so_dia_chi`
2. Mở Logcat
3. Filter: `AddressViewModel|AddressListFragment`

### Bước 3: Kiểm Tra Load Danh Sách

Khi app khởi động, bạn sẽ thấy log:
```
D/AddressViewModel: loadAddressesFromApi - maKH: null, userId: -1
D/AddressViewModel: Using fallback maKH: KH_001
D/AddressViewModel: ✅ GET addresses success! Count: 3
D/AddressViewModel: Mapping 3 addresses
D/AddressViewModel:   - Mapped: Nguyen Van A (69ef28bd04e60f2ea5a7de5c)
D/AddressViewModel:   - Mapped: Nguyen Van A (69ef28be04e60f2ea5a7de5f)
D/AddressViewModel:   - Mapped: Nguyen Van A (69ef28bf04e60f2ea5a7de62)
D/AddressViewModel: ✅ Set 3 addresses to LiveData
D/AddressListFragment: Addresses updated: 3 items
```

### Bước 4: Test Thêm Địa Chỉ Mới

1. **Nhấn nút "Thêm địa chỉ"**
2. **Điền thông tin:**
   ```
   Họ tên: Test User
   Số điện thoại: 0909999999
   Địa chỉ chi tiết: 999 Test Street
   Thành phố/Quận/Phường: Phuong Test, Quan Test, TP.HCM
   ```
3. **Nhấn "Hoàn thành"**

### Bước 5: Kiểm Tra Log

Bạn sẽ thấy:
```
D/AddressViewModel: addAddress - maKH: null
E/AddressViewModel: maKH is null or empty, using default KH_001
D/AddressViewModel: Creating address: Test User - 999 Test Street
D/AddressViewModel: ✅ Create address success! ID: 69ef...
D/AddressViewModel: loadAddressesFromApi - maKH: null, userId: -1
D/AddressViewModel: Using fallback maKH: KH_001
D/AddressViewModel: ✅ GET addresses success! Count: 4
D/AddressViewModel: Mapping 4 addresses
D/AddressViewModel:   - Mapped: Nguyen Van A (69ef28bd04e60f2ea5a7de5c)
D/AddressViewModel:   - Mapped: Nguyen Van A (69ef28be04e60f2ea5a7de5f)
D/AddressViewModel:   - Mapped: Nguyen Van A (69ef28bf04e60f2ea5a7de62)
D/AddressViewModel:   - Mapped: Test User (69ef...)
D/AddressViewModel: ✅ Set 4 addresses to LiveData
D/AddressListFragment: Addresses updated: 4 items
```

### Bước 6: Kiểm Tra Giao Diện

- ✅ Quay về màn hình danh sách
- ✅ Thấy địa chỉ mới xuất hiện ở cuối danh sách
- ✅ Tổng cộng có 4 địa chỉ

---

## 🔍 KIỂM TRA BẰNG POSTMAN

### 1. Kiểm tra danh sách hiện tại
```
GET https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi/khach-hang/KH_001
```

Bạn sẽ thấy 3 địa chỉ đã tạo trước đó.

### 2. Thêm địa chỉ mới
```
POST https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi
Content-Type: application/json

{
  "tenNguoiNhan": "Test User",
  "email": "0909999999",
  "diaChiCuThe": "999 Test Street",
  "phuongXa": "Phuong Test",
  "quanHuyen": "Quan Test",
  "thanhPho": "TP. Ho Chi Minh",
  "diaChiMacDinh": 0,
  "maKhachHang": "KH_001"
}
```

### 3. Kiểm tra lại danh sách
```
GET https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi/khach-hang/KH_001
```

Bạn sẽ thấy 4 địa chỉ (3 cũ + 1 mới).

---

## 🐛 XỬ LÝ LỖI

### Lỗi 1: Không thấy log "Addresses updated"
**Nguyên nhân:** Observer không được gọi

**Giải pháp:**
1. Kiểm tra xem có log "✅ Set X addresses to LiveData" không
2. Nếu có, vấn đề là ở Fragment
3. Nếu không, vấn đề là ở ViewModel

### Lỗi 2: Log hiển thị "Count: 3" nhưng UI không update
**Nguyên nhân:** Adapter không được cập nhật

**Giải pháp:**
1. Kiểm tra xem có gọi `adapter.updateAddresses()` không
2. Kiểm tra xem có gọi `notifyDataSetChanged()` không

### Lỗi 3: "❌ Create address failed: 400"
**Nguyên nhân:** Dữ liệu gửi lên không đúng format

**Giải pháp:**
1. Xem error body trong log
2. Kiểm tra field names (phải là camelCase)
3. Kiểm tra tất cả fields bắt buộc đã có

### Lỗi 4: Địa chỉ mới không xuất hiện
**Nguyên nhân:** `loadAddressesFromApi()` không được gọi sau POST

**Giải pháp:**
1. Kiểm tra log xem có "✅ Create address success!" không
2. Kiểm tra xem có gọi `loadAddressesFromApi()` sau đó không
3. Kiểm tra xem GET API có trả về địa chỉ mới không

---

## 📊 FLOW HOÀN CHỈNH

```
1. User nhấn "Thêm địa chỉ"
   ↓
2. Điền thông tin và nhấn "Hoàn thành"
   ↓
3. AddEditAddressFragment gọi viewModel.addAddress()
   ↓
4. AddressViewModel POST địa chỉ lên server
   ↓
5. Server trả về response thành công
   ↓
6. AddressViewModel gọi loadAddressesFromApi()
   ↓
7. AddressViewModel GET danh sách mới từ server
   ↓
8. AddressViewModel map và set vào LiveData
   ↓
9. AddressListFragment observe thấy thay đổi
   ↓
10. AddressListFragment gọi adapter.updateAddresses()
   ↓
11. Adapter gọi notifyDataSetChanged()
   ↓
12. RecyclerView refresh UI
   ↓
13. ✅ Địa chỉ mới xuất hiện trong danh sách
```

---

## ✅ CHECKLIST

- [x] Sửa loadAddressesFromApi() với fallback
- [x] Thêm log chi tiết
- [x] Sửa AddressListFragment khởi tạo adapter
- [x] Thêm method updateAddresses() vào adapter
- [ ] Clean & Rebuild project
- [ ] Test thêm địa chỉ mới
- [ ] Kiểm tra log
- [ ] Kiểm tra UI

---

## 🎯 KẾT QUẢ MONG ĐỢI

Sau khi thêm địa chỉ mới:

1. ✅ Toast: "Đang thêm địa chỉ..."
2. ✅ Button: "Đang xử lý..."
3. ✅ Log: "✅ Create address success!"
4. ✅ Log: "✅ GET addresses success! Count: 4"
5. ✅ Log: "Addresses updated: 4 items"
6. ✅ UI: Địa chỉ mới xuất hiện trong danh sách
7. ✅ Tổng: 4 địa chỉ (3 cũ + 1 mới)

---

## 🎉 HOÀN THÀNH!

Bây giờ bạn có thể **Clean & Rebuild** và test thêm địa chỉ mới! 🚀

Nếu vẫn gặp vấn đề, hãy gửi log từ Logcat để tôi có thể giúp debug.
