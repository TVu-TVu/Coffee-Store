package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class main extends AppCompatActivity {

    private UserManager userManager;
    private TextView tvGreetingName;
    private TextView tvLoyaltyProgress;
    private ImageView[] cupImageViews = new ImageView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = new UserManager(this);
        tvGreetingName = findViewById(R.id.tv_greeting_name);
        tvLoyaltyProgress = findViewById(R.id.tv_loyalty_progress);

        cupImageViews[0] = findViewById(R.id.cup_1);
        cupImageViews[1] = findViewById(R.id.cup_2);
        cupImageViews[2] = findViewById(R.id.cup_3);
        cupImageViews[3] = findViewById(R.id.cup_4);
        cupImageViews[4] = findViewById(R.id.cup_5);
        cupImageViews[5] = findViewById(R.id.cup_6);
        cupImageViews[6] = findViewById(R.id.cup_7);
        cupImageViews[7] = findViewById(R.id.cup_8);
        updateLoyaltyCard(userManager.getStamps());

        ImageButton profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, MyCartActivity.class);
                startActivity(intent);
            }
        });

        CardView loyaltyCard = findViewById(R.id.loyalty_card_section);
        loyaltyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, RewardsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_gift) {
                    Intent intent = new Intent(main.this, RewardsActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (itemId == R.id.navigation_receipt) {
                    Intent intent = new Intent(main.this, MyOrderActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        CardView cardAmericano = findViewById(R.id.americano_card);
        CardView cardCappuccino = findViewById(R.id.cappuccino_card);
        CardView cardMocha = findViewById(R.id.mocha_card);
        CardView cardFlatWhite = findViewById(R.id.flat_white_card);

        cardAmericano.setOnClickListener(v -> navigateToDetails("Americano", R.drawable.americanoimg));
        cardCappuccino.setOnClickListener(v -> navigateToDetails("Cappuccino", R.drawable.cappuchinoimg));
        cardMocha.setOnClickListener(v -> navigateToDetails("Mocha", R.drawable.mochaimg));
        cardFlatWhite.setOnClickListener(v -> navigateToDetails("Flat White", R.drawable.flatwhiteimg));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load and display the user name every time the activity resumes
        loadAndDisplayUserName(); // Add this call
        updateLoyaltyCard(userManager.getStamps());
    }

    private void loadAndDisplayUserName() {
        String userName = userManager.getFullName();
        tvGreetingName.setText(userName);

    }

    private void navigateToDetails(String drinkName, int imageResId) {
        Intent intent = new Intent(main.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_DRINK_NAME, drinkName);
        intent.putExtra(DetailsActivity.EXTRA_DRINK_IMAGE_RES_ID, imageResId);
        startActivity(intent);
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
    }
}
