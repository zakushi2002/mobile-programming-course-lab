package com.example.messageboundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start, stop;
    private Messenger messenger;
    private boolean isServiceConnected;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger = new Messenger(iBinder);
            isServiceConnected = true;
            // Send message play muic
            sendMessagePlayMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messenger = null;
            isServiceConnected = false;
        }
    };

    private void sendMessagePlayMusic() {
        Message message = Message.obtain(null, MessengerMusicBoundService.MSG_PLAY_MUSIC, 0, 0);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.button_start_service);
        start.setOnClickListener(view -> onStartService());
        stop = findViewById(R.id.button_stop_service);
        stop.setOnClickListener(view -> onStopService());
    }
    private void onStartService() {
        Intent intent = new Intent(this, MessengerMusicBoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void onStopService() {
        if (isServiceConnected) {
            unbindService(serviceConnection);
            isServiceConnected = false;
        }
    }
}