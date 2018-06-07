package com.fresher.tronnv.research.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.ui.HomeFragment;

public class RecordChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_chard);

        HomeFragment recordChartFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.layout_record_chart_container, recordChartFragment)
                .commit();
    }
}
