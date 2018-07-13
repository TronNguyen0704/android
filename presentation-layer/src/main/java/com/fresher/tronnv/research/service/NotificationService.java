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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.activities.LyricActivity;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service{
    public static final String RESTART = "MY_SERVICE_REPLACED";
    private String LOG_TAG = "ServiceClass";
    private Notification status;
    RemoteViews views;
    RemoteViews expandedViews;
    public static String PROCESSED = "com.fresher.tronnv.research.service.REQUEST_PROCESSED";
    public static String CURRENTPOSITION = "com.fresher.tronnv.research.service.REQUEST_CURRENT_POSITION";
    public static String DURATION = "com.fresher.tronnv.research.service.REQUEST_DURATION";
    private boolean isPlaying = true;
    private MediaPlayer mediaPlayer;
    private ArrayList<String> names;
    private ArrayList<String> authors;
    private ArrayList<String> avatars;
    private int progressMediaPlayer = 0;
    private LocalBroadcastManager broadcastReceiver;
    private BroadcastReceiver bReceiver;
    private int id;
    private int duration = 0;
    private int curr = 0;
    public static boolean serviceState = false;
    public static int serviceIdSong= -1;
    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer != null) {
                try {
                    curr = (mediaPlayer!= null) ? mediaPlayer.getCurrentPosition() : curr;
                    duration = (mediaPlayer!= null) ? mediaPlayer.getDuration() : curr;
                    if((mediaPlayer!= null) && mediaPlayer.isPlaying() && isPlaying){
                        sentUpdateToUI(curr,duration);
                    }
                    if(duration - curr <= 150){//trick completion of media player
                        theNextSong();
                    }
                }
                catch (IllegalStateException e){
                        System.out.print(e.toString());
                }
            }
        }
    };
    @Override
    public void onCreate() {
        serviceState  = true;
        broadcastReceiver = LocalBroadcastManager.getInstance(this);
//        mediaPlayer = new MediaPlayer();
        names = new ArrayList<>();
        authors = new ArrayList<>();
        avatars = new ArrayList<>();
        views = new RemoteViews(getPackageName(), R.layout.status_bar);
        expandedViews = new RemoteViews(getPackageName(), R.layout.status_bar_expanded);
        initNotification();
        bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int progress = intent.getIntExtra("progress",curr);
                if(progress != curr){
                    progressMediaPlayer = progress;
                    mediaPlayer.seekTo(progressMediaPlayer);
                }
                if(intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("next")){
                    views.setImageViewResource(R.id.btn_play,
                            android.R.drawable.ic_media_pause);
                    expandedViews.setImageViewResource(R.id.status_bar_play,
                            android.R.drawable.ic_media_pause);
                    views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                    expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                    theNextSong();
                }
                else if(intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("previous")){
                    views.setImageViewResource(R.id.btn_play,
                            android.R.drawable.ic_media_pause);
                    expandedViews.setImageViewResource(R.id.status_bar_play,
                            android.R.drawable.ic_media_pause);
                    views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                    expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                    if(id == 1){
                        id = 11;
                    }
                    id--;
                    serviceIdSong = id;
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    mediaPlayer = MediaPlayer.create(getBaseContext(),getRawIDByName("mp" +String.valueOf(3100 + id)));
                    mediaPlayer.start();
                    serviceState = true;
                    isPlaying = true;
                    duration = mediaPlayer.getDuration();
                    shorNotifi();
                }
                else if(intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("play")){
                        views.setImageViewResource(R.id.btn_play,
                                android.R.drawable.ic_media_pause);
                        expandedViews.setImageViewResource(R.id.status_bar_play,
                                android.R.drawable.ic_media_pause);
                        views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                        expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                        isPlaying = true;
                        serviceState = true;
                        mediaPlayer.start();
                        if(intent.getIntExtra("currSaved",-1) != -1) {
                            mediaPlayer.seekTo(intent.getIntExtra("currSaved",0));
                        }
                    shorNotifi();
                }
                else if( intent.getStringExtra("restart") != null && intent.getStringExtra("restart").endsWith("pause")){

                        views.setImageViewResource(R.id.btn_play,
                                android.R.drawable.ic_media_play);
                        expandedViews.setImageViewResource(R.id.status_bar_play,
                                android.R.drawable.ic_media_play);
                        views.setViewVisibility(R.id.btn_collapse_bar, View.VISIBLE);
                        expandedViews.setViewVisibility(R.id.btn_collapse, View.VISIBLE);
                        mediaPlayer.pause();
                        isPlaying = false;
                    serviceState = false;
                    stopForeground(true);
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver,new IntentFilter(NotificationService.RESTART));
    }
    private void theNextSong(){
        if(id == 10){
            id = 0;
        }
        id++;
        serviceIdSong = id;
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        mediaPlayer = MediaPlayer.create(getApplication(),getRawIDByName("mp" +String.valueOf(3100 + id)));
        mediaPlayer.start();
        isPlaying = true;
        serviceState = true;
        duration = mediaPlayer.getDuration();
        views.setImageViewResource(R.id.btn_play,
                android.R.drawable.ic_media_pause);
        expandedViews.setImageViewResource(R.id.status_bar_play,
                android.R.drawable.ic_media_pause);
        views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
        expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
        shorNotifi();
    }
    private void sentUpdateToUI(int curr, int duration){
        serviceIdSong = id;
        Intent intent = new Intent(PROCESSED);
        if(curr >=0 && duration > 0){
            intent.putExtra(CURRENTPOSITION,curr);
            intent.putExtra(DURATION,duration);
            broadcastReceiver.sendBroadcast(intent);
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bReceiver);
        if(mediaPlayer != null)
            mediaPlayer.release();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!(names != null && names.size()>0)){
            Timer timer = new Timer("UpdateTimer");
            timer.schedule(updateTask, 0L, 1L);
            names.addAll(intent.getExtras().getStringArrayList("names"));
            authors.addAll(intent.getExtras().getStringArrayList("authors"));
            avatars.addAll(intent.getExtras().getStringArrayList("avatars"));
            id = intent.getExtras().getInt("id");
            serviceIdSong = id;
        }
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            shorNotifi();
            mediaPlayer = MediaPlayer.create(this,getRawIDByName("mp" +String.valueOf(3100 + id)));
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();
            serviceState = true;
            isPlaying = true;
            //Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            //Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");
            if(id == 1){
                id = 11;
            }
            id--;
            serviceIdSong = id;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this,getRawIDByName("mp" +String.valueOf(3100 + id)));
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();
            views.setImageViewResource(R.id.btn_play,
                    android.R.drawable.ic_media_pause);
            expandedViews.setImageViewResource(R.id.status_bar_play,
                    android.R.drawable.ic_media_pause);
            views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
            expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
            isPlaying = true;
            serviceState = true;
            shorNotifi();
            //Sent request to UI
            Intent intentPre = new Intent(PROCESSED);
            intentPre.putExtra("id",id);
            broadcastReceiver.sendBroadcast(intentPre);
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            //Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Play");
            if(mediaPlayer.isPlaying()) {
                views.setImageViewResource(R.id.btn_play,
                        android.R.drawable.ic_media_play);
                expandedViews.setImageViewResource(R.id.status_bar_play,
                        android.R.drawable.ic_media_play);
                views.setViewVisibility(R.id.btn_collapse_bar, View.VISIBLE);
                expandedViews.setViewVisibility(R.id.btn_collapse, View.VISIBLE);
                mediaPlayer.pause();
                isPlaying = false;
            }
            else{
                views.setImageViewResource(R.id.btn_play,
                        android.R.drawable.ic_media_pause);
                expandedViews.setImageViewResource(R.id.status_bar_play,
                        android.R.drawable.ic_media_pause);
                views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
                isPlaying = true;
                mediaPlayer.start();
            }
            shorNotifi();
            Intent intentPre = new Intent(PROCESSED);
            intentPre.putExtra("play_pause",isPlaying ? 1 : 2);
            broadcastReceiver.sendBroadcast(intentPre);
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            //Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
            theNextSong();
            //Sent request to UI
            Intent intentNext = new Intent(PROCESSED);
            intentNext.putExtra("id",id);
            broadcastReceiver.sendBroadcast(intentNext);
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            //Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            serviceState = false;
            LocalBroadcastManager.getInstance(this).unregisterReceiver(bReceiver);
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }
    private int getRawIDByName(String name){
        return getResources().getIdentifier(name, "raw", this.getPackageName());
    }
    private BaseTarget target2 = new BaseTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
            // do something with the bitmap
            // for demonstration purposes, let's set it to an imageview
            expandedViews.setImageViewBitmap(R.id.status_bar_album_art,bitmap);
        }

        @Override
        public void getSize(SizeReadyCallback cb) {
            cb.onSizeReady(250, 250);
        }

        @Override
        public void removeCallback(SizeReadyCallback cb) {}
    };
    private void loadImageSimpleTargetApplicationContext() {
        Glide
                .with(getApplicationContext())
                .load(avatars.get(id - 1))
                .into(target2);
    }
    public void initNotification() {

        // showing default album image
        views.setViewVisibility(R.id.img_status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.img_album_art, View.GONE);
        expandedViews.setImageViewBitmap(R.id.status_bar_album_art, Constants.getDefaultAlbumArt(getBaseContext()));
//        if((names != null && names.size()>0)) {
//            //expandedViews.setImageViewBitmap(R.id.status_bar_album_art, Constants.getBitmap(avatars.get(id - 1),getBaseContext())
//            //);
//            loadImageSimpleTargetApplicationContext();
//        }
        views.setImageViewResource(R.id.btn_play,
                android.R.drawable.ic_media_pause);
        expandedViews.setImageViewResource(R.id.status_bar_play,
                android.R.drawable.ic_media_pause);
        views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
        expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
        views.setImageViewResource(R.id.btn_next,
                android.R.drawable.ic_media_next);
        expandedViews.setImageViewResource(R.id.status_bar_next,
                android.R.drawable.ic_media_next);
        views.setImageViewResource(R.id.btn_pre,
                android.R.drawable.ic_media_previous);
        expandedViews.setImageViewResource(R.id.status_bar_prev,
                android.R.drawable.ic_media_previous);
        Intent notificationIntent = new Intent(this, LyricActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, NotificationService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);
        views.setOnClickPendingIntent(R.id.btn_play, pplayIntent);
        expandedViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.btn_next, pnextIntent);
        expandedViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        expandedViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.btn_collapse_bar, pcloseIntent);
        expandedViews.setOnClickPendingIntent(R.id.btn_collapse, pcloseIntent);
        if((names != null && names.size()>0)) {
            views.setTextViewText(R.id.tv_track_name, names.get(id - 1));
            expandedViews.setTextViewText(R.id.status_bar_track_name, names.get(id - 1));

            views.setTextViewText(R.id.tv_art_name, authors.get(id - 1));
            expandedViews.setTextViewText(R.id.status_bar_artist_name, authors.get(id - 1));
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        status = builder.build();
        status.contentView = views;
        status.bigContentView = expandedViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.icon = R.drawable.ic_mp3;
        status.contentIntent = pendingIntent;

    }
    private void shorNotifi(){
        serviceIdSong = id;
        views.setTextViewText(R.id.tv_track_name, names.get(id - 1 ));
        expandedViews.setTextViewText(R.id.status_bar_track_name, names.get(id - 1));
        views.setTextViewText(R.id.tv_art_name, authors.get(id-1));
        expandedViews.setTextViewText(R.id.status_bar_artist_name, authors.get(id - 1));
        status.contentView = views;
        status.bigContentView = expandedViews;
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }
}
