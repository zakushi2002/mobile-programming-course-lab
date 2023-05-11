package com.hcmute.it.k20.app.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hcmute.it.k20.app.musicplayer.MainActivity;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.adapter.SongAdapter;
import com.hcmute.it.k20.app.musicplayer.entity.Song;
import com.hcmute.it.k20.app.musicplayer.service.Mp3Service;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvArtist, seekStart, seekEnd;
    private ImageButton btnPrev, btnPlayOrPause, btnNext;
    public static SeekBar seekBar;
    private boolean isPlaying;
    private Song songs;
    public static int selectedIndex;
    public static CircleImageView mCircleImage;
    private boolean autoPlay;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("send_data_to_activity"));
        mCircleImage = findViewById(R.id.image_view_circle);
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) {
                return;
            }
            songs = (Song) bundle.get("object_song");
            isPlaying = bundle.getBoolean("status_player");
            int actionMusic = bundle.getInt("action_music");

            handlePlayerMusic(actionMusic);
        }
    };
    private void handlePlayerMusic(int action) {
        switch (action) {
            case Mp3Service.ACTION_START:
                showInfoSong();
                setStatusPlayOrPause();
                break;
            case Mp3Service.ACTION_PAUSE:
                setStatusPlayOrPause();
                break;
            case Mp3Service.ACTION_RESUME:
                setStatusPlayOrPause();
                break;
            case Mp3Service.ACTION_PREV:
                showInfoSong();
                break;
            case Mp3Service.ACTION_NEXT:
                showInfoSong();
                break;
        }
    }
    private void showInfoSong() {
        if (songs == null) {
            return;
        }


        updateInfo();

        btnPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    StopAnimation();
                    sendActionToService(Mp3Service.ACTION_PAUSE);
                } else {
                    StartAnimation();
                    sendActionToService(Mp3Service.ACTION_RESUME);
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIndex > 0) {
                    selectedIndex--;
                    songs = new Song(SongAdapter.songs.get(selectedIndex).getId(), SongAdapter.songs.get(selectedIndex).getTitle(),
                            SongAdapter.songs.get(selectedIndex).getArtist(), SongAdapter.songs.get(selectedIndex).getImage(), SongAdapter.songs.get(selectedIndex).getStorageUrl());
                    updateInfo();
                    sendActionToService(Mp3Service.ACTION_PREV);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongAdapter songRVAdapter = new SongAdapter();
                if (selectedIndex >= 0 && selectedIndex < songRVAdapter.getItemCount() - 1) {
                    selectedIndex++;
                    songs = new Song(Long.parseLong(String.valueOf(selectedIndex)), SongAdapter.songs.get(selectedIndex).getTitle(), SongAdapter.songs.get(selectedIndex).getArtist(), SongAdapter.songs.get(selectedIndex).getImage(), SongAdapter.songs.get(selectedIndex).getStorageUrl());
                    updateInfo();
                    sendActionToService(Mp3Service.ACTION_NEXT);
                }
            }
        });
        MainActivity.currentIndex = selectedIndex;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    Mp3Service.player.seekTo(progress);
                    updateInfo();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Mp3Service.player.pause();
                updateInfo();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBars) {
                if(isPlaying)
                {
                    Mp3Service.player.start();
                    updateInfo();
                }
            }
        });

        Mp3Service.player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                seekBar.setProgress(0);
                isPlaying = false;
                StopAnimation();
                updateInfo();
                if (autoPlay){
                    Mp3Service.player.start();
                    StartAnimation();
                    isPlaying = true;
                }
                else{
                    sendActionToService(Mp3Service.ACTION_PAUSE);
                }
            }
        });
    }
    private void updateInfo() {
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/application-music-player.appspot.com/o/img%2F" + songs.getTitle() + ".jpg?alt=media&token=d1260574-beb6-4228-8f72-3f88246c27d8";
        if (!DetailActivity.this.isFinishing ()){
            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .into(mCircleImage);
        }
        tvTitle.setText(songs.getTitle());
        tvArtist.setText(songs.getArtist());
        if(Mp3Service.player != null){
            seekBar.setProgress(Mp3Service.player.getCurrentPosition());
            seekBar.setMax(Mp3Service.player.getDuration());
        }

        int durationInt = Mp3Service.player.getDuration();
        String durationString = convertDurationToString(durationInt);
        seekEnd.setText(durationString);
        seekStart.setText(convertDurationToString(Mp3Service.player.getCurrentPosition()));
        setStatusPlayOrPause();
    }
    private String convertDurationToString(int durationInMillis) {
        int seconds = (durationInMillis / 1000) % 60;
        int minutes = (durationInMillis / (1000 * 60)) % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private void sendActionToService(int action) {
        Intent intent = new Intent(this, Mp3Service.class);
        intent.putExtra("action_music", action);

        startService(intent);
    }

    private void setStatusPlayOrPause() {
        if (isPlaying) {
            btnPlayOrPause.setImageResource(R.drawable.nutpause);
        } else {
            btnPlayOrPause.setImageResource(R.drawable.nutplay);
        }
    }
    public static void StartAnimation(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCircleImage.animate().rotationBy(360).withEndAction(this).setDuration(5000).setInterpolator(new LinearInterpolator()).start();
            }
        };
        mCircleImage.animate().rotationBy(360).withEndAction(runnable).setDuration(5000).setInterpolator(new LinearInterpolator()).start();

    }

    public static void StopAnimation(){
        mCircleImage.animate().cancel();
    }
}