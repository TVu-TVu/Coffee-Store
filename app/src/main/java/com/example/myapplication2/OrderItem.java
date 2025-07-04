// OrderItem.java
package com.example.myapplication2; // Ensure this matches your package name

public class OrderItem {
    private String orderName;
    private String orderId;
    private String orderTime;
    private String orderQuantity;
    private String totalAmount;
    private String orderStatus;
    private int statusIconResId;
    private boolean isOngoing;

    public OrderItem(String orderName, String orderId, String orderTime, String orderQuantity, String totalAmount, String orderStatus, int statusIconResId, boolean isOngoing) {
        this.orderName = orderName;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderQuantity = orderQuantity;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.statusIconResId = statusIconResId;
        this.isOngoing = isOngoing;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getStatusIconResId() {
        return statusIconResId;
    }

    public boolean isOngoing() {
        return isOngoing;
    }
}