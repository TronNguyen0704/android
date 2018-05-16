package com.fresher.tronnv.research.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.ui.LyricsFragment;

public class LyricActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        //Create LyricsFrament
        LyricsFragment lyricsFragment = new LyricsFragment();
        //Add set data
        lyricsFragment.setMusicLyrics(DataManager.musicLyrics);
        //Get data from Intent
        int index = getIntent().getIntExtra("Index",0);
        //Set Index
        lyricsFragment.setmIndex(index);
        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.layout_lyric_container, lyricsFragment)
                .commit();


    }
}
