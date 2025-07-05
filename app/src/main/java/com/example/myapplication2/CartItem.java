package com.example.myapplication2; // Thay thế bằng package name của bạn

public class CartItem {
    private String name;
    private double price;
    private int quantity;
    private String size;
    private String temperature;
    private String shot; // Single/Double
    private String ice; // None/Less/Normal
    private int imageResId; // Resource ID of the drink image

    public CartItem(String name, double price, int quantity, String size, String temperature, String shot, String ice, int imageResId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.temperature = temperature;
        this.shot = shot;
        this.ice = ice;
        this.imageResId = imageResId;
    }

    // Getters for all fields
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getShot() {
        return shot;
    }

    public String getIce() {
        return ice;
    }

    public int getImageResId() {
        return imageResId;
    }
}