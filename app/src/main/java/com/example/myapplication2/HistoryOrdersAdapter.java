package com.example.myapplication2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryOrdersAdapter extends RecyclerView.Adapter<HistoryOrdersAdapter.ViewHolder> {

    private List<Order> orders;

    public HistoryOrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_order, parent, false); // Create this layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvOrderId.setText("Order ID: " + order.getOrderId().substring(0,8));
        holder.tvOrderStatus.setText("Status: " + order.getStatus());
        holder.tvOrderTotal.setText(String.format(Locale.getDefault(), "Total: $%.2f", order.getTotalPrice()));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        holder.tvOrderTimestamp.setText("Concluded: " + sdf.format(new Date(order.getOrderTimestamp()))); // Or a specific delivered/cancelled timestamp

        StringBuilder itemsStr = new StringBuilder();
        for (CartItem item : order.getItems()) {
            itemsStr.append(item.getName()).append(" (Qty: ").append(item.getQuantity()).append(")\n");
        }
        holder.tvOrderItems.setText(itemsStr.toString().trim());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void updateOrders(List<Order> newOrders) {
        this.orders.clear();
        this.orders.addAll(newOrders);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderStatus, tvOrderItems, tvOrderTimestamp, tvOrderTotal;

        ViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_history_order_id);
            tvOrderStatus = itemView.findViewById(R.id.tv_history_order_status);
            tvOrderItems = itemView.findViewById(R.id.tv_history_order_items);
            tvOrderTimestamp = itemView.findViewById(R.id.tv_history_order_timestamp);
            tvOrderTotal = itemView.findViewById(R.id.tv_history_order_total);
        }
    }
}
