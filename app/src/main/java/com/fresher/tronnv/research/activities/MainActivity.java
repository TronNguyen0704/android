package com.fresher.tronnv.research.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.ui.PlayListFragment;
import com.fresher.tronnv.research.ui.RecordChartFragment;


public class MainActivity extends AppCompatActivity implements PlayListFragment.OnItemLyricClickListener{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    RecordChartFragment recordChartFragment = new RecordChartFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, recordChartFragment)
                            .commit();
                    return true;
                }
                case R.id.navigation_my_music: {
                    PlayListFragment playListFragment = new PlayListFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, playListFragment)
                            .commit();
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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        RecordChartFragment recordChartFragment = new RecordChartFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.frame, recordChartFragment)
                .commit();
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
}
