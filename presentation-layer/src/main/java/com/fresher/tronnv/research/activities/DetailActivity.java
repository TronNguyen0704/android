package com.fresher.tronnv.research.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.fresher.tronnv.research.GlideHelper;
import com.fresher.tronnv.research.R;

public class DetailActivity extends AppCompatActivity {
    ImageView mSmallPhoto;
    ImageView mBigPhoto;
    ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mSmallPhoto = findViewById(R.id.img_small_photo);
        mBigPhoto = findViewById(R.id.img_big);
        if (getIntent() != null) {
            String url = getIntent().getStringExtra("url");
            GlideHelper.load(url, false, mSmallPhoto);
            GlideHelper.requestBuilderForHeader(url, mBigPhoto).into(mBigPhoto);
        }
    }
}
