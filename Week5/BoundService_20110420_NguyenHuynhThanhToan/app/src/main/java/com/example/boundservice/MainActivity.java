package com.example.boundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start, stop;
    private MusicBoundService musicBoundService;
    private boolean isServiceConnected;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicBoundService.MyBinder myBinder= (MusicBoundService.MyBinder) iBinder;
            musicBoundService = myBinder.getMusicBoundService();
            musicBoundService.startMusic();
            isServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceConnected = false;
        }
    };
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
        Intent intent = new Intent(this, MusicBoundService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void onStopService() {
        if (isServiceConnected) {
            unbindService(serviceConnection);
            isServiceConnected = false;
        }
    }
}