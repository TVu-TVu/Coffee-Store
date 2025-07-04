// HistoryOrdersFragment.java
package com.example.myapplication2; // Ensure this matches your package name

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrdersFragment extends Fragment {

    private RecyclerView rvHistoryOrders;
    private TextView tvNoHistoryOrders;
    private OrderAdapter orderAdapter;
    private List<OrderItem> historyOrderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_orders, container, false);

        rvHistoryOrders = view.findViewById(R.id.rv_history_orders);
        tvNoHistoryOrders = view.findViewById(R.id.tv_no_history_orders);

        rvHistoryOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample Data for History Orders
        historyOrderList = new ArrayList<>();
        historyOrderList.add(new OrderItem(
                "Flat White",
                "#5795688",
                "02:00 PM, Yesterday",
                "1 Item",
                "$5.00",
                "Delivered",
                R.drawable.resource_true,
                false
        ));
        historyOrderList.add(new OrderItem(
                "Mocha",
                "#5795687",
                "10:00 AM, 2 Days Ago",
                "2 Items",
                "$10.00",
                "Cancelled",
                R.drawable.resource_false, // You need to add this drawable
                false
        ));
        // Add more history orders here if needed

        orderAdapter = new OrderAdapter(historyOrderList);
        rvHistoryOrders.setAdapter(orderAdapter);

        // History orders don't have a "Track Order" button, so no click listener needed for the button itself.
        // However, if you want to handle item clicks (e.g., to view order details), you would add it here.

        // Show/hide "No past orders" message
        if (historyOrderList.isEmpty()) {
            tvNoHistoryOrders.setVisibility(View.VISIBLE);
            rvHistoryOrders.setVisibility(View.GONE);
        } else {
            tvNoHistoryOrders.setVisibility(View.GONE);
            rvHistoryOrders.setVisibility(View.VISIBLE);
        }

        return view;
    }
}