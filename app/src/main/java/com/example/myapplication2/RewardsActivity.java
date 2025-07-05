package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class RewardsActivity extends AppCompatActivity {

    private TextView tvLoyaltyProgress;
    private ImageView[] cupImageViews = new ImageView[8];
    private TextView tvCurrentPoints;
    private RecyclerView rvRewardHistory;
    private RewardHistoryAdapter rewardHistoryAdapter;
    private List<RewardHistoryItem> historyItemsList;
    private Button btnRedeemReward;
    private LinearLayout llLoyaltyCardBar;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        userManager = new UserManager(this);

        tvLoyaltyProgress = findViewById(R.id.tv_loyalty_progress);
        btnRedeemReward = findViewById(R.id.btn_redeem_reward);
        tvCurrentPoints = findViewById(R.id.tv_current_points);
        llLoyaltyCardBar = findViewById(R.id.lnloyalty_card_section);

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
        historyItemsList = new ArrayList<>(); // Initialize the list for the adapter
        rewardHistoryAdapter = new RewardHistoryAdapter(historyItemsList);
        rvRewardHistory.setAdapter(rewardHistoryAdapter);

        if (llLoyaltyCardBar != null){
            llLoyaltyCardBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleLoyaltyCardBarClick();
                }
            });
        }

        btnRedeemReward.setOnClickListener(v -> {
            Intent intent = new Intent(RewardsActivity.this, RedeemActivity.class);
            startActivity(intent);
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

        Log.d("RewardsActivity", "onCreate completed.");
    }

    private void handleLoyaltyCardBarClick() {
        int currentStamps = userManager.getStamps();
        if (currentStamps >= 8){
            userManager.redeemStampsReward();
            int pointsToAdd = 100;
            userManager.addPoints(pointsToAdd);
            RewardHistoryItem pointsReward = new RewardHistoryItem(
                    "Loyalty Bonus", // Name for this reward
                    UserManager.getCurrentDateString(), // Get current date
                    pointsToAdd, // Points earned
                    R.drawable.coffee_cup_1
            );
            userManager.addRewardHistoryItem(pointsReward);
            updateLoyaltyCard();
            updateCurrentPoints();
            loadAndDisplayRewardHistory();
            Toast.makeText(this, "Stamps reward redeemed!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(RewardsActivity.this, "Collect " + (8 - currentStamps) + " more stamps to get a reward!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RewardsActivity", "onResume: Refreshing UI data.");
        updateLoyaltyCard();
        updateCurrentPoints();
        loadAndDisplayRewardHistory();
    }
    private void updateLoyaltyCard() {
        int currentStamps = userManager.getStamps();
        tvLoyaltyProgress.setText(String.format("%d/8", currentStamps));
        for (int i = 0; i < cupImageViews.length; i++) {
            if (i < currentStamps) {
                cupImageViews[i].setImageResource(R.drawable.coffee_cup_1); 
            } else {
                cupImageViews[i].setImageResource(R.drawable.coffee_cup_2);
            }
        }
    }

    private void updateCurrentPoints() {
        int points =userManager.getPoints();
        tvCurrentPoints.setText(String.format("%d", points));
    }

    private void loadAndDisplayRewardHistory() {
        if (userManager == null || rewardHistoryAdapter == null || historyItemsList == null) {
            Log.e("RewardsActivity", "Cannot load history: Crucial component is null.");
            return;
        }
        List<RewardHistoryItem> updatedHistory = userManager.getRewardHistory();
        Log.d("RewardsActivity", "Fetched " + updatedHistory.size() + " reward history items.");

        historyItemsList.clear();
        historyItemsList.addAll(updatedHistory);
        rewardHistoryAdapter.notifyDataSetChanged(); // Crucial step to refresh RecyclerView

    }
}