package com.fresher.tronnv.research.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.activities.MainActivity;

public class NotificationService extends Service {
    private String LOG_TAG = "ServiceClass";
    private Notification status;
    RemoteViews views;
    RemoteViews expandedViews;
    private boolean isPlaying = false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            showNotification(intent.getExtras().getString("name"), intent.getExtras().getString("author"));
            Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");

        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Play");
            showNotification("ACX","ACV");
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    public void showNotification(String name, String author) {
         views = new RemoteViews(getPackageName(), R.layout.status_bar);
         expandedViews = new RemoteViews(getPackageName(), R.layout.status_bar_expanded);
        // showing default album image
        views.setViewVisibility(R.id.img_status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.img_album_art, View.GONE);
        expandedViews.setImageViewBitmap(R.id.status_bar_album_art,
                Constants.getDefaultAlbumArt(this));

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

        //views.setOnClickPendingIntent(R.id.btn_collapse, pcloseIntent);
        //expandedViews.setOnClickPendingIntent(R.id.btn_collapse, pcloseIntent);

        if(isPlaying) {
            views.setImageViewResource(R.id.btn_play,
                    R.drawable.play);
            expandedViews.setImageViewResource(R.id.status_bar_play,
                    R.drawable.play);
            isPlaying = false;
        }
        else{
            views.setImageViewResource(R.id.btn_play,
                    R.drawable.pause);
            expandedViews.setImageViewResource(R.id.status_bar_play,
                    R.drawable.pause);
            isPlaying = true;
        }

        views.setTextViewText(R.id.tv_track_name, name);
        expandedViews.setTextViewText(R.id.status_bar_track_name, name);

        views.setTextViewText(R.id.tv_art_name, author);
        expandedViews.setTextViewText(R.id.status_bar_artist_name, author);

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
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }
}
