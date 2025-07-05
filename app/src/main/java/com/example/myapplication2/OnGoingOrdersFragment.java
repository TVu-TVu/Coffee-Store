package com.example.myapplication2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OnGoingOrdersFragment extends Fragment implements OrderManager.OrderUpdateListener {

    private RecyclerView rvOnGoingOrders;
    private OngoingOrdersAdapter adapter;
    private OrderManager orderManager;
    private TextView tvNoOnGoingOrders;
    // private OrderAdapter orderAdapter;
    //private List<OrderItem> onGoingOrderList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderManager = OrderManager.getInstance(requireContext());
        orderManager.addListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_going_orders, container, false);

        rvOnGoingOrders = view.findViewById(R.id.rv_on_going_orders);
        tvNoOnGoingOrders = view.findViewById(R.id.tv_no_on_going_orders);

        rvOnGoingOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new OngoingOrdersAdapter(new ArrayList<>(), order -> {
            orderManager.cancelOrder(order.getOrderId());
        });
        rvOnGoingOrders.setAdapter(adapter);

        loadOngoingOrders();
        return view;

    }

    private void loadOngoingOrders() {
        List<Order> ongoingOrders = orderManager.getOngoingOrders();
        adapter.updateOrders(ongoingOrders);
        if (orderManager == null || adapter == null || tvNoOnGoingOrders == null) return;

        if (ongoingOrders.isEmpty()) {
            tvNoOnGoingOrders.setVisibility(View.VISIBLE);
            rvOnGoingOrders.setVisibility(View.GONE);
        } else {
            tvNoOnGoingOrders.setVisibility(View.GONE);
            rvOnGoingOrders.setVisibility(View.VISIBLE);
            adapter.updateOrders(ongoingOrders);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadOngoingOrders();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        orderManager.removeListener(this);
    }

    @Override
    public void onOrdersUpdated() {
        if (isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                loadOngoingOrders();
            });

        }
    }
}


/*
       // onGoingOrderList = new ArrayList<>();
        //onGoingOrderList.add(new OrderItem(
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
}*/
