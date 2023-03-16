package com.example.messageboundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;

public class MessengerMusicBoundService extends Service {
    public static final int MSG_PLAY_MUSIC = 1;
    private MediaPlayer mediaPlayer = null;
    private Messenger messenger;
    public class MyHandler extends Handler {
        private Context appContext;

        public MyHandler(Context appContext) {
            this.appContext = appContext;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_PLAY_MUSIC:
                    startMusic();
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }
    public MessengerMusicBoundService()  {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Message MusicBoundService","onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Message MusicBoundService", "onBind");
        messenger = new Messenger(new MyHandler(this));
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("Message MusicBoundService", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Message MusicBoundService", "onDestroy");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void startMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.khuatloihkrayofficiallyricsvideo);
        }
        mediaPlayer.start();
    }
}