package com.fresher.tronnv.research.activities;

import android.content.Intent;
import android.os.AsyncTask;
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


public class MainActivity extends AppCompatActivity implements PlayListFragment.OnItemLyricClickListener{


    private boolean isChange = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    //HomeFragment recordChartFragment = new HomeFragment();
                    if(isChange) {
                        searchView.setVisibility(View.VISIBLE);
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame, recordChartFragment)
                                .commit();
                        isChange = false;
                   }
                    return true;
                }
                case R.id.navigation_my_music: {
                    if(!isChange) {
                        PlayListFragment playListFragment = new PlayListFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame, playListFragment)
                                .commit();
                        searchView.setVisibility(View.GONE);
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
    private ProgressBar progressBar;
    private SearchView searchView;
    private HomeFragment recordChartFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //config Toolbar


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recordChartFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        progressBar = findViewById(R.id.loading_indicator);
        searchView = findViewById(R.id.search_view);
        searchView.setActivated(true);
        searchView.onActionViewExpanded();
        searchView.setIconified(true);
        searchView.setFocusable(false);
        searchView.clearFocus();
        fragmentManager.beginTransaction()
                .add(R.id.frame, recordChartFragment)
                .commit();
        LoadingAsyntask loadingAsyntask = new LoadingAsyntask();
        loadingAsyntask.execute();
    }

    //Event when click on a item in playlist
    @Override
    public void onItemSelected(int position, int idSong, String filter) {
        Log.i("Position clicked = " , "" +position);
        Bundle b = new Bundle();
        b.putInt("Index", position);
        b.putInt("ID", idSong);
        b.putString("Filter", filter);
        final Intent intent = new Intent(this, LyricActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private class LoadingAsyntask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            while(recordChartFragment.isLoading()){

            }
            publishProgress(voids);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
