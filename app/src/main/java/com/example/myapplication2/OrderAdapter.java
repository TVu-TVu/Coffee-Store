// OrderAdapter.java
package com.example.myapplication2; // Ensure this matches your package name

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderItem> orderList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onTrackOrderClick(OrderItem orderItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OrderAdapter(List<OrderItem> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderItem item = orderList.get(position);
        holder.tvOrderName.setText(item.getOrderName());
        holder.tvOrderId.setText(item.getOrderId());
        holder.tvOrderTime.setText(item.getOrderTime());
        holder.tvOrderQuantity.setText(item.getOrderQuantity());
        holder.tvTotalAmount.setText(item.getTotalAmount());
        holder.tvOrderStatus.setText(item.getOrderStatus());
        holder.ivOrderStatusIcon.setImageResource(item.getStatusIconResId());

        if (item.isOngoing()) {
            holder.btnTrackOrder.setVisibility(View.VISIBLE);
            holder.btnTrackOrder.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTrackOrderClick(item);
                }
            });
        } else {
            holder.btnTrackOrder.setVisibility(View.GONE);
            holder.btnTrackOrder.setOnClickListener(null); // Clear listener for recycled views
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderName;
        TextView tvOrderId;
        TextView tvOrderTime;
        TextView tvOrderQuantity;
        TextView tvTotalAmount;
        TextView tvOrderStatus;
        ImageView ivOrderStatusIcon;
        Button btnTrackOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tv_order_name);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderTime = itemView.findViewById(R.id.tv_order_time);
            tvOrderQuantity = itemView.findViewById(R.id.tv_order_quantity);
            tvTotalAmount = itemView.findViewById(R.id.tv_order_amount);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            ivOrderStatusIcon = itemView.findViewById(R.id.iv_order_status_icon);
            btnTrackOrder = itemView.findViewById(R.id.btn_track_order);
        }
    }
}