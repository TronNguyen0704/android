package com.fresher.tronnv.research.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.service.MediaPlayerService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by NGUYEN VAN TRON on 05/18/18.
 * This fragment to show media player
 */
public class MediaPlayerFragment extends Fragment {

    //private MediaPlayer mediaPlayer;
    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar;
    private boolean mIsPause = false;
    private boolean isResume = false;
    private Context mContext;
    private int mId = -1;
    private OnSongChange mOnSongChange;
    private boolean mIsPlaying = true;
    private BroadcastReceiver mBroadcastReceiver;
    private int mCurrentDuration = 0;
    private int mDuration = 0;
    private ImageButton mPlayBtn;
    private TimerTask mUpdateTask = new TimerTask() {
        @Override
        public void run() {
           sentToUpdateUI();
        }
    };
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnSongChange = (OnSongChange) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnSongChange");
        }
    }

    public MediaPlayerFragment() {
        Timer timer = new Timer("UpdateTimer");
        timer.schedule(mUpdateTask, 0L, 1L);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mCurrentDuration = intent.getIntExtra(MediaPlayerService.CURRENTPOSITION, mCurrentDuration);
                mDuration = intent.getIntExtra(MediaPlayerService.DURATION, mDuration);
                int songID = intent.getIntExtra("mId", mId);
                if (intent.getIntExtra("play_pause", -1) != -1) {
                    if (intent.getIntExtra("play_pause", -1) == 1) {
                        mPlayBtn.setImageResource(R.drawable.pause);
                        mIsPlaying = true;
                    } else {
                        mPlayBtn.setImageResource(R.drawable.play);
                        mIsPlaying = false;
                    }
                }
                if (mId != songID) {
                    mPlayBtn.setImageResource(R.drawable.pause);
                    mOnSongChange.onChangeUI(songID);
                    mId = songID;
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 10);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mBroadcastReceiver, new IntentFilter(MediaPlayerService.PROCESSED));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mBroadcastReceiver);
        mUpdateTask.cancel();
        super.onStop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_mediaplayer, container, false);
        final SeekBar seekBar = rootView.findViewById(R.id.seekbar);
        final TextView timeCount = rootView.findViewById(R.id.tv_time_count);
        final TextView totalTime = rootView.findViewById(R.id.tv_total_time);
        int timeReTotal = 0;
        seekBar.setMax(mDuration);
        long min = TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal);
        long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeReTotal) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeReTotal));
        totalTime.setText((min >= 10) ? String.valueOf(min) : "0" + min + ":" + ((sec >= 10) ? String.valueOf(sec) : "0" + sec));
        //Run thread update UI of seek bar
        mUpdateSeekbar = () -> {
            if (!mIsPause) {
                updateSeekBar(seekBar, timeCount, totalTime);
                mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 10);
            }
        };
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Intent intent = new Intent(MediaPlayerService.RESTART);
                    intent.putExtra("progress", progress);
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
                    localBroadcastManager.sendBroadcast(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateSeekBar(seekBar, timeCount, totalTime);
            }
        });
        mPlayBtn = rootView.findViewById(R.id.btn_play);
        mPlayBtn.setImageResource(R.drawable.pause);
        final ImageButton preBtn = rootView.findViewById(R.id.btn_pre);
        preBtn.setImageResource(R.drawable.pre);
        ImageButton nextBtn = rootView.findViewById(R.id.btn_next);
        nextBtn.setImageResource(R.drawable.next);
        mPlayBtn.setOnClickListener(v -> {
            if (mIsPlaying) {
                mOnSongChange.onPauseMedia();
                mIsPlaying = false;
                mPlayBtn.setImageResource(R.drawable.play);
            } else {
                mOnSongChange.onPlay(seekBar.getProgress());
                mIsPlaying = true;
                mPlayBtn.setImageResource(R.drawable.pause);
            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    mPlayBtn.setImageResource(R.drawable.pause);
                }
                if (mId == 1) {
                    mId = 11;
                }
                mId--;
                if (mId < 0) {
                    mId = MediaPlayerService.sServiceIdSong;
                    if (mId == 1) {
                        mId = 11;
                    }
                    mId--;
                }
                //sent data to LyricFragment
                mOnSongChange.onPreviousSong(mId);

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    mPlayBtn.setImageResource(R.drawable.pause);
                }
                if (mId == 0) {
                    mId = MediaPlayerService.sServiceIdSong;
                }
                if (mId == 10) {
                    mId = 0;
                }
                mId++;
                //sent data to LyricFragment
                mOnSongChange.onNextSong(mId);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
        super.onDestroy();
    }

    private void sentToUpdateUI() {
        Intent intent = new Intent(MediaPlayerService.RESTART);
        intent.putExtra("need_update",true);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.sendBroadcast(intent);
    }
    private void updateSeekBar(SeekBar seekBar, TextView timeCount, TextView totalTime) {
        seekBar.setMax(mDuration);
        int timeRemaining = mCurrentDuration;
        seekBar.setProgress(timeRemaining);
        long min = TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining);
        long sec = TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining));
        timeCount.setText((min >= 10) ? String.valueOf(min) : "0" + min + ":" + ((sec >= 10) ? String.valueOf(sec) : "0" + sec));
        long min2 = TimeUnit.MILLISECONDS.toMinutes((long) mDuration);
        long sec2 = TimeUnit.MILLISECONDS.toSeconds((long) mDuration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mDuration));
        totalTime.setText((min >= 10) ? String.valueOf(min2) : "0" + min2 + ":" + ((sec2 >= 10) ? String.valueOf(sec2) : "0" + sec2));
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    private int getRawIDByName(String name) {
        return getResources().getIdentifier(name, "raw", mContext.getPackageName());
    }

    //Interface to transfer data between two fragment
    public interface OnSongChange {
        void onNextSong(int id);

        void onPauseMedia();

        void onPlay(int curr);

        void onPreviousSong(int id);

        void onChangeUI(int songID);
    }
}
