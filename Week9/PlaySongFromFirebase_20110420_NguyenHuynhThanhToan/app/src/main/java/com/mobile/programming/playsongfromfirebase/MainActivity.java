package com.mobile.programming.playsongfromfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this::playSong);
    }
    public void playSong(View v) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/connect-firebase-database.appspot.com/o/chotrongai.mp3?alt=media&token=6befd68e-92d5-4cab-b6c2-54952a33603c");
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.prepare();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}