package com.fresher.tronnv.research.activities;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.ui.LyricsFragment;
import com.fresher.tronnv.research.ui.MediaPlayerFragment;



public class LyricActivity extends AppCompatActivity implements MediaPlayerFragment.OnSongChange{
    private MediaPlayer mPlayer;
    private DataManager dataManager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        dataManager = new DataManager(getBaseContext());
        toolbar = findViewById(R.id.tool_bar);

        //Create LyricsFrament
        LyricsFragment lyricsFragment = new LyricsFragment();
        //Add set data
        String filter = getIntent().getStringExtra("Filter");
        lyricsFragment.setMusicLyrics(dataManager.getDataFromDatabase(filter));
        //Get data from Intent
        int index = getIntent().getIntExtra("Index",0);
        int ID = getIntent().getIntExtra("ID",0);
        mPlayer = MediaPlayer.create(this,getRawIDByName("mp" +String.valueOf(3100 + ID)));

        //SetupToolBar
        toolbar.setSubtitle(dataManager.getSongById(ID).getAuthor());
        toolbar.setTitle(dataManager.getSongById(ID).getName());
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

        mediaPlayerFragment.setMediaPlayer(mPlayer);
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
        if(mPlayer!= null)
            mPlayer.release();
    }
    public int getRawIDByName(String name){
        return getResources().getIdentifier(name, "raw", this.getPackageName());
    }

    @Override
    public void onNextSong(int id) {
        LyricsFragment lyricsFragment = new LyricsFragment();

        //Add set data
        lyricsFragment.setMusicLyrics(dataManager.getDataFromDatabase("null"));//Utils.musicLyrics);
        toolbar.setSubtitle(dataManager.getSongById(id).getAuthor());
        toolbar.setTitle(dataManager.getSongById(id).getName());
        //Set Index
        lyricsFragment.setmIndex(id - 1);
        lyricsFragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_lyric_container, lyricsFragment)
                .commit();
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
                AudioManager a=(AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                a.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_SAME,AudioManager.FLAG_SHOW_UI| AudioManager.FLAG_PLAY_SOUND);
                return true;
            }
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
