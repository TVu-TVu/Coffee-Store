package com.example.myapplication2;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Order implements Serializable { // Serializable for passing via Intent if needed
    private String orderId;
    private List<CartItem> items;
    private long orderTimestamp;
    private String status; // "Preparing", "On the way", "Delivered", "Cancelled"
    private double totalPrice;
    // Add any other relevant details like user ID, address, etc.

    public Order(List<CartItem> items, double totalPrice) {
        this.orderId = UUID.randomUUID().toString(); // Generate a unique ID
        this.items = items;
        this.totalPrice = totalPrice;
        this.orderTimestamp = System.currentTimeMillis();
        this.status = "Preparing"; // Initial status
    }

    // Getters
    public String getOrderId() { return orderId; }
    public List<CartItem> getItems() { return items; }
    public long getOrderTimestamp() { return orderTimestamp; }
    public String getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

}
