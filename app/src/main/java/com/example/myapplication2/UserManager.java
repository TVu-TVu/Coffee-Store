package com.example.myapplication2;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {

    private static final String PREF_NAME = "UserProfilePrefs";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";

    private final SharedPreferences sharedPrefs;
    private final Context context;

    public UserManager(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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
}