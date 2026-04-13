package com.example.ql_don_hang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String date;
    private String customerName;
    private long totalAmount;
    private String status; // "Placed", "Confirmed", "Shipping", "Delivered", "Cancelled"
    private String cancelDate;
    private String cancelReason;
    private String address;
    private String phone;
    private List<Product> products;

    public Order(String id, String date, String customerName, long totalAmount, String status) {
        this.id = id;
        this.date = date;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = status;
        this.products = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getDate() { return date; }
    public String getCustomerName() { return customerName; }
    public long getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCancelDate() { return cancelDate; }
    public void setCancelDate(String cancelDate) { this.cancelDate = cancelDate; }
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
