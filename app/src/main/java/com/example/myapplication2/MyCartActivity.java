package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private TextView tvCartTotalAmount;
    private TextView tvEmptyCartMessage;
    private Button btnPlaceOrder;
    private ImageButton btnBack;
    private CartAdapter cartAdapter;
    private CartManager yourCartManager;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        userManager = new UserManager(this);
        yourCartManager = CartManager.getInstance();

        rvCartItems = findViewById(R.id.rv_cart_items);
        tvCartTotalAmount = findViewById(R.id.tv_cart_total_amount);
        tvEmptyCartMessage = findViewById(R.id.tv_empty_cart_message);
        btnBack = findViewById(R.id.btn_back);
        btnPlaceOrder = findViewById(R.id.btn_place_order);

        setupRecyclerView();
        setupClicklisteners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItemsAndUpdateUI();
    }

    private void setupRecyclerView() {
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, new ArrayList<>());
        rvCartItems.setAdapter(cartAdapter);
    }

    private void setupClicklisteners() {
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, main.class);
            startActivity(intent);
        });

        btnPlaceOrder.setOnClickListener(v -> {
            List<CartItem> currentItems = yourCartManager.getCartItems();
            if (currentItems == null || currentItems.isEmpty()) {
                Toast.makeText(MyCartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            yourCartManager.clearCart();
            Intent intent = new Intent(this, OrderSuccessActivity.class);
            startActivity(intent);
        });
    }

    private void loadCartItemsAndUpdateUI() {
        List<CartItem> currentCartItems = yourCartManager.getCartItems();
        if (cartAdapter != null) {
            cartAdapter.updateCartItems(currentCartItems);
        }
        if (currentCartItems.isEmpty()) {
            tvEmptyCartMessage.setVisibility(View.VISIBLE);
            rvCartItems.setVisibility(View.GONE);
            tvCartTotalAmount.setText(String.format(Locale.getDefault(), "$%.2f", 0.0));
            btnPlaceOrder.setEnabled(false);
        } else {
            tvEmptyCartMessage.setVisibility(View.GONE);
            rvCartItems.setVisibility(View.VISIBLE);
            updateCartTotalAmount();
            btnPlaceOrder.setEnabled(true);
        }
    }

    private void updateCartTotalAmount() {
        double total = yourCartManager.calculateTotalPrice();
        tvCartTotalAmount.setText(String.format(Locale.getDefault(), "$%.2f", total));
    }
}





