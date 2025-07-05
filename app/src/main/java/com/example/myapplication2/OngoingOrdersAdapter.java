package com.example.myapplication2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OngoingOrdersAdapter extends RecyclerView.Adapter<OngoingOrdersAdapter.ViewHolder> {

    private List<Order> orders;
    private OnCancelClickListener cancelClickListener;

    public interface OnCancelClickListener {
        void onCancelClick(Order order);
    }

    public OngoingOrdersAdapter(List<Order> orders, OnCancelClickListener listener) {
        this.orders = orders;
        this.cancelClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ongoing_order, parent, false); // Create this layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvOrderId.setText("Order ID: " + order.getOrderId().substring(0, 8)); // Show partial ID
        holder.tvOrderStatus.setText("Status: " + order.getStatus());
        holder.tvOrderTotal.setText(String.format(Locale.getDefault(), "Total: $%.2f", order.getTotalPrice()));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        holder.tvOrderTimestamp.setText("Placed: " + sdf.format(new Date(order.getOrderTimestamp())));

        // Build items string
        StringBuilder itemsStr = new StringBuilder();
        for (CartItem item : order.getItems()) {
            itemsStr.append(item.getName()).append(" (Qty: ").append(item.getQuantity()).append(")\n");
        }
        holder.tvOrderItems.setText(itemsStr.toString().trim());


        holder.btnCancelOrder.setOnClickListener(v -> {
            if (cancelClickListener != null) {
                cancelClickListener.onCancelClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void updateOrders(List<Order> newOrders) {
        this.orders.clear();
        this.orders.addAll(newOrders);
        notifyDataSetChanged(); // Or use DiffUtil for better performance
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderStatus, tvOrderItems, tvOrderTimestamp, tvOrderTotal;
        Button btnCancelOrder;

        ViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_ongoing_order_id);
            tvOrderStatus = itemView.findViewById(R.id.tv_ongoing_order_status);
            tvOrderItems = itemView.findViewById(R.id.tv_ongoing_order_items);
            tvOrderTimestamp = itemView.findViewById(R.id.tv_ongoing_order_timestamp);
            tvOrderTotal = itemView.findViewById(R.id.tv_ongoing_order_total);
            btnCancelOrder = itemView.findViewById(R.id.btn_cancel_order);
        }
    }
}
