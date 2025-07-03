package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Context context;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvItemName.setText(item.getDrinkName());
        holder.ivItemImage.setImageResource(item.getDrinkImageResId()); // Ensure this drawable exists

        String details = String.format(Locale.getDefault(),
                "Size: %s, %s, %s, Ice: %s",
                item.getSelectedSize(),
                item.isDoubleShot() ? "Double Shot" : "Single Shot",
                item.getSelectedTemperature(),
                item.getSelectedIce());
        holder.tvItemDetails.setText(details);

        holder.tvItemUnitPrice.setText(String.format(Locale.getDefault(), "Unit Price: $%.2f", item.getUnitPrice()));
        holder.tvItemQuantity.setText(String.format(Locale.getDefault(), "Qty: %d", item.getQuantity()));
        holder.tvItemTotalPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        this.cartItems.clear();
        this.cartItems.addAll(newCartItems);
        notifyDataSetChanged(); // Or use DiffUtil for better performance
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName, tvItemDetails, tvItemUnitPrice, tvItemQuantity, tvItemTotalPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImage = itemView.findViewById(R.id.iv_cart_item_image);
            tvItemName = itemView.findViewById(R.id.tv_cart_item_name);
            tvItemDetails = itemView.findViewById(R.id.tv_cart_item_details);
            tvItemUnitPrice = itemView.findViewById(R.id.tv_cart_item_unit_price);
            tvItemQuantity = itemView.findViewById(R.id.tv_cart_item_quantity);
            tvItemTotalPrice = itemView.findViewById(R.id.tv_cart_item_total_price);
        }
    }
}