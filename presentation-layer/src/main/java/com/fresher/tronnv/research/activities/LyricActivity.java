package com.fresher.tronnv.research.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.service.MediaPlayerService;
import com.fresher.tronnv.research.ui.LyricsFragment;
import com.fresher.tronnv.research.ui.MediaPlayerFragment;

import java.util.ArrayList;
import java.util.List;


public class LyricActivity extends AppCompatActivity implements MediaPlayerFragment.OnSongChange, LyricsFragment.LoadFinish {
    private Toolbar mToolbar;
    private ApplicationPresenter mApplicationPresenter;
    private int mId;
    private Intent mServiceIntent;
    private LyricsFragment mLyricsFragment;
    List<MusicLyric> mMusicLyrics;
    private LocalBroadcastManager mBroadcastReceiver;
    private boolean mServiceIsStarted = false;
    private boolean mIsPowerOff = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        mBroadcastReceiver = LocalBroadcastManager.getInstance(this);
        mApplicationPresenter = new ApplicationPresenterImpl(getBaseContext());
        mApplicationPresenter.loadMusicData();
        mToolbar = findViewById(R.id.tool_bar);
        mMusicLyrics = new ArrayList<>();
        //Create LyricsFrament
        mLyricsFragment = new LyricsFragment();
        //Add set data
        String filter = getIntent().getStringExtra("Filter");
        //Get data from Intent
        int index = getIntent().getIntExtra("Index", -1);
        int id = getIntent().getIntExtra("ID", 0);
        mId = id;
        boolean mStart = getIntent().getBooleanExtra("start", false);
        //SetupToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Set Index
        if (mStart && (MediaPlayerService.sServiceIdSong != -1)) {
            MediaPlayerService.sServiceState = false;
            mServiceIntent = new Intent(LyricActivity.this, MediaPlayerService.class);
            stopService(mServiceIntent);
            mLyricsFragment.setmIndex(index);
        } else {
            mLyricsFragment.setmIndex((MediaPlayerService.sServiceIdSong != -1) ? MediaPlayerService.sServiceIdSong - 1 : index);
        }
        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.layout_lyric_container, mLyricsFragment)
                .commit();
        // Create and display the media player
        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setContext(this);
        mediaPlayerFragment.setmId(id);

        fragmentManager.beginTransaction()
                .add(R.id.layout_media_player_container, mediaPlayerFragment)
                .commit();
    }


    @Override
    protected void onStop() {
        mIsPowerOff = true;
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsPowerOff && MediaPlayerService.sServiceIdSong > 0) {
            onChangeUI(MediaPlayerService.sServiceIdSong);
            MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
            mediaPlayerFragment.setContext(this);
            mediaPlayerFragment.setmId(MediaPlayerService.sServiceIdSong);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_media_player_container, mediaPlayerFragment)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!MediaPlayerService.sServiceState)
            stopService(mServiceIntent);
    }

    public int getRawidByName(String name) {
        return getResources().getIdentifier(name, "raw", this.getPackageName());
    }

    @Override
    public void onChangeUI(int songid) {
        mLyricsFragment = new LyricsFragment();
        this.mId = songid;
        mLyricsFragment.setmIndex(songid - 1);
        mLyricsFragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_lyric_container, mLyricsFragment)
                .commit();
    }

    @Override
    public void onNextSong(int id) {
//        if( !MediaPlayerService.sServiceState) {
//            startService();
//        }
        if (!isMyServiceRunning(MediaPlayerService.class)) {
            startService();
        }
        mLyricsFragment = new LyricsFragment();
        this.mId = id;
        mLyricsFragment.setmIndex(id - 1);

        mLyricsFragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_lyric_container, mLyricsFragment)
                .commit();
        Intent intent = new Intent(MediaPlayerService.RESTART);
        intent.putExtra("restart", "next");
        mBroadcastReceiver.sendBroadcast(intent);
    }

    @Override
    public void onPreviousSong(int id) {
//        if (!MediaPlayerService.sServiceState) {
//            startService();
//        }
        if (!isMyServiceRunning(MediaPlayerService.class)) {
            startService();
        }
        this.mId = id;
        //send broadcast to restart service
        Intent intent = new Intent(MediaPlayerService.RESTART);
        intent.putExtra("restart", "previous");
        mBroadcastReceiver.sendBroadcast(intent);

        mLyricsFragment = new LyricsFragment();
        mLyricsFragment.setmIndex(id - 1);
        mLyricsFragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_lyric_container, mLyricsFragment)
                .commit();
    }

    @Override
    public void onPauseMedia() {
        Intent intent = new Intent(MediaPlayerService.RESTART);
        intent.putExtra("restart", "pause");
        mBroadcastReceiver.sendBroadcast(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPlay(int curr) {
//        if(!MediaPlayerService.sServiceState) {
//            startService();
//        }
        if (!isMyServiceRunning(MediaPlayerService.class)) {
            startService();
        }
        Intent intent = new Intent(MediaPlayerService.RESTART);
        intent.putExtra("restart", "play");
        if (curr > 0) {
            intent.putExtra("currSaved", curr);
        }
        mBroadcastReceiver.sendBroadcast(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);
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
                AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_PLAY_SOUND);
                return true;
            }
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void startService() {
        mServiceIsStarted = true;
        mServiceIntent = new Intent(LyricActivity.this, MediaPlayerService.class);
        mServiceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        mServiceIntent.putExtra("id", mId);
        mServiceIntent.putStringArrayListExtra("names", getListString(1));
        mServiceIntent.putStringArrayListExtra("authors", getListString(2));
        mServiceIntent.putStringArrayListExtra("avatars", getListString(3));
        startService(mServiceIntent);
    }

    private ArrayList<String> getListString(int type) {
        int size = mLyricsFragment.getmMusicLyrics().size();
        if (size > 0 && mMusicLyrics.size() == 0)
            mMusicLyrics.addAll(mLyricsFragment.getmMusicLyrics());
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < mMusicLyrics.size(); i++) {
            if (type == 1)
                temp.add(mMusicLyrics.get(i).getName());
            else if (type == 2)
                temp.add(mMusicLyrics.get(i).getAuthor());
            else if (type == 3)
                temp.add(mMusicLyrics.get(i).getAvatar());
        }
        return temp;
    }

    @Override
    public void onFinish(String name, String author) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setSubtitle(author);
        mToolbar.setTitle(name);

        if (!mServiceIsStarted && !MediaPlayerService.sServiceState) {
            startService();
        }
    }

    @Override
    public void onBackPressed() {
        //check if an activity is the last one in the activity stack
        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);
        if (taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            super.onBackPressed();
        }

    }
}
