package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;


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

        tvLoyaltyProgress = findViewById(R.id.tv_loyalty_progress);
        btnRedeemReward = findViewById(R.id.btn_redeem_reward);
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
        historyItems.add(new RewardHistoryItem("Americano", "2024-06-25", 20, R.drawable.coffee_cup_1));
        historyItems.add(new RewardHistoryItem("Capuccino", "2024-06-10", 20, R.drawable.coffee_cup_1));
        historyItems.add(new RewardHistoryItem("Capuccino", "2024-05-01", 20, R.drawable.coffee_cup_1));
        historyItems.add(new RewardHistoryItem("Mocha", "2024-04-25", 20, R.drawable.coffee_cup_1));
        historyItems.add(new RewardHistoryItem("Flat White", "2024-04-22", 20, R.drawable.coffee_cup_1));

        rewardHistoryAdapter = new RewardHistoryAdapter(historyItems);
        rvRewardHistory.setAdapter(rewardHistoryAdapter);

        btnRedeemReward.setOnClickListener(v -> {

        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_shop) {
                    Intent intent = new Intent(RewardsActivity.this, main.class);
                    startActivity(intent);
                    return true;
                }
                if (itemId == R.id.navigation_receipt) {
                    Intent intent = new Intent(RewardsActivity.this, MyOrderActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        updateLoyaltyCard(5);
        updateCurrentPoints(1250);
    }

    private void updateLoyaltyCard(int currentStamps) {
        tvLoyaltyProgress.setText(String.format("%d/8", currentStamps));

        for (int i = 0; i < cupImageViews.length; i++) {
            if (i < currentStamps) {
                cupImageViews[i].setImageResource(R.drawable.coffee_cup_1); 
            } else {
                cupImageViews[i].setImageResource(R.drawable.coffee_cup_2);
            }
        }

        // if (currentStamps >= 8) {
          //  btnRedeemReward.setEnabled(true);
            //btnRedeemReward.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700, null)); // Sử dụng màu teal_700
        //} else {
          //  btnRedeemReward.setEnabled(false);
            //btnRedeemReward.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray, null));
        //}
    }

    private void updateCurrentPoints(int points) {
        tvCurrentPoints.setText(String.format("%d", points));
    }
}