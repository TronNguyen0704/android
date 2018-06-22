package com.fresher.tronnv.research.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.activities.MainActivity;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service{
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
    private LocalBroadcastManager broadcastReceiver;
    private int id;
    private int duration;
    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer!= null) {

                try {
                    int curr = mediaPlayer.getCurrentPosition();
                    duration = mediaPlayer.getDuration();
                    if(mediaPlayer.isPlaying()){
                        sentUpdateToUI(curr,duration);
                    }
                }
                catch (IllegalStateException e){
                        System.out.print(e.toString());
                }
            }
            //Do some work here
        }
    };
    @Override
    public void onCreate() {
        broadcastReceiver = LocalBroadcastManager.getInstance(this);
        mediaPlayer = new MediaPlayer();
        names = new ArrayList<>();
        authors = new ArrayList<>();
        avatars = new ArrayList<>();
        views = new RemoteViews(getPackageName(), R.layout.status_bar);
        expandedViews = new RemoteViews(getPackageName(), R.layout.status_bar_expanded);
        initNotification();
    }
    private void sentUpdateToUI(int curr, int duration){
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
    public void onDestroy() {
        super.onDestroy();
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
        }
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            shorNotifi();
            mediaPlayer = MediaPlayer.create(this,getRawIDByName("mp" +String.valueOf(3100 + id)));
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();
            //Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            //Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");
            if(id == 1){
                id = 11;
            }
            id--;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this,getRawIDByName("mp" +String.valueOf(3100 + id)));
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();
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
                mediaPlayer.pause();
                isPlaying = false;
            }
            else{
                views.setImageViewResource(R.id.btn_play,
                        android.R.drawable.ic_media_pause);
                expandedViews.setImageViewResource(R.id.status_bar_play,
                        android.R.drawable.ic_media_pause);
                isPlaying = true;
                mediaPlayer.start();
            }
            if(!isPlaying) {
                views.setViewVisibility(R.id.btn_collapse_bar, View.GONE);
                expandedViews.setViewVisibility(R.id.btn_collapse, View.GONE);
            }
            else {
                views.setViewVisibility(R.id.btn_collapse_bar, View.VISIBLE);
                expandedViews.setViewVisibility(R.id.btn_collapse, View.VISIBLE);
            }
            shorNotifi();
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            //Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
            if(id == 10){
                id = 0;
            }
            id++;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this,getRawIDByName("mp" +String.valueOf(3100 + id)));
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();
            //Sent request to UI
            Intent intentNext = new Intent(PROCESSED);
            intentNext.putExtra("id",id);
            broadcastReceiver.sendBroadcast(intentNext);
            shorNotifi();
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            //Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }
    private int getRawIDByName(String name){
        return getResources().getIdentifier(name, "raw", this.getPackageName());
    }
    public void initNotification() {

        // showing default album image
        views.setViewVisibility(R.id.img_status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.img_album_art, View.GONE);
        expandedViews.setImageViewBitmap(R.id.status_bar_album_art,Constants.getDefaultAlbumArt(this)
                );
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
        Intent notificationIntent = new Intent(this, MainActivity.class);
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
        //expandedViews.setTextViewText(R.id.status_bar_album_name, "Album Name");
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
        views.setTextViewText(R.id.tv_track_name, names.get(id - 1 ));
        expandedViews.setTextViewText(R.id.status_bar_track_name, names.get(id - 1));
        views.setTextViewText(R.id.tv_art_name, authors.get(id-1));
        expandedViews.setTextViewText(R.id.status_bar_artist_name, authors.get(id - 1));
        status.contentView = views;
        status.bigContentView = expandedViews;
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }
}
