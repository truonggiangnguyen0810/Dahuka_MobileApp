# Script tạo dữ liệu mẫu cho API Đơn Hàng
# Chạy script này để POST 5 đơn hàng mẫu lên server

$baseUrl = "https://boney-unspoiled-thesis.ngrok-free.dev/api/don-hang"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TẠO DỮ LIỆU MẪU CHO ĐƠN HÀNG" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Đơn hàng 1: iPhone 15 Pro Max - Placed
Write-Host "Đang tạo đơn hàng 1: iPhone 15 Pro Max..." -ForegroundColor Yellow
$order1 = @{
    date = "2024-01-15"
    customerName = "Nguyễn Văn A"
    totalAmount = 59980000
    status = "Placed"
    address = "123 Đường Lê Lợi, Quận 1, TP.HCM"
    phone = "0901234567"
    products = @(
        @{
            name = "iPhone 15 Pro Max"
            model = "256GB - Titan Tự Nhiên"
            quantity = 2
            price = 29990000
        }
    )
} | ConvertTo-Json -Depth 10

try {
    $response1 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $order1 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response1.id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Start-Sleep -Seconds 1

# Đơn hàng 2: Samsung Galaxy S24 Ultra - Confirmed
Write-Host "Đang tạo đơn hàng 2: Samsung Galaxy S24 Ultra..." -ForegroundColor Yellow
$order2 = @{
    date = "2024-01-16"
    customerName = "Trần Thị B"
    totalAmount = 31990000
    status = "Confirmed"
    address = "456 Đường Nguyễn Huệ, Quận 3, TP.HCM"
    phone = "0912345678"
    products = @(
        @{
            name = "Samsung Galaxy S24 Ultra"
            model = "512GB - Đen"
            quantity = 1
            price = 31990000
        }
    )
} | ConvertTo-Json -Depth 10

try {
    $response2 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $order2 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response2.id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Start-Sleep -Seconds 1

# Đơn hàng 3: MacBook Pro M3 - Shipping
Write-Host "Đang tạo đơn hàng 3: MacBook Pro M3..." -ForegroundColor Yellow
$order3 = @{
    date = "2024-01-17"
    customerName = "Lê Văn C"
    totalAmount = 45000000
    status = "Shipping"
    address = "789 Đường Trần Hưng Đạo, Quận 5, TP.HCM"
    phone = "0923456789"
    products = @(
        @{
            name = "MacBook Pro M3"
            model = "14 inch - 16GB RAM - 512GB SSD"
            quantity = 1
            price = 45000000
        }
    )
} | ConvertTo-Json -Depth 10

try {
    $response3 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $order3 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response3.id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Start-Sleep -Seconds 1

# Đơn hàng 4: iPad Pro - Delivered
Write-Host "Đang tạo đơn hàng 4: iPad Pro..." -ForegroundColor Yellow
$order4 = @{
    date = "2024-01-10"
    customerName = "Phạm Thị D"
    totalAmount = 25000000
    status = "Delivered"
    address = "321 Đường Võ Văn Tần, Quận 10, TP.HCM"
    phone = "0934567890"
    products = @(
        @{
            name = "iPad Pro 12.9"
            model = "256GB - WiFi + Cellular"
            quantity = 1
            price = 25000000
        }
    )
} | ConvertTo-Json -Depth 10

try {
    $response4 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $order4 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response4.id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Start-Sleep -Seconds 1

# Đơn hàng 5: Apple Watch Ultra 2 - Cancelled
Write-Host "Đang tạo đơn hàng 5: Apple Watch Ultra 2..." -ForegroundColor Yellow
$order5 = @{
    date = "2024-01-12"
    customerName = "Hoàng Văn E"
    totalAmount = 18000000
    status = "Cancelled"
    cancelDate = "2024-01-13"
    cancelReason = "Khách hàng đổi ý, không muốn mua nữa"
    address = "555 Đường Cách Mạng Tháng 8, Quận Tân Bình, TP.HCM"
    phone = "0945678901"
    products = @(
        @{
            name = "Apple Watch Ultra 2"
            model = "Titanium - GPS + Cellular"
            quantity = 2
            price = 9000000
        }
    )
} | ConvertTo-Json -Depth 10

try {
    $response5 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $order5 -ContentType "application/json"
    Write-Host "✅ Tạo thành công! ID: $($response5.id)" -ForegroundColor Green
} catch {
    Write-Host "❌ Lỗi: $_" -ForegroundColor Red
}
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "HOÀN THÀNH TẠO DỮ LIỆU MẪU" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Bạn có thể kiểm tra bằng cách:" -ForegroundColor White
Write-Host "1. Chạy app và xem danh sách đơn hàng" -ForegroundColor White
Write-Host "2. Gọi API GET: $baseUrl" -ForegroundColor White
Write-Host ""
