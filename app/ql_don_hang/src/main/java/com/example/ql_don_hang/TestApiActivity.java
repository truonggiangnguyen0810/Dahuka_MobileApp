package com.example.ql_don_hang;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activity để test tất cả các API operations với dữ liệu ảo
 */
public class TestApiActivity extends AppCompatActivity {
    private static final String TAG = "TestApiActivity";
    
    private OrderRepository repository;
    private TextView tvLog;
    private ScrollView scrollView;
    private StringBuilder logBuilder;
    
    // Lưu ID để test các operations khác
    private String createdOrderId = null;
    private String createdDetailId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        
        repository = new OrderRepository();
        logBuilder = new StringBuilder();
        
        initViews();
        setupButtons();
    }

    private void initViews() {
        tvLog = findViewById(R.id.tvLog);
        scrollView = findViewById(R.id.scrollView);
    }

    private void setupButtons() {
        // Button tạo dữ liệu mẫu
        findViewById(R.id.btnGenerateSample).setOnClickListener(v -> generateSampleData());
        
        // Button test từng operation
        findViewById(R.id.btnTestGetAll).setOnClickListener(v -> testGetAllOrders());
        findViewById(R.id.btnTestPost).setOnClickListener(v -> testCreateOrder());
        findViewById(R.id.btnTestGetById).setOnClickListener(v -> testGetOrderById());
        findViewById(R.id.btnTestPut).setOnClickListener(v -> testUpdateOrder());
        findViewById(R.id.btnTestDelete).setOnClickListener(v -> testDeleteOrder());
        
        findViewById(R.id.btnTestGetDetails).setOnClickListener(v -> testGetOrderDetails());
        findViewById(R.id.btnTestPostDetail).setOnClickListener(v -> testAddOrderDetail());
        findViewById(R.id.btnTestPutDetail).setOnClickListener(v -> testUpdateOrderDetail());
        findViewById(R.id.btnTestDeleteDetail).setOnClickListener(v -> testDeleteOrderDetail());
        
        // Button test tất cả
        findViewById(R.id.btnTestAll).setOnClickListener(v -> testAllOperations());
        findViewById(R.id.btnClearLog).setOnClickListener(v -> clearLog());
    }

    // ==================== TẠO DỮ LIỆU MẪU ====================

    /**
     * Tạo 5 đơn hàng mẫu với dữ liệu ảo
     */
    private void generateSampleData() {
        addLog("=== TẠO DỮ LIỆU MẪU ===");
        addLog("Đang tạo 5 đơn hàng với các trạng thái khác nhau...");
        addLog("");
        
        SampleDataGenerator generator = new SampleDataGenerator(this);
        generator.generateSampleOrders(new SampleDataGenerator.OnGenerateCompleteListener() {
            @Override
            public void onComplete(int successCount, int totalCount) {
                addLog("=== KẾT QUẢ TẠO DỮ LIỆU ===");
                addLog("✅ Thành công: " + successCount + "/" + totalCount + " đơn hàng");
                addLog("");
                
                if (successCount > 0) {
                    addLog("Bạn có thể:");
                    addLog("1. Nhấn 'GET All' để xem danh sách");
                    addLog("2. Nhấn '▶ TEST TẤT CẢ' để test các operations");
                    addLog("");
                }
                
                Toast.makeText(TestApiActivity.this, 
                    "Đã tạo " + successCount + "/" + totalCount + " đơn hàng mẫu!", 
                    Toast.LENGTH_LONG).show();
            }
        });
    }

    // ==================== TEST ĐƠN HÀNG ====================

    /**
     * TEST 1: GET - Lấy tất cả đơn hàng
     */
    private void testGetAllOrders() {
        addLog("=== TEST GET ALL ORDERS ===");
        
        repository.getAllOrders(new OrderRepository.OrderCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> orders) {
                addLog("✅ Lấy danh sách thành công!");
                addLog("Số lượng đơn hàng: " + orders.size());
                
                for (int i = 0; i < Math.min(orders.size(), 3); i++) {
                    Order order = orders.get(i);
                    addLog("  - ID: " + order.getId() + 
                           ", Khách: " + order.getCustomerName() + 
                           ", Tổng: " + formatMoney(order.getTotalAmount()));
                }
                
                if (orders.size() > 0) {
                    createdOrderId = orders.get(0).getId();
                    addLog("Đã lưu ID đơn hàng đầu tiên: " + createdOrderId);
                }
                
                addLog("");
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi: " + errorMessage);
                addLog("");
            }
        });
    }

    /**
     * TEST 2: POST - Tạo đơn hàng mới với dữ liệu ảo
     */
    private void testCreateOrder() {
        addLog("=== TEST POST - TẠO ĐƠN HÀNG MỚI ===");
        
        // Tạo dữ liệu ảo
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        
        Order newOrder = new Order(
            null, // ID sẽ được tạo bởi server
            currentDate,
            "Nguyễn Văn Test",
            59980000L,
            "Placed"
        );
        
        newOrder.setAddress("123 Đường Test, Quận 1, TP.HCM");
        newOrder.setPhone("0901234567");
        
        // Thêm sản phẩm vào đơn hàng
        List<Product> products = new ArrayList<>();
        products.add(new Product("iPhone 15 Pro Max", "256GB - Titan Tự Nhiên", 2, 29990000L));
        newOrder.setProducts(products);
        
        addLog("Dữ liệu đơn hàng:");
        addLog("  - Khách hàng: " + newOrder.getCustomerName());
        addLog("  - Địa chỉ: " + newOrder.getAddress());
        addLog("  - SĐT: " + newOrder.getPhone());
        addLog("  - Tổng tiền: " + formatMoney(newOrder.getTotalAmount()));
        addLog("  - Trạng thái: " + newOrder.getStatus());
        addLog("  - Số sản phẩm: " + products.size());
        
        repository.createOrder(newOrder, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                createdOrderId = createdOrder.getId();
                addLog("✅ Tạo đơn hàng thành công!");
                addLog("ID đơn hàng mới: " + createdOrderId);
                addLog("");
                
                Toast.makeText(TestApiActivity.this, 
                    "Tạo đơn hàng thành công! ID: " + createdOrderId, 
                    Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi tạo đơn hàng: " + errorMessage);
                addLog("");
            }
        });
    }

    /**
     * TEST 3: GET by ID - Lấy thông tin đơn hàng theo ID
     */
    private void testGetOrderById() {
        if (createdOrderId == null) {
            addLog("⚠️ Chưa có ID đơn hàng. Vui lòng tạo đơn hàng hoặc lấy danh sách trước!");
            addLog("");
            return;
        }
        
        addLog("=== TEST GET ORDER BY ID ===");
        addLog("Đang lấy đơn hàng ID: " + createdOrderId);
        
        repository.getOrderById(createdOrderId, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                addLog("✅ Lấy thông tin thành công!");
                addLog("Chi tiết đơn hàng:");
                addLog("  - ID: " + order.getId());
                addLog("  - Khách hàng: " + order.getCustomerName());
                addLog("  - Ngày đặt: " + order.getDate());
                addLog("  - Địa chỉ: " + order.getAddress());
                addLog("  - SĐT: " + order.getPhone());
                addLog("  - Tổng tiền: " + formatMoney(order.getTotalAmount()));
                addLog("  - Trạng thái: " + order.getStatus());
                
                if (order.getProducts() != null && !order.getProducts().isEmpty()) {
                    addLog("  - Sản phẩm:");
                    for (Product p : order.getProducts()) {
                        addLog("    + " + p.getName() + " (" + p.getModel() + ") x" + p.getQuantity());
                    }
                }
                addLog("");
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi: " + errorMessage);
                addLog("");
            }
        });
    }

    /**
     * TEST 4: PUT - Cập nhật đơn hàng
     */
    private void testUpdateOrder() {
        if (createdOrderId == null) {
            addLog("⚠️ Chưa có ID đơn hàng. Vui lòng tạo đơn hàng hoặc lấy danh sách trước!");
            addLog("");
            return;
        }
        
        addLog("=== TEST PUT - CẬP NHẬT ĐƠN HÀNG ===");
        addLog("Đang cập nhật đơn hàng ID: " + createdOrderId);
        
        // Lấy thông tin đơn hàng hiện tại
        repository.getOrderById(createdOrderId, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                // Cập nhật trạng thái
                String oldStatus = order.getStatus();
                order.setStatus("Confirmed");
                
                addLog("Thay đổi trạng thái: " + oldStatus + " → Confirmed");
                
                repository.updateOrder(createdOrderId, order, new OrderRepository.OrderCallback<Order>() {
                    @Override
                    public void onSuccess(Order updatedOrder) {
                        addLog("✅ Cập nhật thành công!");
                        addLog("Trạng thái mới: " + updatedOrder.getStatus());
                        addLog("");
                    }

                    @Override
                    public void onError(String errorMessage) {
                        addLog("❌ Lỗi cập nhật: " + errorMessage);
                        addLog("");
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi lấy thông tin: " + errorMessage);
                addLog("");
            }
        });
    }

    /**
     * TEST 5: DELETE - Xóa đơn hàng
     */
    private void testDeleteOrder() {
        if (createdOrderId == null) {
            addLog("⚠️ Chưa có ID đơn hàng. Vui lòng tạo đơn hàng hoặc lấy danh sách trước!");
            addLog("");
            return;
        }
        
        addLog("=== TEST DELETE - XÓA ĐƠN HÀNG ===");
        addLog("Đang xóa đơn hàng ID: " + createdOrderId);
        
        String orderIdToDelete = createdOrderId;
        
        repository.deleteOrder(orderIdToDelete, new OrderRepository.OrderCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                addLog("✅ Xóa đơn hàng thành công!");
                addLog("Đã xóa đơn hàng ID: " + orderIdToDelete);
                createdOrderId = null;
                addLog("");
                
                Toast.makeText(TestApiActivity.this, "Xóa đơn hàng thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi xóa: " + errorMessage);
                addLog("");
            }
        });
    }

    // ==================== TEST CHI TIẾT ĐƠN HÀNG ====================

    /**
     * TEST 6: GET - Lấy chi tiết đơn hàng
     */
    private void testGetOrderDetails() {
        if (createdOrderId == null) {
            addLog("⚠️ Chưa có ID đơn hàng. Vui lòng tạo đơn hàng trước!");
            addLog("");
            return;
        }
        
        addLog("=== TEST GET ORDER DETAILS ===");
        addLog("Đang lấy chi tiết đơn hàng ID: " + createdOrderId);
        
        repository.getOrderDetails(createdOrderId, new OrderRepository.OrderCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                addLog("✅ Lấy chi tiết thành công!");
                addLog("Số sản phẩm: " + products.size());
                
                for (Product product : products) {
                    addLog("  - " + product.getName());
                    addLog("    Model: " + product.getModel());
                    addLog("    SL: " + product.getQuantity() + " x " + formatMoney(product.getPrice()));
                }
                addLog("");
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi: " + errorMessage);
                addLog("");
            }
        });
    }

    /**
     * TEST 7: POST - Thêm sản phẩm vào đơn hàng
     */
    private void testAddOrderDetail() {
        if (createdOrderId == null) {
            addLog("⚠️ Chưa có ID đơn hàng. Vui lòng tạo đơn hàng trước!");
            addLog("");
            return;
        }
        
        addLog("=== TEST POST - THÊM SẢN PHẨM ===");
        addLog("Đang thêm sản phẩm vào đơn hàng ID: " + createdOrderId);
        
        OrderDetailRequest request = new OrderDetailRequest(
            createdOrderId,
            "PROD002",
            "Samsung Galaxy S24 Ultra",
            "512GB - Đen",
            1,
            31990000L
        );
        
        addLog("Thông tin sản phẩm:");
        addLog("  - Tên: " + request.getProductName());
        addLog("  - Model: " + request.getModel());
        addLog("  - SL: " + request.getQuantity());
        addLog("  - Giá: " + formatMoney(request.getPrice()));
        
        repository.addOrderDetail(request, new OrderRepository.OrderCallback<Product>() {
            @Override
            public void onSuccess(Product product) {
                addLog("✅ Thêm sản phẩm thành công!");
                addLog("");
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi: " + errorMessage);
                addLog("");
            }
        });
    }

    /**
     * TEST 8: PUT - Cập nhật chi tiết đơn hàng
     */
    private void testUpdateOrderDetail() {
        addLog("=== TEST PUT - CẬP NHẬT CHI TIẾT ===");
        addLog("⚠️ Cần có ID chi tiết đơn hàng từ server");
        addLog("Ví dụ code:");
        addLog("  Product updated = new Product(\"iPhone 15\", \"256GB\", 3, 29990000);");
        addLog("  repository.updateOrderDetail(detailId, updated, callback);");
        addLog("");
    }

    /**
     * TEST 9: DELETE - Xóa sản phẩm khỏi đơn hàng
     */
    private void testDeleteOrderDetail() {
        addLog("=== TEST DELETE - XÓA SẢN PHẨM ===");
        addLog("⚠️ Cần có ID chi tiết đơn hàng từ server");
        addLog("Ví dụ code:");
        addLog("  repository.deleteOrderDetail(detailId, callback);");
        addLog("");
    }

    // ==================== TEST TẤT CẢ ====================

    /**
     * Test tất cả các operations theo thứ tự
     */
    private void testAllOperations() {
        addLog("========================================");
        addLog("BẮT ĐẦU TEST TẤT CẢ CÁC OPERATIONS");
        addLog("========================================");
        addLog("");
        
        // Test theo thứ tự: POST → GET → PUT → GET Details → POST Detail → DELETE
        testCreateOrderForFullTest();
    }

    private void testCreateOrderForFullTest() {
        addLog("BƯỚC 1: Tạo đơn hàng mới");
        
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Order newOrder = new Order(null, currentDate, "Test User Full", 29990000L, "Placed");
        newOrder.setAddress("456 Test Street");
        newOrder.setPhone("0987654321");
        
        repository.createOrder(newOrder, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                createdOrderId = order.getId();
                addLog("✅ Tạo thành công! ID: " + createdOrderId);
                addLog("");
                
                // Tiếp tục test GET
                new android.os.Handler().postDelayed(() -> testGetByIdForFullTest(), 1000);
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi: " + errorMessage);
                addLog("");
            }
        });
    }

    private void testGetByIdForFullTest() {
        addLog("BƯỚC 2: Lấy thông tin đơn hàng vừa tạo");
        
        repository.getOrderById(createdOrderId, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                addLog("✅ Lấy thành công! Khách: " + order.getCustomerName());
                addLog("");
                
                // Tiếp tục test UPDATE
                new android.os.Handler().postDelayed(() -> testUpdateForFullTest(), 1000);
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi: " + errorMessage);
                addLog("");
            }
        });
    }

    private void testUpdateForFullTest() {
        addLog("BƯỚC 3: Cập nhật trạng thái đơn hàng");
        
        repository.getOrderById(createdOrderId, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                order.setStatus("Confirmed");
                
                repository.updateOrder(createdOrderId, order, new OrderRepository.OrderCallback<Order>() {
                    @Override
                    public void onSuccess(Order updatedOrder) {
                        addLog("✅ Cập nhật thành công! Trạng thái: " + updatedOrder.getStatus());
                        addLog("");
                        
                        addLog("========================================");
                        addLog("HOÀN THÀNH TEST TẤT CẢ CÁC OPERATIONS");
                        addLog("========================================");
                        addLog("");
                        
                        Toast.makeText(TestApiActivity.this, 
                            "Hoàn thành test tất cả operations!", 
                            Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        addLog("❌ Lỗi: " + errorMessage);
                        addLog("");
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                addLog("❌ Lỗi: " + errorMessage);
                addLog("");
            }
        });
    }

    // ==================== HELPER METHODS ====================

    private void addLog(String message) {
        Log.d(TAG, message);
        logBuilder.append(message).append("\n");
        runOnUiThread(() -> {
            tvLog.setText(logBuilder.toString());
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    private void clearLog() {
        logBuilder = new StringBuilder();
        tvLog.setText("");
        addLog("Log đã được xóa.");
        addLog("");
    }

    private String formatMoney(long amount) {
        return String.format(Locale.getDefault(), "%,d đ", amount);
    }
}
