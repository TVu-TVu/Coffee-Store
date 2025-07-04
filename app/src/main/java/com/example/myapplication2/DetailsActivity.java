package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_DRINK_NAME = "drink_name";
    public static final String EXTRA_DRINK_IMAGE_RES_ID = "drink_image_res_id";

    private ImageView ivDrinkImage;
    private TextView tvDrinkNameDisplay;
    private TextView tvQuantity;
    private TextView tvTotalAmount;

    private TextView tvShotSingle, tvShotDouble;
    private ImageButton btnHot, btnCold;
    private ImageButton btnSizeSmall, btnSizeMedium, btnSizeLarge;
    private ImageButton btnIceSmall, btnIceMedium, btnIceLarge;
    private Button btnAddToCart;
    private ImageButton btnback;
    private ImageButton btnCartPreview;

    private int quantity = 1; // Initialize quantity
    private boolean isDoubleShot = false; // Initialize shot option
    private String selectedTemperature = "Cold"; // Initialize temperature option
    private String selectedSize = "Small"; // Initialize size option
    private String selectedIce = "Small"; // Initialize ice option
    private UserManager userManager;
    private static final double PRICE_SMALL = 2.00;
    private static final double PRICE_MEDIUM = 2.50;
    private static final double PRICE_LARGE = 3.00;

    private static final double PRICE_DOUBLE_SHOT_EXTRA = 0.50;

    private String originalDrinkName;
    private int originalDrinkImageResId;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        userManager = new UserManager(this);

        ivDrinkImage = findViewById(R.id.iv_drink_image);
        tvDrinkNameDisplay = findViewById(R.id.tv_drink_name);
        tvQuantity = findViewById(R.id.tv_quantity); // Initialize
        tvTotalAmount = findViewById(R.id.tv_total_amount); // Initialize

        ImageButton btnMinus = findViewById(R.id.btn_quantity_minus);
        ImageButton btnPlus = findViewById(R.id.btn_quantity_plus);

        tvShotSingle = findViewById(R.id.tv_shot_single);
        tvShotDouble = findViewById(R.id.tv_shot_double);
        btnHot = findViewById(R.id.btn_hot);
        btnCold = findViewById(R.id.btn_cold);

        btnSizeSmall = findViewById(R.id.btn_size_small);
        btnSizeMedium = findViewById(R.id.btn_size_medium);
        btnSizeLarge = findViewById(R.id.btn_size_large);

        btnIceSmall = findViewById(R.id.btn_ice_small);
        btnIceMedium = findViewById(R.id.btn_ice_medium);
        btnIceLarge = findViewById(R.id.btn_ice_large);

        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnback = findViewById(R.id.btn_back);
        btnCartPreview = findViewById(R.id.btn_cart);
        cartManager = CartManager.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            originalDrinkName = intent.getStringExtra(EXTRA_DRINK_NAME);
            originalDrinkImageResId = intent.getIntExtra(EXTRA_DRINK_IMAGE_RES_ID, 0); // 0 is a default value if not found
        }
        if (originalDrinkName != null) {
            tvDrinkNameDisplay.setText(originalDrinkName);
        }
        if (originalDrinkImageResId != 0) {
            ivDrinkImage.setImageResource(originalDrinkImageResId);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String drinkName = extras.getString(EXTRA_DRINK_NAME, "Unknown Drink");
            int drinkImageResId = extras.getInt(EXTRA_DRINK_IMAGE_RES_ID, 0); // 0 if not found

            tvDrinkNameDisplay.setText(drinkName);
            if (drinkImageResId != 0) {
                ivDrinkImage.setImageResource(drinkImageResId);
            } else {
                // Set a placeholder or hide if no image is provided
                ivDrinkImage.setImageResource(R.drawable.group_7103);
            }
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish()); // Go back to previous activity

        updateShotSelectionUI();
        updateTemperatureSelectionUI();
        updateSizeSelectionUI();
        updateIceSelectionUI();

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotalAmount();
            }
        });

        btnPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            updateTotalAmount();
        });

        tvShotSingle.setOnClickListener(v -> {
            isDoubleShot = false;
            updateShotSelectionUI();
            updateTotalAmount();
        });

        tvShotDouble.setOnClickListener(v -> {
            isDoubleShot = true;
            updateShotSelectionUI();
            updateTotalAmount();
        });

        btnHot.setOnClickListener(v -> {
            selectedTemperature = "Hot";
            updateTemperatureSelectionUI();
        });

        btnCold.setOnClickListener(v -> {
            selectedTemperature = "Cold";
            updateTemperatureSelectionUI();
        });

        btnSizeSmall.setOnClickListener(v -> {
            selectedSize = "Small";
            updateSizeSelectionUI();
            updateTotalAmount();
        });

        btnSizeMedium.setOnClickListener(v -> {
            selectedSize = "Medium";
            updateSizeSelectionUI();
            updateTotalAmount();
        });

        btnSizeLarge.setOnClickListener(v -> {
            selectedSize = "Large";
            updateSizeSelectionUI();
            updateTotalAmount();
        });

        btnIceSmall.setOnClickListener(v -> {
            selectedIce = "Small";
            updateIceSelectionUI();
            updateTotalAmount();
        });

        btnIceMedium.setOnClickListener(v -> {
            selectedIce = "Medium";
            updateIceSelectionUI();
            updateTotalAmount();
        });

        btnIceLarge.setOnClickListener(v -> {
            selectedIce = "Large";
            updateIceSelectionUI();
            updateTotalAmount();
        });

        btnAddToCart.setOnClickListener(v -> {
            double itemPrice = calculateCurrentItemPrice();
            int currentImageResId = extras.getInt(EXTRA_DRINK_IMAGE_RES_ID, R.drawable.group_7103); // Lấy lại imageResId

            CartItem newCartItem = new CartItem(
                    tvDrinkNameDisplay.getText().toString(),
                    itemPrice, // Price per item, before quantity
                    quantity,
                    selectedSize,
                    selectedTemperature,
                    isDoubleShot ? "Double" : "Single",
                    selectedIce,
                    currentImageResId
            );
            CartManager.getInstance().addItemToCart(newCartItem);
            Gson gson = new Gson();

            String itemJson = gson.toJson(newCartItem);
            RewardHistoryItem rewardHistoryItem = gson.fromJson(itemJson, RewardHistoryItem.class);
            userManager.addRewardHistoryItem(rewardHistoryItem);


            Toast.makeText(DetailsActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            // Optionally, clear selections or reset quantity after adding to cart
            Intent intentToCart = new Intent(DetailsActivity.this, MyCartActivity.class);
            startActivity(intentToCart);
        });

        btnback.setOnClickListener(v -> {
            Intent intent2 = new Intent(DetailsActivity.this, main.class);
            startActivity(intent2);
        });

        // Handle Cart Preview button click
        btnCartPreview.setOnClickListener(v -> showCartPreviewDialog());
    }

    private double calculateCurrentItemPrice() {
        double currentPrice = 0.0;
        switch (selectedSize) {
            case "Small":
                currentPrice = PRICE_SMALL;
                break;
            case "Medium":
                currentPrice = PRICE_MEDIUM;
                break;
            case "Large":
                currentPrice = PRICE_LARGE;
                break;
        }
        if (isDoubleShot) {
            currentPrice += PRICE_DOUBLE_SHOT_EXTRA;
        }
        return currentPrice;
    }

    // Phương thức để hiển thị BottomSheetDialog
    private void showCartPreviewDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_cart_preview, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        RecyclerView rvCartItems = bottomSheetView.findViewById(R.id.rv_cart_items_preview);
        TextView tvCartTotal = bottomSheetView.findViewById(R.id.tv_cart_preview_total);
        Button btnGoToCart = bottomSheetView.findViewById(R.id.btn_go_to_cart);

        List<CartItem> currentCartItems = cartManager.getInstance().getCartItems();

        CartAdapter adapter = new CartAdapter(this, currentCartItems);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(adapter);

        double totalCartAmount = 0.0;
        for (CartItem item : currentCartItems) {
            totalCartAmount += (item.getPrice() * item.getQuantity());
        }
        tvCartTotal.setText(String.format("Total: $%.2f", totalCartAmount));

        btnGoToCart.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(DetailsActivity.this, MyCartActivity.class); // Giả sử bạn có MyCartActivity
            startActivity(intent);
        });

        bottomSheetDialog.show();
    }


    private void updateShotSelectionUI() {
        if (isDoubleShot) {
            tvShotDouble.setBackgroundResource(R.drawable.shot_button_gray);
            tvShotDouble.setTextColor(getResources().getColor(android.R.color.white));
            tvShotSingle.setBackgroundResource(R.drawable.shot_button);
            tvShotSingle.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        } else {
            tvShotSingle.setBackgroundResource(R.drawable.shot_button_gray);
            tvShotSingle.setTextColor(getResources().getColor(android.R.color.white));
            tvShotDouble.setBackgroundResource(R.drawable.shot_button);
            tvShotDouble.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        }
    }

    private void updateTemperatureSelectionUI() {
        if (selectedTemperature.equals("Hot")) {
            btnHot.setBackgroundResource(R.color.white);
            btnHot.setColorFilter(getResources().getColor(android.R.color.black));
            btnCold.setBackgroundResource(R.color.white);
            btnCold.setColorFilter(getResources().getColor(R.color.colorText4));
        } else {
            btnCold.setBackgroundResource(R.color.white);
            btnCold.setColorFilter(getResources().getColor(android.R.color.black));
            btnHot.setBackgroundResource(R.color.white);
            btnHot.setColorFilter(getResources().getColor(R.color.colorText4));
        }
    }

    private void updateSizeSelectionUI() {
        // Reset all to unselected
        btnSizeSmall.setBackgroundResource(R.color.white);
        btnSizeSmall.setColorFilter(getResources().getColor(R.color.colorTextPrimary));
        btnSizeMedium.setBackgroundResource(R.color.white);
        btnSizeMedium.setColorFilter(getResources().getColor(R.color.colorTextPrimary));
        btnSizeLarge.setBackgroundResource(R.color.white);
        btnSizeLarge.setColorFilter(getResources().getColor(R.color.colorTextPrimary));

        // Set selected
        switch (selectedSize) {
            case "Small":
                btnSizeSmall.setBackgroundResource(R.color.colorText4);
                btnSizeSmall.setColorFilter(getResources().getColor(android.R.color.black));
                break;
            case "Medium":
                btnSizeMedium.setBackgroundResource(R.color.colorText4);
                btnSizeMedium.setColorFilter(getResources().getColor(android.R.color.black));
                break;
            case "Large":
                btnSizeLarge.setBackgroundResource(R.color.colorText4);
                btnSizeLarge.setColorFilter(getResources().getColor(android.R.color.black));
                break;
        }
    }

    private void updateIceSelectionUI() {
        // Reset all to unselected
        btnIceSmall.setBackgroundResource(R.color.white);
        btnIceSmall.setColorFilter(getResources().getColor(R.color.colorTextPrimary));
        btnIceMedium.setBackgroundResource(R.color.white);
        btnIceMedium.setColorFilter(getResources().getColor(R.color.colorTextPrimary));
        btnIceLarge.setBackgroundResource(R.color.white);
        btnIceLarge.setColorFilter(getResources().getColor(R.color.colorTextPrimary));

        // Set selected
        switch (selectedIce) {
            case "Small":
                btnIceSmall.setBackgroundResource(R.color.colorText4);
                btnIceSmall.setColorFilter(getResources().getColor(android.R.color.black));
                break;
            case "Medium":
                btnIceMedium.setBackgroundResource(R.color.colorText4);
                btnIceMedium.setColorFilter(getResources().getColor(android.R.color.black));
                break;
            case "Large":
                btnIceLarge.setBackgroundResource(R.color.colorText4);
                btnSizeLarge.setColorFilter(getResources().getColor(android.R.color.black));
                break;
        }
    }

    private void updateTotalAmount() {
        double currentPrice = 0.0;
        switch (selectedSize) {
            case "Small":
                currentPrice = PRICE_SMALL;
                break;
            case "Medium":
                currentPrice = PRICE_MEDIUM;
                break;
            case "Large":
                currentPrice = PRICE_LARGE;
                break;
        }
        if (isDoubleShot) {
            currentPrice += PRICE_DOUBLE_SHOT_EXTRA; // +0.5$
        }

        double totalAmount = currentPrice * quantity;
        tvTotalAmount.setText(String.format("$%.2f", totalAmount));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}