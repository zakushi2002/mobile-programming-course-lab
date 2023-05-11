package com.hcmute.it.k20.app.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hcmute.it.k20.app.musicplayer.MainActivity;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.adapter.SongAdapter;
import com.hcmute.it.k20.app.musicplayer.entity.Song;

import java.util.ArrayList;
import java.util.List;

public class PopularSongActivity extends AppCompatActivity {
    private RecyclerView rv_add_song;
    private SongAdapter adapter;
    private List<Song> songArrayList;
    private Song songs;
    private ImageView img_add_song_to_playlist;
    private boolean isPlaying;    private RelativeLayout layout_bottom;
    private MainActivity mainActivity;
    public static int selectedIndex;
    public static ImageView imgSong, imgPlayOrPause, imgPrev, imgNext, imgClear;
    public static TextView tvTitleSong, tvSingerSong;
    public static int currentPlaylistIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_song);
        mainActivity = new MainActivity();
        // rv_add_song = findViewById(R.id.rv_song_playlist);
    }
}