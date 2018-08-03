package com.fresher.tronnv.research.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.activities.LyricActivity;
import com.fresher.tronnv.research.usercontrol.MediaPlayerCustom;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MediaPlayerService extends Service{
    public static final String RESTART = "MY_SERVICE_REPLACED";
    public static final String PROCESSED = "com.fresher.tronnv.research.service.REQUEST_PROCESSED";
    public static final String CURRENTPOSITION = "com.fresher.tronnv.research.service.REQUEST_CURRENT_POSITION";
    public static final String DURATION = "com.fresher.tronnv.research.service.REQUEST_DURATION";
    private static final String LOG_TAG = "MediaPlayerService";
    public static boolean sServiceState = false;
    public static int sServiceIdSong = -1;
    private Notification mStatus;
    private RemoteViews mViews;
    private RemoteViews mExpandedViews;
    private boolean mIsPlaying = true;
    private MediaPlayerCustom mMediaPlayer;
    private ArrayList<String> mNames;
    private ArrayList<String> mAuthors;
    private ArrayList<String> mAvatars;
    private int mProgressMediaPlayer = 0;
    private LocalBroadcastManager mBroadcastReceiver;
    private BroadcastReceiver mReceiver;
    private int mId;
    private int mDuration = 0;
    private int mCurr = 0;
    @Override
    public void onCreate() {
        sServiceState = true;
        mBroadcastReceiver = LocalBroadcastManager.getInstance(this);
        mMediaPlayer = new MediaPlayerCustom(getApplicationContext());
        mNames = new ArrayList<>();
        mAuthors = new ArrayList<>();
        mAvatars = new ArrayList<>();
        mViews = new RemoteViews(getPackageName(), R.layout.status_bar);
        mExpandedViews = new RemoteViews(getPackageName(), R.layout.status_bar_expanded);
        initNotification();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int progress = intent.getIntExtra("progress", mCurr);
                if (progress != mCurr) {
                    mProgressMediaPlayer = progress;
                    mMediaPlayer.seekTo(mProgressMediaPlayer);
                }
                if (intent.getBooleanExtra("need_update",false)) {
                    mCurr = (mMediaPlayer != null) ? mMediaPlayer.getCurrentPosition() : mCurr;
                    mDuration = (mMediaPlayer != null) ? mMediaPlayer.getDuration() : mCurr;
                    if ((mMediaPlayer != null) && mMediaPlayer.isPlaying() && mIsPlaying) {
                        sentUpdateToUI(mCurr, mDuration);
                    }
//                    if(mDuration - mCurr < 150){
//                        theNextSong();
//                    }
                }
                if (intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("next")) {
                    mViews.setImageViewResource(R.id.btn_play,
                            android.R.drawable.ic_media_pause);
                    mExpandedViews.setImageViewResource(R.id.status_bar_play,
                            android.R.drawable.ic_media_pause);
                    mViews.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                    mExpandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                    theNextSong();
                } else if (intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("previous")) {
                    mViews.setImageViewResource(R.id.btn_play,
                            android.R.drawable.ic_media_pause);
                    mExpandedViews.setImageViewResource(R.id.status_bar_play,
                            android.R.drawable.ic_media_pause);
                    mViews.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                    mExpandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                    if (mId == 1) {
                        mId = 11;
                    }
                    mId--;
                    sServiceIdSong = mId;
                    mMediaPlayer.stopPlayback();
                    mMediaPlayer.setId(getRawIDByName("mp" + String.valueOf(3100 + mId))); //= MediaPlayer.create(getBaseContext(), getRawIDByName("mp" + String.valueOf(3100 + mId)));
                    mMediaPlayer.setOnCompletionListener(mp -> {
                        theNextSong();
                    });
                    mMediaPlayer.start();
                    sServiceState = true;
                    mIsPlaying = true;
                    mDuration = mMediaPlayer.getDuration();
                    Handler handler = new Handler(getMainLooper());
                    handler.postDelayed(() -> notificationChange(), 500L);
                    notificationChange();
                } else if (intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("play")) {
                    mViews.setImageViewResource(R.id.btn_play,
                            android.R.drawable.ic_media_pause);
                    mExpandedViews.setImageViewResource(R.id.status_bar_play,
                            android.R.drawable.ic_media_pause);
                    mViews.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                    mExpandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                    mIsPlaying = true;
                    mMediaPlayer.start();
                    sServiceState = true;
                    mDuration = mMediaPlayer.getDuration();
                    if (intent.getIntExtra("currSaved", -1) != -1) {
                        mMediaPlayer.seekTo(intent.getIntExtra("currSaved", 0));
                    }
                    Handler handler = new Handler(getMainLooper());
                    handler.postDelayed(() -> notificationChange(), 500L);
                    notificationChange();
                } else if (intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("pause")) {

                    mViews.setImageViewResource(R.id.btn_play,
                            android.R.drawable.ic_media_play);
                    mExpandedViews.setImageViewResource(R.id.status_bar_play,
                            android.R.drawable.ic_media_play);
                    mViews.setViewVisibility(R.id.btn_collapse_bar, View.VISIBLE);
                    mExpandedViews.setViewVisibility(R.id.btn_collapse, View.VISIBLE);
                    mMediaPlayer.pause();
                    mIsPlaying = false;
                    sServiceState = false;
                    stopForeground(true);
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(MediaPlayerService.RESTART));
    }

    private void theNextSong() {
        if (mId == 10) {
            mId = 0;
        }
        mId++;
        sServiceIdSong = mId;
        if (mMediaPlayer != null) {
            mMediaPlayer.stopPlayback();
        }
        mMediaPlayer.setId(getRawIDByName("mp" + String.valueOf(3100 + mId))); // = MediaPlayer.create(getApplication(), getRawIDByName("mp" + String.valueOf(3100 + mId)));
        mMediaPlayer.setOnCompletionListener(mp -> {
            theNextSong();
        });
        mMediaPlayer.start();
        mIsPlaying = true;
        sServiceState = true;
        mDuration = mMediaPlayer.getDuration();
        mViews.setImageViewResource(R.id.btn_play,
                android.R.drawable.ic_media_pause);
        mExpandedViews.setImageViewResource(R.id.status_bar_play,
                android.R.drawable.ic_media_pause);
        mViews.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
        mExpandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(() -> {notificationChange();}, 500L);
        notificationChange();
        //Sent request to UI
        Intent intentPre = new Intent(PROCESSED);
        intentPre.putExtra("mId", mId);
        mBroadcastReceiver.sendBroadcast(intentPre);
    }
    private void sentUpdateToUI(int curr, int duration) {
        sServiceIdSong = mId;
        Intent intent = new Intent(PROCESSED);
        if (curr >= 0 && duration > 0) {
            intent.putExtra(CURRENTPOSITION, curr);
            intent.putExtra(DURATION, duration);
            mBroadcastReceiver.sendBroadcast(intent);
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        if (mMediaPlayer != null)
            mMediaPlayer.stopPlayback();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!(mNames != null && mNames.size() > 0)) {
            mNames.addAll(intent.getExtras().getStringArrayList("names"));
            mAuthors.addAll(intent.getExtras().getStringArrayList("authors"));
            mAvatars.addAll(intent.getExtras().getStringArrayList("avatars"));
            mId = intent.getExtras().getInt("id");
            sServiceIdSong = mId;
        }
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Handler handler = new Handler(getMainLooper());
            handler.postDelayed(() -> notificationChange(), 500L);
            notificationChange();
            mMediaPlayer.stopPlayback();
            mMediaPlayer.setId(getRawIDByName("mp" + String.valueOf(3100 + mId))); //= MediaPlayer.create(this, getRawIDByName("mp" + String.valueOf(3100 + mId)));
            mMediaPlayer.setOnCompletionListener(mp -> {
                theNextSong();
            });
            mMediaPlayer.start();
            mDuration = mMediaPlayer.getDuration();
            sServiceState = true;
            mIsPlaying = true;
            //Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            //Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");
            if (mId == 1) {
                mId = 11;
            }
            mId--;
            sServiceIdSong = mId;
            mMediaPlayer.stopPlayback();
            mMediaPlayer.setId(getRawIDByName("mp" + String.valueOf(3100 + mId))); //mMediaPlayer = MediaPlayer.create(this, getRawIDByName("mp" + String.valueOf(3100 + mId)));
            mMediaPlayer.setOnCompletionListener(mp -> {
                theNextSong();
            });
            mMediaPlayer.start();
            mDuration = mMediaPlayer.getDuration();
            mViews.setImageViewResource(R.id.btn_play,
                    android.R.drawable.ic_media_pause);
            mExpandedViews.setImageViewResource(R.id.status_bar_play,
                    android.R.drawable.ic_media_pause);
            mViews.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
            mExpandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
            mIsPlaying = true;
            sServiceState = true;
            Handler handler = new Handler(getMainLooper());
            handler.postDelayed(() -> notificationChange(), 500L);
            notificationChange();
            //Sent request to UI
            Intent intentPre = new Intent(PROCESSED);
            intentPre.putExtra("mId", mId);
            mBroadcastReceiver.sendBroadcast(intentPre);
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            //Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Play");
            if (mMediaPlayer.isPlaying()) {
                mViews.setImageViewResource(R.id.btn_play,
                        android.R.drawable.ic_media_play);
                mExpandedViews.setImageViewResource(R.id.status_bar_play,
                        android.R.drawable.ic_media_play);
                mViews.setViewVisibility(R.id.btn_collapse_bar, View.VISIBLE);
                mExpandedViews.setViewVisibility(R.id.btn_collapse, View.VISIBLE);
                mMediaPlayer.pause();
                mIsPlaying = false;
            } else {
                mViews.setImageViewResource(R.id.btn_play,
                        android.R.drawable.ic_media_pause);
                mExpandedViews.setImageViewResource(R.id.status_bar_play,
                        android.R.drawable.ic_media_pause);
                mViews.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                mExpandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                mIsPlaying = true;
                mMediaPlayer.start();
                mDuration = mMediaPlayer.getDuration();
            }
            Handler handler = new Handler(getMainLooper());
            handler.postDelayed(() -> notificationChange(), 500L);
            notificationChange();
            Intent intentPre = new Intent(PROCESSED);
            intentPre.putExtra("play_pause", mIsPlaying ? 1 : 2);
            mBroadcastReceiver.sendBroadcast(intentPre);
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            //Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
            theNextSong();
            //Sent request to UI
            Intent intentNext = new Intent(PROCESSED);
            intentNext.putExtra("mId", mId);
            mBroadcastReceiver.sendBroadcast(intentNext);
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            //Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            mMediaPlayer.stopPlayback();
            sServiceState = false;
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    private int getRawIDByName(String name) {
        return getResources().getIdentifier(name, "raw", this.getPackageName());
    }
    public void initNotification() {
        // showing default album image
        mViews.setViewVisibility(R.id.img_status_bar_icon, View.VISIBLE);
        mViews.setViewVisibility(R.id.img_album_art, View.GONE);
        if (mAvatars != null && mAvatars.size() > 0) {
            mExpandedViews.setImageViewBitmap(R.id.status_bar_album_art, Utils.getBitmapFromURL(mAvatars.get(mId), getBaseContext()));
        } else {
            mExpandedViews.setImageViewBitmap(R.id.status_bar_album_art, Utils.getDefaultAlbumArt(getBaseContext()));
        }
        mViews.setImageViewResource(R.id.btn_play,
                android.R.drawable.ic_media_pause);
        mExpandedViews.setImageViewResource(R.id.status_bar_play,
                android.R.drawable.ic_media_pause);
        mViews.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
        mExpandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
        mViews.setImageViewResource(R.id.btn_next,
                android.R.drawable.ic_media_next);
        mExpandedViews.setImageViewResource(R.id.status_bar_next,
                android.R.drawable.ic_media_next);
        mViews.setImageViewResource(R.id.btn_pre,
                android.R.drawable.ic_media_previous);
        mExpandedViews.setImageViewResource(R.id.status_bar_prev,
                android.R.drawable.ic_media_previous);
        Intent notificationIntent = new Intent(this, LyricActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, MediaPlayerService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, MediaPlayerService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, MediaPlayerService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, MediaPlayerService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);
        mViews.setOnClickPendingIntent(R.id.btn_play, pplayIntent);
        mExpandedViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        mViews.setOnClickPendingIntent(R.id.btn_next, pnextIntent);
        mExpandedViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        mExpandedViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        mViews.setOnClickPendingIntent(R.id.btn_collapse_bar, pcloseIntent);
        mExpandedViews.setOnClickPendingIntent(R.id.btn_collapse, pcloseIntent);
        if ((mNames != null && mNames.size() > 0)) {
            mViews.setTextViewText(R.id.tv_track_name, mNames.get(mId - 1));
            mExpandedViews.setTextViewText(R.id.status_bar_track_name, mNames.get(mId - 1));

            mViews.setTextViewText(R.id.tv_art_name, mAuthors.get(mId - 1));
            mExpandedViews.setTextViewText(R.id.status_bar_artist_name, mAuthors.get(mId - 1));
        }
        String CHANNEL_ID = "my_channel_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "my_service";
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            service.createNotificationChannel(chan);
            CHANNEL_ID = channelId;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID).setOngoing(false);
        mStatus = builder.build();
        mStatus.contentView = mViews;
        mStatus.bigContentView = mExpandedViews;
        mStatus.flags = Notification.FLAG_ONGOING_EVENT;
        mStatus.icon = R.drawable.ic_mp3;
        mStatus.contentIntent = pendingIntent;

    }

    private void notificationChange() {
        sServiceIdSong = mId;
        mViews.setTextViewText(R.id.tv_track_name, mNames.get(mId - 1));
        mExpandedViews.setTextViewText(R.id.status_bar_track_name, mNames.get(mId - 1));
        mViews.setTextViewText(R.id.tv_art_name, mAuthors.get(mId - 1));
        mExpandedViews.setTextViewText(R.id.status_bar_artist_name, mAuthors.get(mId - 1));
        if (mAvatars != null && mAvatars.size() > 0) {
            mExpandedViews.setImageViewBitmap(R.id.status_bar_album_art, Utils.getBitmapFromURL(mAvatars.get(mId - 1), getBaseContext()));
            mViews.setImageViewBitmap(R.id.img_status_bar_icon, Utils.getBitmapFromURL(mAvatars.get(mId - 1), getBaseContext()));
        } else {
            mExpandedViews.setImageViewBitmap(R.id.status_bar_album_art, Utils.getDefaultAlbumArt(getBaseContext()));
        }
        mStatus.contentView = mViews;
        mStatus.bigContentView = mExpandedViews;
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, mStatus);
    }
}
