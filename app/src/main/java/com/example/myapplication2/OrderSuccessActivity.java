package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersuccess);

        Button btnTrackOrder = findViewById(R.id.btn_track_my_order);
        if (btnTrackOrder != null) {
            btnTrackOrder.setOnClickListener(v -> {
                // Intent to go to MyOrderActivity
                Intent intent = new Intent(OrderSuccessActivity.this, MyOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

    }
}