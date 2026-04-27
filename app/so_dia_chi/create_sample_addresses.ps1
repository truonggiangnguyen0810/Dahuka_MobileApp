# Script tạo dữ liệu mẫu cho API Sổ Địa Chỉ
# Chạy script này để POST 3 địa chỉ mẫu lên server

$baseUrl = "https://boney-unspoiled-thesis.ngrok-free.dev/api/so-dia-chi"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TẠO DỮ LIỆU MẪU CHO SỔ ĐỊA CHỈ" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Địa chỉ 1: Địa chỉ mặc định
Write-Host "Đang tạo địa chỉ 1 (Mặc định)..." -ForegroundColor Yellow
$address1 = @{
    tenNguoiNhan = "Nguyen Van A"
    email = "0901234567"
    diaChiCuThe = "123 Duong Le Loi"
    phuongXa = "Phuong Ben Nghe"
    quanHuyen = "Quan 1"
    thanhPho = "TP. Ho Chi Minh"
    diaChiMacDinh = 1
    maKhachHang = "KH_001"
} | ConvertTo-Json -Depth 10

try {
    $response1 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $address1 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response1._id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Start-Sleep -Seconds 1

# Địa chỉ 2: Địa chỉ công ty
Write-Host "Đang tạo địa chỉ 2 (Công ty)..." -ForegroundColor Yellow
$address2 = @{
    tenNguoiNhan = "Nguyen Van A"
    email = "0901234567"
    diaChiCuThe = "456 Duong Nguyen Hue"
    phuongXa = "Phuong Ben Thanh"
    quanHuyen = "Quan 1"
    thanhPho = "TP. Ho Chi Minh"
    diaChiMacDinh = 0
    maKhachHang = "KH_001"
} | ConvertTo-Json -Depth 10

try {
    $response2 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $address2 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response2._id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Start-Sleep -Seconds 1

# Địa chỉ 3: Địa chỉ nhà riêng
Write-Host "Đang tạo địa chỉ 3 (Nhà riêng)..." -ForegroundColor Yellow
$address3 = @{
    tenNguoiNhan = "Nguyen Van A"
    email = "0901234567"
    diaChiCuThe = "789 Duong Tran Hung Dao"
    phuongXa = "Phuong Co Giang"
    quanHuyen = "Quan 1"
    thanhPho = "TP. Ho Chi Minh"
    diaChiMacDinh = 0
    maKhachHang = "KH_001"
} | ConvertTo-Json -Depth 10

try {
    $response3 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $address3 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response3._id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "HOÀN THÀNH TẠO DỮ LIỆU MẪU" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Bạn có thể kiểm tra bằng cách:" -ForegroundColor White
Write-Host "1. Chạy app và xem danh sách địa chỉ" -ForegroundColor White
Write-Host "2. Gọi API GET: $baseUrl/khach-hang/KH_001" -ForegroundColor White
Write-Host ""
