package com.example.myapplication2;

import android.content.Context;
import android.util.Log;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserManager {

    private static final String PREF_NAME = "UserProfilePrefs";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_POINTS = "points";
    private static final String KEY_REWARDS_HISTORY = "rewards_history";
    private static final String KEY_CART_ITEMS = "cart_items";
    private static final String KEY_STAMPS = "user_stamps";
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
        return sharedPrefs.getInt(KEY_POINTS, 1490);
    }

    public void addPoints(int pointsToAdd) {
        int currentPoints = getPoints();
        sharedPrefs.edit().putInt(KEY_POINTS, currentPoints + pointsToAdd).apply();
        Log.d("UserManager", "Added " + pointsToAdd + " points.");
    }
    public void subtractPoints(int pointsToSubtract) {
        int currentPoints = getPoints();
        sharedPrefs.edit().putInt(KEY_POINTS, currentPoints - pointsToSubtract).apply();
    }

    public int getStamps() {
        return sharedPrefs.getInt(KEY_STAMPS, 0); // Default to 0 stamps
    }

    public void addStamp() {
        int currentStamps = getStamps();

        if (currentStamps < 8)
            currentStamps++;
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(KEY_STAMPS, currentStamps);
        editor.apply();
    }



    public void redeemStampsReward() {
        // Assuming a full card (8 stamps) is needed to redeem
        if (getStamps() >= 8) {

            sharedPrefs.edit().putInt(KEY_STAMPS, 0).apply();
            Log.d("UserManager", "Stamps reward redeemed. Stamps reset to 0.");
        }
    }


    public List<RewardHistoryItem> getRewardHistory() {
        String jsonHistory = sharedPrefs.getString(KEY_REWARDS_HISTORY, null);
        if (jsonHistory == null) {
            return new ArrayList<>(); // Return empty list if no history
        }
        Type type = new TypeToken<ArrayList<RewardHistoryItem>>() {}.getType();
        List<RewardHistoryItem> historyItems = gson.fromJson(jsonHistory, type);
        return historyItems == null ? new ArrayList<>() : historyItems; // Ensure non-null return
    }

    public void addRewardHistoryItem(RewardHistoryItem newItem) {
        if (newItem == null) return;
        List<RewardHistoryItem> historyItems = getRewardHistory();
        historyItems.add(0, newItem); // Add to the beginning to show newest first
        String jsonHistory = gson.toJson(historyItems);
        sharedPrefs.edit().putString(KEY_REWARDS_HISTORY, jsonHistory).apply();
    }

    public void addRewardHistoryItems(List<RewardHistoryItem> newItems) {
        if (newItems == null || newItems.isEmpty()) {
            return;
        }
        List<RewardHistoryItem> historyItems = getRewardHistory();
        historyItems.addAll(0, newItems); // Add all new items to the beginning
        String jsonHistory = gson.toJson(historyItems);
        sharedPrefs.edit().putString(KEY_REWARDS_HISTORY, jsonHistory).apply();
    }
/*
    public void addRewardHistoryItem(String itemJson) {
        String currentHistory = getRewardsHistory();
        if (currentHistory.equals("[]")) {
            sharedPrefs.edit().putString(KEY_REWARDS_HISTORY, "[" + itemJson + "]").apply();
        } else {
            // Loại bỏ dấu "]" cuối cùng, thêm phẩy và item mới, rồi thêm "]"
            String newHistory = currentHistory.substring(0, currentHistory.length() - 1) + "," + itemJson + "]";
            sharedPrefs.edit().putString(KEY_REWARDS_HISTORY, newHistory).apply();
        }
    }*/

    public static String getCurrentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
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