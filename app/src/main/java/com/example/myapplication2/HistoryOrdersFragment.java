package com.example.myapplication2;

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

public class HistoryOrdersFragment extends Fragment implements OrderManager.OrderUpdateListener {

    private RecyclerView rvHistoryOrders;
    private HistoryOrdersAdapter adapter;
    private OrderManager orderManager;
    private TextView tvNoHistoryOrders;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderManager = OrderManager.getInstance(requireContext());
        orderManager.addListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_orders, container, false); // Create this layout

        rvHistoryOrders = view.findViewById(R.id.rv_history_orders);
        tvNoHistoryOrders = view.findViewById(R.id.tv_no_history_orders);
        rvHistoryOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryOrdersAdapter(new ArrayList<>());
        rvHistoryOrders.setAdapter(adapter);
        loadHistoryOrders();
        return view;
    }


    private void loadHistoryOrders() {
        if (orderManager == null || adapter == null || tvNoHistoryOrders == null) return;

        List<Order> historyOrders = orderManager.getHistoryOrders();
        if (historyOrders.isEmpty()) {
            tvNoHistoryOrders.setVisibility(View.VISIBLE);
            rvHistoryOrders.setVisibility(View.GONE);
        } else {
            tvNoHistoryOrders.setVisibility(View.GONE);
            rvHistoryOrders.setVisibility(View.VISIBLE);
            adapter.updateOrders(historyOrders);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadHistoryOrders();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderManager != null) {
            orderManager.removeListener(this);
        }
    }

    @Override
    public void onOrdersUpdated() {
        if (isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(this::loadHistoryOrders);
        }
    }


}