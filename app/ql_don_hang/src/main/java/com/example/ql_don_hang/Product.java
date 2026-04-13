package com.example.ql_don_hang;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String model;
    private int quantity;
    private long price;

    public Product(String name, String model, int quantity, long price) {
        this.name = name;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() { return name; }
    public String getModel() { return model; }
    public int getQuantity() { return quantity; }
    public long getPrice() { return price; }
}
