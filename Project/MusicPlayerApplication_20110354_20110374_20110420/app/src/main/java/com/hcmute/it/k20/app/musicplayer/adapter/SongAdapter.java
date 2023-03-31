package com.hcmute.it.k20.app.musicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.entity.Song;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{
    private List<Song> songs;

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @NonNull
    @NotNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        if (song == null) {
            return;
        }
        holder.imgSong.setImageResource(song.getImage());
        holder.titleSong.setText(song.getTitle());
        holder.nameSinger.setText(song.getSinger());
    }

    @Override
    public int getItemCount() {
        if (songs != null) {
            return songs.size();
        }
        return 0;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgSong;
        private TextView titleSong;
        private TextView nameSinger;
        public SongViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.img_song);
            titleSong = itemView.findViewById(R.id.title_song);
            nameSinger = itemView.findViewById(R.id.name_singer);
        }
    }
}
