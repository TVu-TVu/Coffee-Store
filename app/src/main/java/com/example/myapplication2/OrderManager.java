package com.example.myapplication2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.util.Log;

public class OrderManager {
    private static OrderManager instance;
    private List<Order> ongoingOrders;
    private List<Order> historyOrders;
    private Context context; // For SharedPreferences if you persist data
    private UserManager userManager;

    // Listener interface for UI updates
    public interface OrderUpdateListener {
        void onOrdersUpdated();
    }

    private List<OrderUpdateListener> listeners = new ArrayList<>();

    private OrderManager(Context context) {
        this.context = context.getApplicationContext();
        this.userManager = new UserManager(this.context);
        ongoingOrders = new ArrayList<>(); // Load from SharedPreferences if implemented
        historyOrders = new ArrayList<>(); // Load from SharedPreferences if implemented
    }

    public static synchronized OrderManager getInstance(Context context) {
        if (instance == null) {
            instance = new OrderManager(context);
        }
        return instance;
    }

    public void addListener(OrderUpdateListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(OrderUpdateListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (OrderUpdateListener listener : listeners) {
            listener.onOrdersUpdated();
        }
    }

    public List<Order> getOngoingOrders() {
        return new ArrayList<>(ongoingOrders); // Return a copy
    }

    public List<Order> getHistoryOrders() {
        return new ArrayList<>(historyOrders); // Return a copy
    }

    public void placeNewOrder(List<CartItem> cartItems, double totalPrice) {
        if (cartItems == null || cartItems.isEmpty()) return;

        Order newOrder = new Order(new ArrayList<>(cartItems), totalPrice); // Create order with a copy of items
        ongoingOrders.add(0, newOrder); // Add to the beginning of the list
        Log.d("OrderManager", "Order placed: " + newOrder.getOrderId() + " Status: " + newOrder.getStatus());
        startOrderStatusTimers(newOrder);
        notifyListeners();
        // Save to SharedPreferences if implemented
    }

    private void startOrderStatusTimers(Order order) {
        Handler handler = new Handler(Looper.getMainLooper());

        // Timer 1: Preparing -> On the way (30 seconds)
        handler.postDelayed(() -> {
            if (order.getStatus().equals("Preparing")) { // Check if not cancelled
                order.setStatus("On the way");
                Log.d("OrderManager", "Order " + order.getOrderId() + " Status: " + order.getStatus());
                notifyListeners();
                // Save to SharedPreferences

                // Timer 2: On the way -> Delivered (another 30 seconds)
                handler.postDelayed(() -> {
                    if (order.getStatus().equals("On the way")) { // Check if not cancelled
                        moveToHistory(order, "Delivered");
                        Log.d("OrderManager", "Order " + order.getOrderId() + " Status: Delivered and moved to history");
                    }
                }, 30 * 1000L); // 30 seconds
            }
        }, 30 * 1000L); // 30 seconds
    }

    public void cancelOrder(String orderId) {
        Order orderToCancel = findOrderInOngoing(orderId);
        if (orderToCancel != null) {
            moveToHistory(orderToCancel, "Cancelled");
            Log.d("OrderManager", "Order " + orderToCancel.getOrderId() + " Status: Cancelled and moved to history");
        }
    }

    private void moveToHistory(Order order, String finalStatus) {
        if (order == null) {
            Log.e("OrderManager", "Attempted to move a null order to history.");
            return;
        }
        Log.d("OrderManager", "Moving order " + order.getOrderId() + " to history with status: " + finalStatus);

        order.setStatus(finalStatus);
        boolean removed = ongoingOrders.remove(order);
        if (!removed) {
            Log.w("OrderManager", "Order " + order.getOrderId() + " was not found in ongoingOrders to remove.");
            // Decide if you want to proceed if it wasn't in ongoing.
            // It might already be in history if moveToHistory is called multiple times by mistake.
            // For now, we'll add to history regardless, but log it.
        }
        // Prevent duplicates in history if it might be called multiple times for the same order
        if (!historyOrders.contains(order)) {
            historyOrders.add(0, order); // Add to the beginning of history
        } else {
            Log.w("OrderManager", "Order " + order.getOrderId() + " is already in history. Status updated to " + finalStatus);
        }


        if ("Delivered".equals(finalStatus)) {
            Log.i("OrderManager", "Processing DELIVERED order: " + order.getOrderId());
            int pointsForThisOrder = 20; // Default points for a delivered order
            userManager.addPoints(pointsForThisOrder);
            userManager.addStamp();
            Log.d("OrderManager", "Awarded " + pointsForThisOrder + " points and 1 stamp for order: " + order.getOrderId());

            // --- Logic from your processDeliveredOrder ---
            List<RewardHistoryItem> historyEntriesForThisOrder = new ArrayList<>();
            String currentDate = UserManager.getCurrentDateString(); // Make sure this static method exists in UserManager
            List<CartItem> deliveredItems = order.getItems(); // Assuming Order class has getItems() method

            if (deliveredItems != null && !deliveredItems.isEmpty()) {
                if (deliveredItems.size() == 1) {
                    CartItem item = deliveredItems.get(0);
                    // Ensure CartItem has getName() and getImageResId()
                    historyEntriesForThisOrder.add(new RewardHistoryItem(
                            item.getName(),
                            currentDate,
                            pointsForThisOrder, // Points for this single item order
                            item.getImageResId()
                    ));
                } else {
                    // For multiple items, add a summary entry with the points
                    // Ensure R.drawable.ic_default_reward exists
                    historyEntriesForThisOrder.add(new RewardHistoryItem(
                            "Order Delivered (" + deliveredItems.size() + " items)",
                            currentDate,
                            pointsForThisOrder,
                            R.drawable.coffee_cup_2
                    ));
                    // Then list each item individually
                    for (CartItem item : deliveredItems) {
                        historyEntriesForThisOrder.add(new RewardHistoryItem(
                                item.getName(),
                                currentDate,
                                20, // 0 points for individual listing, main points in summary
                                item.getImageResId()
                        ));
                    }
                }
                if (!historyEntriesForThisOrder.isEmpty()) {
                    userManager.addRewardHistoryItems(historyEntriesForThisOrder); // Make sure this method exists in UserManager
                    Log.d("OrderManager", "Added " + historyEntriesForThisOrder.size() + " reward history entries for order: " + order.getOrderId());
                } else {
                    Log.w("OrderManager", "No reward history items generated for delivered order: " + order.getOrderId());
                }
            } else {
                Log.w("OrderManager", "Delivered order " + order.getOrderId() + " has no items. Adding generic reward entry.");
                // Fallback if order has no items but is delivered
                userManager.addRewardHistoryItem(new RewardHistoryItem(
                        "Order Reward",
                        currentDate,
                        pointsForThisOrder,
                        R.drawable.coffee_cup_2
                ));
            }
        }

        notifyListeners();
        // Save to SharedPreferences
    }

    private Order findOrderInOngoing(String orderId) {
        for (Order order : ongoingOrders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }


}
