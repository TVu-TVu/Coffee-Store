package com.example.myapplication2;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItemToCart(CartItem item) {
        cartItems.add(item);
    }
    public void removeItemByInstance(CartItem item) {
        boolean removed = cartItems.remove(item);
        if (!removed) {
            for (int i = 0; i < cartItems.size(); i++) {
                CartItem currentItem = cartItems.get(i);
                // Define what makes an item "equal" for removal purposes
                if (currentItem.getName().equals(item.getName()) &&
                        currentItem.getPrice() == item.getPrice() && // Add other relevant properties
                        currentItem.getSize().equals(item.getSize())) { // Example
                    cartItems.remove(i);
                    removed = true;
                    break;
                }
            }
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems); // Return a copy to prevent external modification
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice()*item.getQuantity();
        }
        return total;
    }

}