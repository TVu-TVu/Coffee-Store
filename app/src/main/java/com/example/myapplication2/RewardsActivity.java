package com.example.myapplication2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;


public class RewardsActivity extends AppCompatActivity {

    private TextView tvLoyaltyProgress;
    private ImageView[] cupImageViews = new ImageView[8];
    private TextView tvCurrentPoints;
    private RecyclerView rvRewardHistory;
    private RewardHistoryAdapter rewardHistoryAdapter;
    private Button btnRedeemReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        ImageButton btnBack = findViewById(R.id.btn_back);
        tvLoyaltyProgress = findViewById(R.id.tv_loyalty_progress);
        Button btnRedeemReward = findViewById(R.id.btn_redeem_reward);
        tvCurrentPoints = findViewById(R.id.tv_current_points);

        // Ánh xạ các ImageView của cốc
        cupImageViews[0] = findViewById(R.id.cup_1);
        cupImageViews[1] = findViewById(R.id.cup_2);
        cupImageViews[2] = findViewById(R.id.cup_3);
        cupImageViews[3] = findViewById(R.id.cup_4);
        cupImageViews[4] = findViewById(R.id.cup_5);
        cupImageViews[5] = findViewById(R.id.cup_6);
        cupImageViews[6] = findViewById(R.id.cup_7);
        cupImageViews[7] = findViewById(R.id.cup_8);

        // Cấu hình RecyclerView
        rvRewardHistory = findViewById(R.id.rv_reward_history);
        rvRewardHistory.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu giả cho lịch sử phần thưởng
        List<RewardHistoryItem> historyItems = new ArrayList<>();
        historyItems.add(new RewardHistoryItem("Free Coffee Redeemed", "2024-06-25", "Completed", R.drawable.coffee_cup_1));
        historyItems.add(new RewardHistoryItem("Free Pastry Voucher", "2024-06-10", "Expired", R.drawable.coffee_cup_2)); // Thay thế bằng icon thực tế
        historyItems.add(new RewardHistoryItem("Bonus Points Earned", "2024-05-01", "Completed", R.drawable.coffee_cup_1)); // Thay thế bằng icon thực tế

        rewardHistoryAdapter = new RewardHistoryAdapter(historyItems);
        rvRewardHistory.setAdapter(rewardHistoryAdapter);


        btnBack.setOnClickListener(v -> onBackPressed());

        btnRedeemReward.setOnClickListener(v -> {

        });


        updateLoyaltyCard(5);
        updateCurrentPoints(1250);
    }

    private void updateLoyaltyCard(int currentStamps) {
        tvLoyaltyProgress.setText(String.format("%d/8", currentStamps));

        for (int i = 0; i < cupImageViews.length; i++) {
            if (i < currentStamps) {
                cupImageViews[i].setImageResource(R.drawable.coffee_cup_1); // Cốc đã tích lũy
            } else {
                cupImageViews[i].setImageResource(R.drawable.coffee_cup_2); // Cốc chưa tích lũy
            }
        }

        if (currentStamps >= 8) {
            btnRedeemReward.setEnabled(true);
            btnRedeemReward.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700, null)); // Sử dụng màu teal_700
        } else {
            btnRedeemReward.setEnabled(false);
            btnRedeemReward.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray, null));
        }
    }

    private void updateCurrentPoints(int points) {
        tvCurrentPoints.setText(String.format("%d Points", points));
    }
}