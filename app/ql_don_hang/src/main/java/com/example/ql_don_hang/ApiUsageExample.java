package com.example.ql_don_hang;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

/**
 * VÍ DỤ SỬ DỤNG CÁC API METHODS
 * 
 * File này chứa các ví dụ về cách sử dụng tất cả các API methods
 * cho quản lý đơn hàng và chi tiết đơn hàng
 */
public class ApiUsageExample {

    private OrderRepository repository;
    private AppCompatActivity activity;

    public ApiUsageExample(AppCompatActivity activity) {
        this.activity = activity;
        this.repository = new OrderRepository();
    }

    // ==================== VÍ DỤ ĐƠN HÀNG ====================

    /**
     * VÍ DỤ 1: Lấy danh sách tất cả đơn hàng (GET)
     */
    public void exampleGetAllOrders() {
        repository.getAllOrders(new OrderRepository.OrderCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> orders) {
                // Xử lý khi lấy danh sách thành công
                Toast.makeText(activity, "Lấy được " + orders.size() + " đơn hàng", Toast.LENGTH_SHORT).show();
                
                // Hiển thị danh sách đơn hàng
                for (Order order : orders) {
                    System.out.println("Đơn hàng: " + order.getId() + " - " + order.getCustomerName());
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Xử lý khi có lỗi
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 2: Lấy thông tin chi tiết một đơn hàng (GET by ID)
     */
    public void exampleGetOrderById(String orderId) {
        repository.getOrderById(orderId, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                // Xử lý khi lấy thông tin thành công
                Toast.makeText(activity, "Đơn hàng: " + order.getCustomerName(), Toast.LENGTH_SHORT).show();
                
                // Hiển thị thông tin chi tiết
                System.out.println("ID: " + order.getId());
                System.out.println("Khách hàng: " + order.getCustomerName());
                System.out.println("Tổng tiền: " + order.getTotalAmount());
                System.out.println("Trạng thái: " + order.getStatus());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 3: Tạo đơn hàng mới (POST)
     */
    public void exampleCreateOrder() {
        // Tạo đơn hàng mới
        Order newOrder = new Order(
            null, // ID sẽ được tạo tự động bởi server
            "2024-01-15",
            "Nguyễn Văn A",
            500000,
            "Placed"
        );
        
        // Thêm thông tin bổ sung
        newOrder.setAddress("123 Đường ABC, Quận 1, TP.HCM");
        newOrder.setPhone("0901234567");

        repository.createOrder(newOrder, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                // Xử lý khi tạo đơn hàng thành công
                Toast.makeText(activity, "Tạo đơn hàng thành công! ID: " + createdOrder.getId(), 
                              Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, "Lỗi tạo đơn hàng: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 4: Cập nhật đơn hàng (PUT)
     */
    public void exampleUpdateOrder(String orderId) {
        // Lấy thông tin đơn hàng hiện tại trước
        repository.getOrderById(orderId, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                // Cập nhật trạng thái đơn hàng
                order.setStatus("Confirmed");
                
                // Gửi request cập nhật
                repository.updateOrder(orderId, order, new OrderRepository.OrderCallback<Order>() {
                    @Override
                    public void onSuccess(Order updatedOrder) {
                        Toast.makeText(activity, "Cập nhật đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 5: Hủy đơn hàng (PUT với trạng thái Cancelled)
     */
    public void exampleCancelOrder(String orderId, String cancelReason) {
        repository.getOrderById(orderId, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                // Cập nhật trạng thái hủy
                order.setStatus("Cancelled");
                order.setCancelDate("2024-01-16");
                order.setCancelReason(cancelReason);
                
                repository.updateOrder(orderId, order, new OrderRepository.OrderCallback<Order>() {
                    @Override
                    public void onSuccess(Order updatedOrder) {
                        Toast.makeText(activity, "Hủy đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 6: Xóa đơn hàng (DELETE)
     */
    public void exampleDeleteOrder(String orderId) {
        repository.deleteOrder(orderId, new OrderRepository.OrderCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Toast.makeText(activity, "Xóa đơn hàng thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, "Lỗi xóa đơn hàng: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ==================== VÍ DỤ CHI TIẾT ĐƠN HÀNG ====================

    /**
     * VÍ DỤ 7: Lấy tất cả chi tiết đơn hàng (GET)
     */
    public void exampleGetAllOrderDetails() {
        repository.getAllOrderDetails(new OrderRepository.OrderCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                Toast.makeText(activity, "Lấy được " + products.size() + " sản phẩm", Toast.LENGTH_SHORT).show();
                
                for (Product product : products) {
                    System.out.println("Sản phẩm: " + product.getName() + " - SL: " + product.getQuantity());
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 8: Lấy chi tiết đơn hàng theo ID đơn hàng (GET by Order ID)
     */
    public void exampleGetOrderDetailsByOrderId(String orderId) {
        repository.getOrderDetails(orderId, new OrderRepository.OrderCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                Toast.makeText(activity, "Đơn hàng có " + products.size() + " sản phẩm", 
                              Toast.LENGTH_SHORT).show();
                
                // Hiển thị danh sách sản phẩm
                for (Product product : products) {
                    System.out.println("- " + product.getName() + 
                                     " (" + product.getModel() + ")" +
                                     " x" + product.getQuantity() +
                                     " = " + product.getPrice() + "đ");
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 9: Thêm sản phẩm vào đơn hàng (POST)
     */
    public void exampleAddProductToOrder(String orderId) {
        // Tạo request thêm sản phẩm
        OrderDetailRequest request = new OrderDetailRequest(
            orderId,
            "PROD001",
            "iPhone 15 Pro Max",
            "256GB - Titan Tự Nhiên",
            2,
            29990000
        );

        repository.addOrderDetail(request, new OrderRepository.OrderCallback<Product>() {
            @Override
            public void onSuccess(Product product) {
                Toast.makeText(activity, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, "Lỗi thêm sản phẩm: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 10: Cập nhật chi tiết đơn hàng (PUT)
     */
    public void exampleUpdateOrderDetail(String detailId) {
        // Tạo sản phẩm với thông tin mới
        Product updatedProduct = new Product(
            "iPhone 15 Pro Max",
            "256GB - Titan Tự Nhiên",
            3, // Cập nhật số lượng từ 2 lên 3
            29990000
        );

        repository.updateOrderDetail(detailId, updatedProduct, new OrderRepository.OrderCallback<Product>() {
            @Override
            public void onSuccess(Product product) {
                Toast.makeText(activity, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * VÍ DỤ 11: Xóa sản phẩm khỏi đơn hàng (DELETE)
     */
    public void exampleDeleteOrderDetail(String detailId) {
        repository.deleteOrderDetail(detailId, new OrderRepository.OrderCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Toast.makeText(activity, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, "Lỗi xóa sản phẩm: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ==================== VÍ DỤ TỔNG HỢP ====================

    /**
     * VÍ DỤ 12: Tạo đơn hàng hoàn chỉnh với sản phẩm
     */
    public void exampleCreateCompleteOrder() {
        // Bước 1: Tạo đơn hàng
        Order newOrder = new Order(
            null,
            "2024-01-15",
            "Trần Thị B",
            59980000,
            "Placed"
        );
        newOrder.setAddress("456 Đường XYZ, Quận 3, TP.HCM");
        newOrder.setPhone("0912345678");

        repository.createOrder(newOrder, new OrderRepository.OrderCallback<Order>() {
            @Override
            public void onSuccess(Order createdOrder) {
                String orderId = createdOrder.getId();
                
                // Bước 2: Thêm sản phẩm vào đơn hàng
                OrderDetailRequest product1 = new OrderDetailRequest(
                    orderId, "PROD001", "iPhone 15 Pro Max", 
                    "256GB - Titan Tự Nhiên", 2, 29990000
                );
                
                repository.addOrderDetail(product1, new OrderRepository.OrderCallback<Product>() {
                    @Override
                    public void onSuccess(Product product) {
                        Toast.makeText(activity, "Tạo đơn hàng hoàn chỉnh thành công!", 
                                      Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
