package com.fresher.tronnv.research.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.ui.PlayListFragment;
import com.fresher.tronnv.research.ui.HomeFragment;
import com.fresher.tronnv.research.ui.adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements PlayListFragment.OnItemLyricClickListener, HomeFragment.Loadfinish {

    private ProgressBar mProgressBar;
    private SearchView mSearchView;
    private HomeFragment mHomeFragment;
    private PlayListFragment mPlayListFragment;
    private ViewPager mViewPager;
    MenuItem mPrevMenuItem;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    mSearchView.setVisibility(View.VISIBLE);
                    mViewPager.setCurrentItem(0);
                    break;
                }
                case R.id.navigation_my_music: {
                    mViewPager.setCurrentItem(1);
                    break;
                }
                case R.id.navigation_more_setting:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //config UI
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mProgressBar = findViewById(R.id.loading_indicator);
        mSearchView = findViewById(R.id.search_view);
        mViewPager = findViewById(R.id.viewpager_main);
        mSearchView.setActivated(true);
        mSearchView.onActionViewExpanded();
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();
        mHomeFragment = new HomeFragment();
        mPlayListFragment = new PlayListFragment();
        mPlayListFragment.setmSearchView(mSearchView);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                }
                else
                {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(mViewPager);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mHomeFragment);
        adapter.addFragment(mPlayListFragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    private void hideViews() {
        mSearchView.animate().translationY(-mSearchView.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        mSearchView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    //Event when click on a item in playlist
    @Override
    public void onItemSelected(int position, int idSong, String filter) {
        Log.i("Position clicked = ", "" + position);
        Bundle bundle = new Bundle();
        bundle.putInt("Index", position);
        bundle.putInt("ID", idSong);
        bundle.putString("Filter", filter);
        bundle.putBoolean("start", true);
        final Intent intent = new Intent(this, LyricActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFinish() {
        mProgressBar.setVisibility(View.GONE);
    }
}
