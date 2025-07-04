// MyOrderActivity.java
package com.example.myapplication2; // Ensure this matches your package name

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyOrderActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageButton btnBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        btnBack = findViewById(R.id.btn_back);
        tvTitle = findViewById(R.id.tv_my_order_title);

        // Set title text (optional, if not set directly in XML string)
        tvTitle.setText(R.string.my_order_title);

        // Setup ViewPager2 with FragmentStateAdapter
        setupViewPager(viewPager);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(R.string.tab_on_going);
                    } else {
                        tab.setText(R.string.tab_history);
                    }
                }).attach();

        // Handle back button click
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void setupViewPager(ViewPager2 viewPager) {
        MyOrderPagerAdapter adapter = new MyOrderPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);
    }

    private static class MyOrderPagerAdapter extends FragmentStateAdapter {

        public MyOrderPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new OnGoingOrdersFragment();
            } else {
                return new HistoryOrdersFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2; // Number of tabs: On Going and History
        }
    }
}