package com.example.myapplication2;

public class RewardHistoryItem {
    private String title;
    private String date;
    private int pointsEarned;
    private int iconResId; // Resource ID of icon

    public RewardHistoryItem(String title, String date, int pointsEarned, int iconResId) {
        this.title = title;
        this.date = date;
        this.pointsEarned = pointsEarned;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public int getIconResId() {
        return iconResId;
    }
}