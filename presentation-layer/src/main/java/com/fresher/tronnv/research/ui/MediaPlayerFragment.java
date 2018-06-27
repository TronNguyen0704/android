package com.fresher.tronnv.research.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.service.NotificationService;

import java.util.concurrent.TimeUnit;

import static android.content.Context.NOTIFICATION_SERVICE;
/**
 * Created by NGUYEN VAN TRON on 05/18/18.
/**
 *This fragment to show media player
 */
public class MediaPlayerFragment extends Fragment{

    //private MediaPlayer mediaPlayer;
    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar;
    private boolean isPause = false;
    private boolean isResume = false;
    private Context context;
    private int ID;
    private OnSongChange onSongChange;
    private boolean isPlaying = true;
    private BroadcastReceiver broadcastReceiver;
    private int currentDuration = 0;
    private int duration = 0;
    private ImageButton playBtn;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSongChange = (OnSongChange) context;
        }
        catch (ClassCastException  e){
            throw new ClassCastException(context.toString() + "must implement OnSongChange");
        }
    }
    public MediaPlayerFragment(){
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                currentDuration = intent.getIntExtra(NotificationService.CURRENTPOSITION,0);
                duration = intent.getIntExtra(NotificationService.DURATION,0);
                int songID = intent.getIntExtra("id",ID);
                if(intent.getIntExtra("play_pause",-1) != -1){
                    if(intent.getIntExtra("play_pause",-1)==1) {
                        playBtn.setImageResource(R.drawable.pause);
                        isPlaying = true;
                    }
                    else{
                        playBtn.setImageResource(R.drawable.play);
                        isPlaying = false;
                    }
                }
                if(ID != songID) {
                    onSongChange.onChangeUI(songID);
                    ID = songID;
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver,new IntentFilter(NotificationService.PROCESSED));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
    boolean flag = false;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_mediaplayer,container,false);
        final SeekBar seekBar = rootView.findViewById(R.id.seekbar);
        final TextView timeCount = rootView.findViewById(R.id.tv_time_count);
        final TextView totalTime = rootView.findViewById(R.id.tv_total_time);
        int timeReTotal = 0;
        seekBar.setMax(duration);
        long min = TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal);
        long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeReTotal) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal));
        totalTime.setText((min>=10)? String.valueOf(min) : "0"+ min +":" +( (sec>=10)? String.valueOf(sec) : "0"+ sec));
        //Run thread update UI of seek bar
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                if(!isPause){
                    updateSeekBar(seekBar,timeCount,totalTime);
                    mSeekbarUpdateHandler.postDelayed(this, 50);
                }
            }
        };
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
//                        Intent intent = new Intent(NotificationService.RESTART);
//                        intent.putExtra("progress", progress);
//                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
//                        localBroadcastManager.sendBroadcast(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        playBtn = rootView.findViewById(R.id.btn_play);
        playBtn.setImageResource(R.drawable.pause);
        final ImageButton preBtn = rootView.findViewById(R.id.btn_pre);
        preBtn.setImageResource(R.drawable.pre);
        ImageButton nextBtn = rootView.findViewById(R.id.btn_next);
        nextBtn.setImageResource(R.drawable.next);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    onSongChange.onPauseMedia();
                    isPlaying = false;
                    playBtn.setImageResource(R.drawable.play);
                }
                else{
                    onSongChange.onPlay();
                    isPlaying = true;
                    playBtn.setImageResource(R.drawable.pause);
                }
            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume) {
                    playBtn.setImageResource(R.drawable.pause);
                }
                if(ID == 1){
                    ID = 11;
                }
                ID--;
                //sent data to LyricFragment
                onSongChange.onPreviousSong(ID);
                mUpdateSeekbar = new Runnable() {
                    @Override
                    public void run() {
                        if(!isPause){
                            updateSeekBar(seekBar,timeCount,totalTime);
                            mSeekbarUpdateHandler.postDelayed(this, 50);
                        }
                    }
                };

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume) {
                    playBtn.setImageResource(R.drawable.pause);
                }
                if(ID == 10){
                    ID = 0;
                }
                ID++;
                //sent data to LyricFragment
                onSongChange.onNextSong(ID);
                mUpdateSeekbar = new Runnable() {
                    @Override
                    public void run() {
                        if(!isPause){
                            updateSeekBar(seekBar,timeCount,totalTime);
                            mSeekbarUpdateHandler.postDelayed(this, 50);
                        }
                    }
                };

            }
        });
        return rootView;
    }
    private void updateSeekBar(SeekBar seekBar, TextView timeCount, TextView totalTime){
        seekBar.setMax(duration);
        int timeRemaining = currentDuration;
        seekBar.setProgress(timeRemaining);
        long min = TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining);
        long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining));
        timeCount.setText((min>=10)? String.valueOf(min) : "0"+ min +":" +( (sec>=10)? String.valueOf(sec) : "0"+ sec));
        long min2 = TimeUnit.MILLISECONDS.toMinutes((long) duration);
        long sec2 = TimeUnit.MILLISECONDS.toSeconds((long) duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) duration));
        totalTime.setText((min>=10)? String.valueOf(min2) : "0"+ min2 +":" +( (sec2>=10)? String.valueOf(sec2) : "0"+ sec2));
    }
    public void setMediaPlayer(MediaPlayer mediaPlayer) {

    }
    public void setContext(Context context){
        this.context = context;
    }
    public void setID(int id) {
        this.ID = id;
    }
    private int getRawIDByName(String name){
        return getResources().getIdentifier(name, "raw", context.getPackageName());
    }

    //Interface to transfer data between two fragment
    public interface OnSongChange {
        void onNextSong(int id);
        void onPauseMedia();
        void onPlay();
        void onPreviousSong(int id);
        void onChangeUI(int songID);
    }
}
