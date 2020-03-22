package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class RestaurantInfoActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        Log.d(TAG, "OnCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPaper with the sections adapter
        mViewPager = findViewById(R.id.view_pager);
        setupViewPaper(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPaper(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new RestaurantDetailTabFrag(), "Detail");
        adapter.addFragment(new RestaurantMapTabFrag(), "Map");
        adapter.addFragment(new RestaurantMenuTabFrag(), "Menu");
        adapter.addFragment(new RestaurantGalleryTabFrag(), "Gallery");
        viewPager.setAdapter(adapter);
    }
}
