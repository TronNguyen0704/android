package com.fresher.tronnv.research.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fresher.tronnv.research.R;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
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
    private Context context;
    private int ID;
    private OnSongChange onSongChange;

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
        if(mediaPlayer != null) {
            mediaPlayer.start();

            mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //mediaPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mediaPlayer.pause();
        isPause = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!= null)
            mediaPlayer.release();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_mediaplayer,container,false);
        final SeekBar seekBar = rootView.findViewById(R.id.seekbar);
        final TextView timeCount = rootView.findViewById(R.id.txt_time_count);
        final TextView totalTime = rootView.findViewById(R.id.txt_total_time);
        int timeReTotal = 0;
        if(mediaPlayer!= null) {
            timeReTotal = mediaPlayer.getDuration();
            seekBar.setMax(timeReTotal);
        }
        totalTime.setText(String.format(" %d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal), TimeUnit.MILLISECONDS.toSeconds((long) timeReTotal) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal))));
        //Run thread update UI of seek bar
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!= null && !isPause) {

                    int timeRemaining = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(timeRemaining);
                    timeCount.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
                    mSeekbarUpdateHandler.postDelayed(this, 50);
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
        ImageButton preBtn = rootView.findViewById(R.id.btn_pre);
        ImageButton nextBtn = rootView.findViewById(R.id.btn_next);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playBtn.setImageResource(R.drawable.play);
                }
                else{
                    mediaPlayer.start();
                    playBtn.setImageResource(R.drawable.pause);
                }
            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtn.setImageResource(R.drawable.pause);
                mediaPlayer.release();
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
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                            mSeekbarUpdateHandler.postDelayed(this, 50);
                        }
                    }
                };
                mediaPlayer.start();
                mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtn.setImageResource(R.drawable.pause);

                mediaPlayer.release();
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
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                            mSeekbarUpdateHandler.postDelayed(this, 50);
                        }
                    }
                };
                mediaPlayer.start();
                mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
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
    }
}
