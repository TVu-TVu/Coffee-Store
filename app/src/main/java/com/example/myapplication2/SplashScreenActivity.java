package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // Import Handler
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000; // 2000 milliseconds = 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, main.class);
                startActivity(mainIntent);
                finish(); // close SplashScreenActivity so that user cannot turn back
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}