package com.example.myapplication2;

import java.io.Serializable; // To pass it via Intent

public class CartItem implements Serializable { // Implement Serializable if passing via Intent
    private String drinkName;
    private int drinkImageResId;
    private int quantity;
    private boolean isDoubleShot;
    private String selectedTemperature;
    private String selectedSize;
    private String selectedIce; // Assuming you might add ice options later
    private double unitPrice; // Price for one unit of this specific configuration
    private double totalPrice; // unitPrice * quantity

    // Constructor
    public CartItem(String drinkName, int drinkImageResId, int quantity,
                    boolean isDoubleShot, String selectedTemperature, String selectedSize,
                    String selectedIce, double unitPrice) {
        this.drinkName = drinkName;
        this.drinkImageResId = drinkImageResId;
        this.quantity = quantity;
        this.isDoubleShot = isDoubleShot;
        this.selectedTemperature = selectedTemperature;
        this.selectedSize = selectedSize;
        this.selectedIce = selectedIce; // Initialize ice
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }

    // Getters for display)
    public String getDrinkName() { return drinkName; }
    public int getDrinkImageResId() { return drinkImageResId; }
    public int getQuantity() { return quantity; }
    public boolean isDoubleShot() { return isDoubleShot; }
    public String getSelectedTemperature() { return selectedTemperature; }
    public String getSelectedSize() { return selectedSize; }
    public String getSelectedIce() { return selectedIce; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.unitPrice * quantity;
    }

}