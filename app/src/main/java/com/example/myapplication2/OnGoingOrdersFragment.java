// OnGoingOrdersFragment.java
package com.example.myapplication2; // Ensure this matches your package name

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OnGoingOrdersFragment extends Fragment {

    private RecyclerView rvOnGoingOrders;
    private TextView tvNoOnGoingOrders;
    private OrderAdapter orderAdapter;
    private List<OrderItem> onGoingOrderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_going_orders, container, false);

        rvOnGoingOrders = view.findViewById(R.id.rv_on_going_orders);
        tvNoOnGoingOrders = view.findViewById(R.id.tv_no_on_going_orders);

        rvOnGoingOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample Data for On Going Orders
        onGoingOrderList = new ArrayList<>();
        onGoingOrderList.add(new OrderItem(
                "Cappuccino",
                "#5795689",
                "04:30 PM, Today",
                "3 Items",
                "$13.00",
                "On the way",
                R.drawable.on_going, // You need to add this drawable
                true
        ));
        onGoingOrderList.add(new OrderItem(
                "Americano",
                "#5795690",
                "05:00 PM, Today",
                "2 Items",
                "$8.00",
                "Preparing",
                R.drawable.on_going, // You need to add this drawable
                true
        ));
        // Add more ongoing orders here if needed

        orderAdapter = new OrderAdapter(onGoingOrderList);
        rvOnGoingOrders.setAdapter(orderAdapter);

        orderAdapter.setOnItemClickListener(item -> {
            // Handle Track Order button click
            Toast.makeText(getContext(), "Tracking order: " + item.getOrderId(), Toast.LENGTH_SHORT).show();
            // You can start a new activity for order tracking here
        });

        // Show/hide "No ongoing orders" message
        if (onGoingOrderList.isEmpty()) {
            tvNoOnGoingOrders.setVisibility(View.VISIBLE);
            rvOnGoingOrders.setVisibility(View.GONE);
        } else {
            tvNoOnGoingOrders.setVisibility(View.GONE);
            rvOnGoingOrders.setVisibility(View.VISIBLE);
        }

        return view;
    }
}