package com.example.myapplication2;

public class RewardHistoryItem {
    private String title;
    private String date;
    private String status;
    private int iconResId; // Resource ID of icon

    public RewardHistoryItem(String title, String date, String status, int iconResId) {
        this.title = title;
        this.date = date;
        this.status = status;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getIconResId() {
        return iconResId;
    }
}