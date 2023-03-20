package com.example.foregroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Song song;
    private boolean isPlaying;
    private RelativeLayout mediaPlayerBottom;
    private ImageView imgSong, imgClose, imgPlayOrPause;
    private TextView titleSong, singerOfSong;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                song = (Song) bundle.get("object_song");
                isPlaying = bundle.getBoolean("status_player");
                int actionMusic = bundle.getInt("action_music");
                handleMediaPlayerBottom(actionMusic);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // editData = findViewById(R.id.edit_data_intent);
        // private EditText editData;
        Button buttonStart = findViewById(R.id.button_start_service);
        Button buttonStop = findViewById(R.id.button_stop_service);

        mediaPlayerBottom = findViewById(R.id.media_player_bottom);
        imgSong = findViewById(R.id.img_song);
        imgClose = findViewById(R.id.img_close);
        imgPlayOrPause = findViewById(R.id.img_play_pause);
        titleSong = findViewById(R.id.title_song);
        singerOfSong = findViewById(R.id.singer_of_song);



        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("send_data_to_activity"));
        buttonStart.setOnClickListener(v -> clickStartService());
        buttonStop.setOnClickListener(v -> clickStopService());


    }

    private void clickStopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    private void clickStartService() {
        Song song = new Song("Khuất Lối", "H-Kray", R.drawable.khuatloihkrayzingmp3, R.raw.khuatloihkrayofficiallyricsvideo);
        Intent intent = new Intent(this, MyService.class);
        // intent.putExtra("key_data", editData.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song", song);
        intent.putExtras(bundle);

        startService(intent);
    }

    private void handleMediaPlayerBottom(int actionMusic) {
        switch (actionMusic) {
            case MyService.ACTION_START:
                mediaPlayerBottom.setVisibility(View.VISIBLE);
                showInfoSong();
                setStatusButtonPlayOrPause();
                break;
            case MyService.ACTION_PAUSE:
                setStatusButtonPlayOrPause();
                break;
            case MyService.ACTION_RESUME:
                setStatusButtonPlayOrPause();
                break;
            case MyService.ACTION_CLEAR:
                mediaPlayerBottom.setVisibility(View.GONE);
                break;
        }
    }

    private void showInfoSong() {
        if (song != null) {
            imgSong.setImageResource(song.getImage());
            titleSong.setText(song.getTitle());
            singerOfSong.setText(song.getSinger());
            imgPlayOrPause.setOnClickListener(v -> {
                if (isPlaying) {
                    sendActionToService(MyService.ACTION_PAUSE);
                }
                else {
                    sendActionToService(MyService.ACTION_RESUME);
                }
            });
            imgClose.setOnClickListener(v -> {
                sendActionToService(MyService.ACTION_CLEAR);

            });
        }
    }

    private void sendActionToService(int action) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("action_music_service", action);
        startService(intent);
    }

    private void setStatusButtonPlayOrPause() {
        if (isPlaying) {
            imgPlayOrPause.setImageResource(android.R.drawable.ic_media_pause);
        }
        else {
            imgPlayOrPause.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}