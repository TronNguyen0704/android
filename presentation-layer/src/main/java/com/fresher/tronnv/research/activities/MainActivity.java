package com.fresher.tronnv.research.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.ui.PlayListFragment;
import com.fresher.tronnv.research.ui.HomeFragment;


public class MainActivity extends AppCompatActivity implements PlayListFragment.OnItemLyricClickListener, HomeFragment.Loadfinish{


    private boolean isChange = false;
    private ProgressBar progressBar;
    private SearchView searchView;
    private HomeFragment homeFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    //HomeFragment homeFragment = new HomeFragment();
                    if(isChange) {
                        searchView.setVisibility(View.VISIBLE);
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame, homeFragment)
                                .commit();
                        isChange = false;
                   }
                    return true;
                }
                case R.id.navigation_my_music: {
                    if(!isChange) {
                        PlayListFragment playListFragment = new PlayListFragment();
                        playListFragment.setSearchView(searchView);
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame, playListFragment)
                                .commit();
                        //searchView.setVisibility(View.GONE);
                        isChange = true;
                    }
                    return true;
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
        progressBar = findViewById(R.id.loading_indicator);
        searchView = findViewById(R.id.search_view);
        searchView.setActivated(true);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();
        homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame, homeFragment)
                .commit();
    }

    //Event when click on a item in playlist
    @Override
    public void onItemSelected(int position, int idSong, String filter) {
        Log.i("Position clicked = " , "" +position);
        Bundle bundle = new Bundle();
        bundle.putInt("Index", position);
        bundle.putInt("ID", idSong);
        bundle.putString("Filter", filter);
        final Intent intent = new Intent(this, LyricActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFinish() {
        progressBar.setVisibility(View.GONE);
    }
}
