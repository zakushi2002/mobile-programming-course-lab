package com.hcmute.it.k20.app.musicplayer.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.adapter.SongAdapter;
import com.hcmute.it.k20.app.musicplayer.entity.Song;

import java.util.ArrayList;
import java.util.List;

public class PopularSongFragment extends Fragment {


    public PopularSongFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerViewSongs;
    View v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_popular_song, container, false);
        recyclerViewSongs = v.findViewById(R.id.rcv_songs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerViewSongs.setLayoutManager(linearLayoutManager);

        SongAdapter songAdapter = new SongAdapter(getListSongs());
        recyclerViewSongs.setAdapter(songAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(v.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewSongs.addItemDecoration(itemDecoration);
        return v;
    }
    private List<Song> getListSongs() {
        List<Song> list = new ArrayList<>();
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        list.add(new Song("Khuat Loi", "H-Kray", R.drawable.music_img, R.raw.khuatloihkrayofficiallyricsvideo));
        return list;
    }
}