package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

public class main extends AppCompatActivity {

    private TextView tvGreetingName;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = new UserManager(this);
        tvGreetingName = findViewById(R.id.tv_greeting_name);

        ImageButton profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, ProfileActivity.class);
                startActivity(intent);
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
}
