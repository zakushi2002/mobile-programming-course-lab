package com.example.backgroundservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.button_start_service);
        start.setOnClickListener(this::startService);
        stop = findViewById(R.id.button_stop_service);
        stop.setOnClickListener(this::stopService);
    }

    public void startService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }

    public void stopService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        stopService(intent);
    }
}