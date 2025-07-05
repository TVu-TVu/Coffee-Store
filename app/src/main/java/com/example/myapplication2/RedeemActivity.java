package com.example.myapplication2; // Make sure this matches your package name

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RedeemActivity extends AppCompatActivity {

    // Assuming you have a UserManager to access current points
    private UserManager userManager;
    private Button btnRedeemAmericano;
    private Button btnRedeemCappuccino;
    private Button btnRedeemMocha;
    private Button btnRedeemFlatWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        userManager = new UserManager(this);

        btnRedeemAmericano = findViewById(R.id.btn_redeem_americano);
        btnRedeemCappuccino = findViewById(R.id.btn_redeem_cappuccino);
        btnRedeemMocha = findViewById(R.id.btn_redeem_mocha);
        btnRedeemFlatWhite = findViewById(R.id.btn_redeem_flat_white);

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> onBackPressed());

        // Set OnClickListener for each redeem button
        btnRedeemAmericano.setOnClickListener(v -> attemptRedeem("Americano", 200));
        btnRedeemCappuccino.setOnClickListener(v -> attemptRedeem("Cappuccino", 200));
        btnRedeemMocha.setOnClickListener(v -> attemptRedeem("Mocha", 200));
        btnRedeemFlatWhite.setOnClickListener(v -> attemptRedeem("Flat White", 200));

        // You might want to update button states based on current points
        updateRedeemButtonStates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update button states if points might have changed in another activity
        updateRedeemButtonStates();
    }

    private void attemptRedeem(String drinkName, int pointsCost) {
        int currentPoints = userManager.getPoints();

        if (currentPoints >= pointsCost) {
            // Deduct points
            userManager.subtractPoints(pointsCost);
            Toast.makeText(this, "You redeemed a " + drinkName + "!", Toast.LENGTH_SHORT).show();

            // Re-evaluate button states after redemption
            updateRedeemButtonStates();
        } else {
            Toast.makeText(this, "Not enough points to redeem " + drinkName + ".", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRedeemButtonStates() {
        int currentPoints = userManager.getPoints(); // Get current points
        if (currentPoints >= 200) {
            btnRedeemAmericano.setEnabled(true);
            btnRedeemAmericano.setBackgroundResource(R.color.colorBackgroundDark);
            btnRedeemCappuccino.setEnabled(true);
            btnRedeemCappuccino.setBackgroundResource(R.color.colorBackgroundDark);
            btnRedeemMocha.setEnabled(true);
            btnRedeemMocha.setBackgroundResource(R.color.colorBackgroundDark);
            btnRedeemFlatWhite.setEnabled(true);
            btnRedeemFlatWhite.setBackgroundResource(R.color.colorBackgroundDark);
        }
    }
}