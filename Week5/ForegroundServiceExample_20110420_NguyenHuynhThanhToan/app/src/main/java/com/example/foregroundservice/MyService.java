package com.example.foregroundservice;

import static com.example.foregroundservice.MyApp.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.AndroidException;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private MediaPlayer mediaPlayer = null;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Check", "MyService onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // String receivedData = intent.getStringExtra("key_data");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Song song = (Song) bundle.get("object_song");
            if (song != null) {
                startMusic(song);
                sendNotification(song);
            }
        }
        // sendNotification(receivedData);
        return START_NOT_STICKY;
    }

    private void startMusic(Song song) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResource());
        }
        mediaPlayer.start();
    }

    // Input Data - String - 1) Project 1: Foreground service sample
    private void sendNotification(String receivedData) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service Example")
                .setContentText(receivedData).setSmallIcon(R.drawable.baseline_catching_pokemon_24)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }
    // Input Data - Song - 2) Project 2: Media player (foreground service) 
    private void sendNotification(Song song) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), song.getImage());
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.title_song, song.getTitle());
        remoteViews.setTextViewText(R.id.singer_of_song, song.getSinger());
        remoteViews.setImageViewBitmap(R.id.img_song, bitmap);
        remoteViews.setImageViewResource(R.id.img_play_pause, android.R.drawable.ic_media_pause);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service Example")
                .setSmallIcon(R.drawable.music_notification)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();

        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Check", "MyService onDestroy");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}