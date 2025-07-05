package com.example.myapplication2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RewardHistoryAdapter extends RecyclerView.Adapter<RewardHistoryAdapter.RewardHistoryViewHolder> {

    private List<RewardHistoryItem> historyList;

    public RewardHistoryAdapter(List<RewardHistoryItem> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public RewardHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward_history, parent, false);
        return new RewardHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardHistoryViewHolder holder, int position) {
        RewardHistoryItem item = historyList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDate.setText(item.getDate());
        holder.tvPointsEarned.setText(String.valueOf(item.getPointsEarned()));
        holder.ivIcon.setImageResource(item.getIconResId());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class RewardHistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvDate;
        TextView tvPointsEarned;

        public RewardHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_reward_icon);
            tvTitle = itemView.findViewById(R.id.tv_reward_title);
            tvDate = itemView.findViewById(R.id.tv_reward_date);
            tvPointsEarned = itemView.findViewById(R.id.tv_reward_points_earned);
        }
    }
}