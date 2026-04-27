package com.example.ql_don_hang;

import java.io.Serializable;

/**
 * Request body để thêm sản phẩm vào đơn hàng
 */
public class OrderDetailRequest implements Serializable {
    private String orderId;
    private String productId;
    private String productName;
    private String model;
    private int quantity;
    private long price;

    public OrderDetailRequest() {
    }

    public OrderDetailRequest(String orderId, String productId, String productName, 
                            String model, int quantity, long price) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
