package com.example.myapplication2;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String PREF_NAME = "UserProfilePrefs";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_POINTS = "points";
    private static final String KEY_REWARDS_HISTORY = "rewards_history";
    private static final String KEY_CART_ITEMS = "cart_items";
    private final SharedPreferences sharedPrefs;
    private final Context context;
    private final Gson gson;

    public UserManager(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public String getFullName() {
        return sharedPrefs.getString(KEY_FULL_NAME, context.getString(R.string.full_name_value));
    }

    public String getPhoneNumber() {
        return sharedPrefs.getString(KEY_PHONE_NUMBER, context.getString(R.string.phone_value));
    }

    public String getEmail() {
        return sharedPrefs.getString(KEY_EMAIL, context.getString(R.string.email_value));
    }

    public String getAddress() {
        return sharedPrefs.getString(KEY_ADDRESS, context.getString(R.string.address_value));
    }

    public void updateFullName(String fullName) {
        sharedPrefs.edit().putString(KEY_FULL_NAME, fullName).apply();
    }

    public void updatePhoneNumber(String phoneNumber) {
        sharedPrefs.edit().putString(KEY_PHONE_NUMBER, phoneNumber).apply();
    }

    public void updateEmail(String email) {
        sharedPrefs.edit().putString(KEY_EMAIL, email).apply();
    }

    public void updateAddress(String address) {
        sharedPrefs.edit().putString(KEY_ADDRESS, address).apply();
    }

    public int getPoints() {
        return sharedPrefs.getInt(KEY_POINTS, 0);
    }

    public void addPoints(int pointsToAdd) {
        int currentPoints = getPoints();
        sharedPrefs.edit().putInt(KEY_POINTS, currentPoints + pointsToAdd).apply();
    }

    public String getRewardsHistory() {
        return sharedPrefs.getString(KEY_REWARDS_HISTORY, "[]");
    }

    public void addRewardHistoryItem(String itemJson) {
        String currentHistory = getRewardsHistory();
        if (currentHistory.equals("[]")) {
            sharedPrefs.edit().putString(KEY_REWARDS_HISTORY, "[" + itemJson + "]").apply();
        } else {
            // Loại bỏ dấu "]" cuối cùng, thêm phẩy và item mới, rồi thêm "]"
            String newHistory = currentHistory.substring(0, currentHistory.length() - 1) + "," + itemJson + "]";
            sharedPrefs.edit().putString(KEY_REWARDS_HISTORY, newHistory).apply();
        }
        //public void subtractPoints(int pointsToSubtract) {
        //  int currentPoints = getPoints();
        //if (currentPoints >= pointsToSubtract) {
        //  sharedPrefs.edit().putInt(KEY_POINTS, currentPoints - pointsToSubtract).apply();
        //}
        //}
    }

    public void addCartItem(CartItem item) {
        List<CartItem> currentItems = getCartItems();
        currentItems.add(item);
        saveCartItems(currentItems);
    }

    public List<CartItem> getCartItems() {
        String json = sharedPrefs.getString(KEY_CART_ITEMS, "[]");
        Type type = new TypeToken<List<CartItem>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void saveCartItems(List<CartItem> items) {
        String json = gson.toJson(items);
        sharedPrefs.edit().putString(KEY_CART_ITEMS, json).apply();
    }

    public void clearCart() {
        sharedPrefs.edit().remove(KEY_CART_ITEMS).apply();
    }

}