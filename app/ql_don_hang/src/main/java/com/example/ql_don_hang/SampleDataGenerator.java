package com.example.ql_don_hang;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Class tạo dữ liệu ảo để test API
 */
public class SampleDataGenerator {
    private static final String TAG = "SampleDataGenerator";
    private OrderRepository repository;
    private Context context;
    private int successCount = 0;
    private int totalOrders = 5;

    public SampleDataGenerator(Context context) {
        this.context = context;
        this.repository = new OrderRepository();
    }

    /**
     * Tạo 5 đơn hàng mẫu với các trạng thái khác nhau
     */
    public void generateSampleOrders(final OnGenerateCompleteListener listener) {
        successCount = 0;
        
        Log.d(TAG, "Bắt đầu tạo " + totalOrders + " đơn hàng mẫu...");
        
        // Đơn hàng 1: Placed
        createOrder1();
        
        // Đơn hàng 2: Confirmed
        new android.os.Handler().postDelayed(this::createOrder2, 500);
        
        // Đơn hàng 3: Shipping
        new android.os.Handler().postDelayed(this::createOrder3, 1000);
        
        // Đơn hàng 4: Delivered
        new android.os.Handler().postDelayed(this::createOrder4, 1500);
        
        // Đơn hàng 5: Cancelled
        new android.os.Handler().postDelayed(() -> {
            createOrder5();
            
            // Kiểm tra kết quả sau 2 giây
            new android.os.Handler().postDelayed(() -> {
                if (listener != null) {
                    listener.onComplete(successCount, totalOrders);
                }
            }, 2000);
        }, 2000);
    }

    /**
     * Đơn hàng 1: iPhone 15 Pro Max - Trạng thái Placed
     */
    private void createOrder1() {
        String date = getDateDaysAgo(5);
        
        Order order = new Order(
            null,
            date,
            "Nguyễn Văn A",
            59980000L,
            "Placed"
        );
        order.setAddress("123 Đường Lê Lợi, Quận 1, TP.HCM");
        order.setPhone("0901234567");
        
        List<Product> products = new ArrayList<>();
        products.add(new Product("iPhone 15 Pro Max", "256GB - Titan Tự Nhiên", 2, 29990000L));
        order.setProducts(products);
        
        repository.createOrder(order, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                successCount++;
                Log.d(TAG, "✅ Tạo đơn hàng 1 thành công: " + createdOrder.getId());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "❌ Lỗi tạo đơn hàng 1: " + errorMessage);
            }
        });
    }

    /**
     * Đơn hàng 2: Samsung Galaxy S24 Ultra - Trạng thái Confirmed
     */
    private void createOrder2() {
        String date = getDateDaysAgo(4);
        
        Order order = new Order(
            null,
            date,
            "Trần Thị B",
            31990000L,
            "Confirmed"
        );
        order.setAddress("456 Đường Nguyễn Huệ, Quận 3, TP.HCM");
        order.setPhone("0912345678");
        
        List<Product> products = new ArrayList<>();
        products.add(new Product("Samsung Galaxy S24 Ultra", "512GB - Đen", 1, 31990000L));
        order.setProducts(products);
        
        repository.createOrder(order, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                successCount++;
                Log.d(TAG, "✅ Tạo đơn hàng 2 thành công: " + createdOrder.getId());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "❌ Lỗi tạo đơn hàng 2: " + errorMessage);
            }
        });
    }

    /**
     * Đơn hàng 3: MacBook Pro M3 - Trạng thái Shipping
     */
    private void createOrder3() {
        String date = getDateDaysAgo(3);
        
        Order order = new Order(
            null,
            date,
            "Lê Văn C",
            45000000L,
            "Shipping"
        );
        order.setAddress("789 Đường Trần Hưng Đạo, Quận 5, TP.HCM");
        order.setPhone("0923456789");
        
        List<Product> products = new ArrayList<>();
        products.add(new Product("MacBook Pro M3", "14 inch - 16GB RAM - 512GB SSD", 1, 45000000L));
        order.setProducts(products);
        
        repository.createOrder(order, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                successCount++;
                Log.d(TAG, "✅ Tạo đơn hàng 3 thành công: " + createdOrder.getId());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "❌ Lỗi tạo đơn hàng 3: " + errorMessage);
            }
        });
    }

    /**
     * Đơn hàng 4: iPad Pro - Trạng thái Delivered
     */
    private void createOrder4() {
        String date = getDateDaysAgo(10);
        
        Order order = new Order(
            null,
            date,
            "Phạm Thị D",
            25000000L,
            "Delivered"
        );
        order.setAddress("321 Đường Võ Văn Tần, Quận 10, TP.HCM");
        order.setPhone("0934567890");
        
        List<Product> products = new ArrayList<>();
        products.add(new Product("iPad Pro 12.9", "256GB - WiFi + Cellular", 1, 25000000L));
        order.setProducts(products);
        
        repository.createOrder(order, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                successCount++;
                Log.d(TAG, "✅ Tạo đơn hàng 4 thành công: " + createdOrder.getId());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "❌ Lỗi tạo đơn hàng 4: " + errorMessage);
            }
        });
    }

    /**
     * Đơn hàng 5: Apple Watch Ultra 2 - Trạng thái Cancelled
     */
    private void createOrder5() {
        String date = getDateDaysAgo(8);
        String cancelDate = getDateDaysAgo(7);
        
        Order order = new Order(
            null,
            date,
            "Hoàng Văn E",
            18000000L,
            "Cancelled"
        );
        order.setAddress("555 Đường Cách Mạng Tháng 8, Quận Tân Bình, TP.HCM");
        order.setPhone("0945678901");
        order.setCancelDate(cancelDate);
        order.setCancelReason("Khách hàng đổi ý, không muốn mua nữa");
        
        List<Product> products = new ArrayList<>();
        products.add(new Product("Apple Watch Ultra 2", "Titanium - GPS + Cellular", 2, 9000000L));
        order.setProducts(products);
        
        repository.createOrder(order, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                successCount++;
                Log.d(TAG, "✅ Tạo đơn hàng 5 thành công: " + createdOrder.getId());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "❌ Lỗi tạo đơn hàng 5: " + errorMessage);
            }
        });
    }

    /**
     * Lấy ngày cách đây X ngày
     */
    private String getDateDaysAgo(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    /**
     * Interface callback khi hoàn thành tạo dữ liệu
     */
    public interface OnGenerateCompleteListener {
        void onComplete(int successCount, int totalCount);
    }

    /**
     * Tạo dữ liệu mẫu đơn giản (chỉ 1 đơn hàng để test nhanh)
     */
    public void generateQuickSample(final OrderRepository.OrderCallback<Order> callback) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        
        Order order = new Order(
            null,
            date,
            "Test User",
            29990000L,
            "Placed"
        );
        order.setAddress("Test Address");
        order.setPhone("0900000000");
        
        List<Product> products = new ArrayList<>();
        products.add(new Product("Test Product", "Test Model", 1, 29990000L));
        order.setProducts(products);
        
        repository.createOrder(order, callback);
    }
}
