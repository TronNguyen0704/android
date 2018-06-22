package com.fresher.tronnv.research.activities;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.service.MediaPlayerService;
import com.fresher.tronnv.research.service.NotificationService;
import com.fresher.tronnv.research.ui.LyricsFragment;
import com.fresher.tronnv.research.ui.MediaPlayerFragment;

import java.util.ArrayList;
import java.util.List;


public class LyricActivity extends AppCompatActivity implements MediaPlayerFragment.OnSongChange, LyricsFragment.LoadFinish{
    private Toolbar toolbar;
    private ApplicationPresenter applicationPresenter;
    private int id;
    private Intent serviceIntent;
    private LyricsFragment lyricsFragment;
    List<MusicLyric> musicLyrics ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        applicationPresenter = new ApplicationPresenterImpl(getBaseContext());
        applicationPresenter.loadMusicData();
        toolbar = findViewById(R.id.tool_bar);
        musicLyrics = new ArrayList<>();
        //Create LyricsFrament
        lyricsFragment = new LyricsFragment();
        //Add set data
        String filter = getIntent().getStringExtra("Filter");
        //Get data from Intent
        int index = getIntent().getIntExtra("Index",0);
        int ID = getIntent().getIntExtra("ID",0);
        id = ID;

        //SetupToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Set Index
        lyricsFragment.setmIndex(index);
        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.layout_lyric_container, lyricsFragment)
                .commit();
        // Create and display the media player
        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setContext(this);
        mediaPlayerFragment.setID(ID);

        fragmentManager.beginTransaction()
                .add(R.id.layout_media_player_container, mediaPlayerFragment)
                .commit();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //mPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(mReceiver);
    }
    public int getRawIDByName(String name){
        return getResources().getIdentifier(name, "raw", this.getPackageName());
    }

    @Override
    public void onNextSong(int id) {
        lyricsFragment = new LyricsFragment();
        //Add set data
        //Set Index
        this.id = id;
        lyricsFragment.setmIndex(id - 1);
        lyricsFragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_lyric_container, lyricsFragment)
                .commit();
        stopService(serviceIntent);
        startService();
    }




    @Override
    public void onPauseMedia() {
        stopService(serviceIntent);
    }

    @Override
    public void onPlay() {
        startService();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar,menu);
        // Define the listener
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        };

        // Get the MenuItem for the action item
        MenuItem actionMenuItem = menu.findItem(R.id.action_volume);

        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(actionMenuItem, expandListener);

        // Any other things you have to do when creating the options menu...

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_back: {
//                onBackPressed();
//                return true;
//
//            }
            case R.id.action_volume: {
                AudioManager audioManager =(AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_SAME,AudioManager.FLAG_SHOW_UI| AudioManager.FLAG_PLAY_SOUND);
                return true;
            }
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void startService() {
        serviceIntent = new Intent(LyricActivity.this, NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        serviceIntent.putExtra("id",id);
        serviceIntent.putStringArrayListExtra("names",getListString(1));
        serviceIntent.putStringArrayListExtra("authors",getListString(2));
        serviceIntent.putStringArrayListExtra("avatars",getListString(3));
        startService(serviceIntent);
    }
    private ArrayList<String> getListString(int type){
        int size = lyricsFragment.getMusicLyrics().size();
        if(size>0 && musicLyrics.size() ==0)
            musicLyrics.addAll(lyricsFragment.getMusicLyrics());
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0;i< musicLyrics.size();i++){
            if(type == 1)
                temp.add(musicLyrics.get(i).getName());
            else if(type == 2)
                temp.add(musicLyrics.get(i).getAuthor());
            else if(type == 3)
                temp.add(musicLyrics.get(i).getAvatar());
        }
        return temp;
    }
    @Override
    public void onFinish(String name, String author) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setSubtitle(author);
        toolbar.setTitle(name);
        startService();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
