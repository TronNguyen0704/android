package com.fresher.tronnv.research.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.ui.LyricsFragment;
import com.fresher.tronnv.research.ui.MediaPlayerFragment;

public class LyricActivity extends AppCompatActivity implements MediaPlayerFragment.OnSongChange{
    MediaPlayer mPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        //Create LyricsFrament
        LyricsFragment lyricsFragment = new LyricsFragment();
        //Add set data
        lyricsFragment.setMusicLyrics(Utils.musicLyricsShow);
        //Get data from Intent
        int index = getIntent().getIntExtra("Index",0);
        int ID = getIntent().getIntExtra("ID",0);
        mPlayer = MediaPlayer.create(this,getRawIDByName("mp" +String.valueOf(3100 + ID)));

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
        lyricsFragment.setMusicLyrics(Utils.musicLyrics);
        //Set Index
        lyricsFragment.setmIndex(id - 1);
        lyricsFragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_lyric_container, lyricsFragment)
                .commit();
    }
}
