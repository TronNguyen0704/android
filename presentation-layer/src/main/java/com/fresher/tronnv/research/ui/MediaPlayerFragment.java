package com.fresher.tronnv.research.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.fresher.tronnv.research.R;

import java.util.concurrent.TimeUnit;

import static android.content.Context.NOTIFICATION_SERVICE;
/**
 * Created by NGUYEN VAN TRON on 05/18/18.
 */
/**
 *This fragment to show media player
 */
public class MediaPlayerFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar;
    private boolean isPause = false;
    private boolean isResume = false;
    private Context context;
    private int ID;
    private OnSongChange onSongChange;
    private NotificationManager mNotificationManager;
    private void showNotification() {
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        String CHANNEL_ID = "my_channel_01";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(false);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, (int)System.currentTimeMillis(), new Intent("Kill_app"), PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle(getString(R.string.title))
                .setContentText(getString(R.string.content))
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_mp3)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mNotificationManager.notify(0, builder.build());
    }
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

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mediaPlayer != null && !isResume) {
            mediaPlayer.start();
            mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if(mediaPlayer != null)
//            mediaPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mediaPlayer != null)
            mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!= null)
            mediaPlayer.reset();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_mediaplayer,container,false);
        final SeekBar seekBar = rootView.findViewById(R.id.seekbar);
        final TextView timeCount = rootView.findViewById(R.id.tv_time_count);
        final TextView totalTime = rootView.findViewById(R.id.tv_total_time);
        int timeReTotal = 0;
        if(mediaPlayer!= null) {
            timeReTotal = mediaPlayer.getDuration();
            seekBar.setMax(timeReTotal);
        }
        long min = TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal);
        long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeReTotal) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal));
        totalTime.setText((min>=10)? String.valueOf(min) : "0"+ min +":" +( (sec>=10)? String.valueOf(sec) : "0"+ sec));
        //Run thread update UI of seek bar
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!= null && !isPause) {
                    try {
                        int timeRemaining = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(timeRemaining);
                        long min = TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining);
                        long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining));
                        timeCount.setText((min>=10)? String.valueOf(min) : "0"+ min +":" +( (sec>=10)? String.valueOf(sec) : "0"+ sec));
                        mSeekbarUpdateHandler.postDelayed(this, 50);
                    }
                    catch (IllegalStateException e){
                        System.out.print(e.toString());
                    }

                }
            }
        };
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final ImageButton playBtn = rootView.findViewById(R.id.btn_play);
        playBtn.setImageResource(R.drawable.pause);
        final ImageButton preBtn = rootView.findViewById(R.id.btn_pre);
        preBtn.setImageResource(R.drawable.pre);
        ImageButton nextBtn = rootView.findViewById(R.id.btn_next);
        nextBtn.setImageResource(R.drawable.next);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    onSongChange.onPauseMedia();
                    isResume = true;
                    playBtn.setImageResource(R.drawable.play);
                }
                else{
                    onSongChange.onPlay();
                    mediaPlayer.start();
                    isResume = false;
                    playBtn.setImageResource(R.drawable.pause);
                }
            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isResume) {
                    playBtn.setImageResource(R.drawable.pause);
                    mediaPlayer.release();
                }
                if(ID == 1){
                    ID = 11;
                }
                ID--;
                //sent data to LyricFragment
                onSongChange.onNextSong(ID);
                mediaPlayer = MediaPlayer.create(context,getRawIDByName("mp" +String.valueOf(3100 + ID)));
                mUpdateSeekbar = new Runnable() {
                    @Override
                    public void run() {
                        if(mediaPlayer!= null && !isPause) {
                            if(mediaPlayer.isPlaying()) {
                                int timeRemaining = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(timeRemaining);
                                long min = TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining);
                                long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining));
                                timeCount.setText((min >= 10) ? String.valueOf(min) : "0" + min + ":" + ((sec >= 10) ? String.valueOf(sec) : "0" + sec));
                                mSeekbarUpdateHandler.postDelayed(this, 50);
                            }
                        }
                    }
                };
                if(!isResume) {
                    mediaPlayer.start();
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                }

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume) {
                    playBtn.setImageResource(R.drawable.pause);
                    mediaPlayer.release();
                }
                if(ID == 10){
                    ID = 0;
                }
                ID++;
                //sent data to LyricFragment
                onSongChange.onNextSong(ID);
                mediaPlayer = MediaPlayer.create(context,getRawIDByName("mp" +String.valueOf(3100 + ID)));
                mUpdateSeekbar = new Runnable() {
                    @Override
                    public void run() {
                        if(mediaPlayer!= null && !isPause) {
                            if(mediaPlayer.isPlaying()) {
                                int timeRemaining = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(timeRemaining);
                                long min = TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining);
                                long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining));
                                timeCount.setText((min >= 10) ? String.valueOf(min) : "0" + min + ":" + ((sec >= 10) ? String.valueOf(sec) : "0" + sec));
                                mSeekbarUpdateHandler.postDelayed(this, 50);
                            }
                        }
                    }
                };
                if(!isResume) {
                    mediaPlayer.start();
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                }

            }
        });
        return rootView;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
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
    }
}
