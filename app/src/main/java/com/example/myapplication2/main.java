package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import  android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
}