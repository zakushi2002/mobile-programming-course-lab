package com.hcmute.it.k20.app.musicplayer.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.entity.Playlist;
import com.hcmute.it.k20.app.musicplayer.entity.Song;

import de.hdodenhof.circleimageview.CircleImageView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    public static List<Song> songs;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public static TextView tvTitleSong, tvArtistSong;
    public static int currentSongIndex;
    public static CircleImageView imgSong;
    public static TextView titleSong;
    public static TextView nameSinger;

    public interface ClickListener {
        void onItemClick(int position);
    }

    public SongAdapter(List<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    public SongAdapter() {
    }

    public SongAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        //holder.imgSong.setImageResource(song.getImage());
        titleSong.setText(song.getTitle());
        nameSinger.setText(song.getArtist());
        String imageUrl = song.getImage();
        Glide.with(context).load(imageUrl).into(imgSong);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DatabaseReference reff;
                int playlist_index = PlaylistAdapter.currentPlaylistIndex;
                reff = FirebaseDatabase.getInstance().getReference().child("Playlist").child(String.valueOf(playlist_index));
                if (song != null) {
                    PlaylistAdapter.playlistArrayList.get(playlist_index).getArrayList().add(song);
                    Toast.makeText(context, "Added to playlist", Toast.LENGTH_SHORT).show();
                }

                Playlist playlist = new Playlist(PlaylistAdapter.currentPlaylistIndex, PlaylistAdapter.playlistArrayList.get(PlaylistAdapter.currentPlaylistIndex).getNamePlaylist(), PlaylistAdapter.playlistArrayList.get(playlist_index).getArrayList());
                Map<String, Object> updates = new HashMap<>();
                updates.put("arrayList", playlist.getArrayList());
                reff.updateChildren(updates);*/
                playSong(view, "https://firebasestorage.googleapis.com/v0/b/application-music-player.appspot.com/o/audio%2FKhuat%20Loi%20-%20H-Kray?alt=media&token=c7732cdb-d443-441a-b7c3-5de1040cf9fd");
            }
        });
    }

    public void playSong(View v, String dataSource) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(dataSource);
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (songs != null) {
            return songs.size();
        }
        return 0;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        public SongViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.img_song);
            titleSong = itemView.findViewById(R.id.title_song);
            nameSinger = itemView.findViewById(R.id.name_singer);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }
}
