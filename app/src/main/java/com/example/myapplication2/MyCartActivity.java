package com.example.myapplication2;

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
    private CartAdapter cartAdapter;
    private List<CartItem> itemsInCart;
    private CartManager yourCartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);


        rvCartItems = findViewById(R.id.rv_cart_items);
        tvCartTotalAmount = findViewById(R.id.tv_cart_total_amount);
        tvEmptyCartMessage = findViewById(R.id.tv_empty_cart_message);
        ImageButton btnBack = findViewById(R.id.btn_back);
        Button btnPlaceOrder = findViewById(R.id.btn_place_order);

        yourCartManager = CartManager.getInstance();
        //RecyclerView
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        itemsInCart = yourCartManager.getCartItems();
        cartAdapter = new CartAdapter(this, itemsInCart);
        rvCartItems.setAdapter(cartAdapter);

        // Event button Back
        btnBack.setOnClickListener(v -> onBackPressed());

        // Event button Place Order
        btnPlaceOrder.setOnClickListener(v -> {
            Toast.makeText(MyCartActivity.this, "Order Placed!", Toast.LENGTH_SHORT).show();
            yourCartManager.clearCart(); // Clear cart after placing order
            loadCartItems(); // Refresh display
        });
        loadCartItems();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    private void loadCartItems() {
        itemsInCart = yourCartManager.getCartItems();
        cartAdapter.updateCartItems(itemsInCart);

        if (itemsInCart.isEmpty()) {
            tvEmptyCartMessage.setVisibility(View.VISIBLE);
            rvCartItems.setVisibility(View.GONE);
        } else {
            tvEmptyCartMessage.setVisibility(View.GONE);
            rvCartItems.setVisibility(View.VISIBLE);
        }
        updateCartTotalAmount();
    }

    private void updateCartTotalAmount() {

        double total = 0.0;
        total = yourCartManager.calculateTotalPrice();
        tvCartTotalAmount.setText(String.format("$%.2f", total));
    }

}






