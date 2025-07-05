package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    public interface OnCartItemClickListener {
        void onRemoveItemClick(int position, CartItem item);
    }
    private OnCartItemClickListener listener;

    public CartAdapter(Context context, List<CartItem> cartItems, OnCartItemClickListener listener) {
        this.context = context;
        this.cartItems = (cartItems != null) ? new ArrayList<>(cartItems) : new ArrayList<>();
        this.listener = listener; // Set the listener
    }

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = (cartItems != null) ? new ArrayList<>(cartItems) : new ArrayList<>();
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_preview, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        if (item.getImageResId() != 0) {
            holder.ivItemImage.setImageResource(item.getImageResId());
            holder.ivItemImage.setVisibility(View.VISIBLE);
        } else {
            holder.ivItemImage.setVisibility(View.GONE);
        }

        // Format drink name and options
        String drinkNameWithOptions = item.getName();
        StringBuilder optionsBuilder = new StringBuilder();
        if (item.getSize() != null && !item.getSize().isEmpty()) {
            optionsBuilder.append(item.getSize().substring(0, 1)).append(", ");
        }
        if (item.getTemperature() != null && !item.getTemperature().isEmpty()) {
            optionsBuilder.append(item.getTemperature()).append(", ");
        }
        if (item.getShot() != null && !item.getShot().isEmpty()) {
            optionsBuilder.append(item.getShot()).append(" Shot, ");
        }
        if (item.getIce() != null && !item.getIce().isEmpty()) {
            optionsBuilder.append(item.getIce()).append(" Ice, ");
        }

        if (optionsBuilder.length() > 0) {
            optionsBuilder.setLength(optionsBuilder.length() - 2); // Remove trailing ", "
            drinkNameWithOptions += " (" + optionsBuilder.toString() + ")";
        }
        holder.tvItemName.setText(drinkNameWithOptions);

        holder.tvItemPriceQuantity.setText(String.format(Locale.getDefault(), "$%.2f x %d", item.getPrice(), item.getQuantity()));
        holder.tvItemTotalPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice() * item.getQuantity()));

        if (holder.btnRemoveItem != null && listener != null) {
            holder.btnRemoveItem.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition(); // Use getAdapterPosition() for safety
                if (currentPosition != RecyclerView.NO_POSITION) {
                    listener.onRemoveItemClick(currentPosition, cartItems.get(currentPosition));
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        if (this.cartItems == null) {
            this.cartItems = new ArrayList<>();
        }
        this.cartItems.clear();
        if (newCartItems != null) {
            this.cartItems.addAll(newCartItems);
        }
        notifyDataSetChanged();
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName;
        TextView tvItemPriceQuantity;
        TextView tvItemTotalPrice;
        ImageButton btnRemoveItem;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImage = itemView.findViewById(R.id.iv_cart_item_image);
            tvItemName = itemView.findViewById(R.id.tv_cart_item_name);
            tvItemPriceQuantity = itemView.findViewById(R.id.tv_cart_item_price_quantity);
            tvItemTotalPrice = itemView.findViewById(R.id.tv_cart_item_total_price);
            btnRemoveItem = itemView.findViewById(R.id.btn_remove_cart_item);
        }
    }
}