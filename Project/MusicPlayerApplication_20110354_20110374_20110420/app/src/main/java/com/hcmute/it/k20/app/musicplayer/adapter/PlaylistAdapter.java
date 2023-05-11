package com.hcmute.it.k20.app.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.activity.PopularSongActivity;
import com.hcmute.it.k20.app.musicplayer.entity.Playlist;
import com.hcmute.it.k20.app.musicplayer.service.Mp3Service;

import java.util.List;


public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder>{

    public static List<Playlist> playlistArrayList;
    private Context context;
    public static TextView tvPlaylistName;
//    public static ImageView img_song;
    private ClickListener clickListener;

    public static int currentPlaylistIndex;

    private ImageView add_song_to_playlist;
    public interface ClickListener {
        void onItemClick(int position);
    }
    public PlaylistAdapter(List<Playlist> playlistArrayList, Context context) {
        this.playlistArrayList = playlistArrayList;
        this.context = context;
    }

    public PlaylistAdapter() {
    }

    public PlaylistAdapter(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Playlist playlist = playlistArrayList.get(position);
        tvPlaylistName.setText(playlist.getNamePlaylist());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPlaylistIndex = position;
                PopularSongActivity.currentPlaylistIndex = currentPlaylistIndex;
                Intent intent = new Intent(context, PopularSongActivity.class);
                context.startActivity(intent);
            }
        });

    }
    public int getItemCount() {
        if(playlistArrayList != null){
            return playlistArrayList.size();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            // tvPlaylistName = itemView.findViewById(R.id.tv_playlistName_rv);
//            add_song_to_playlist = itemView.findViewById(R.id.add_playlist);

        }
    }

    private void sendActionToService(int action) {
        Intent intent = new Intent(context, Mp3Service.class);
        intent.putExtra("action_music", action);
        context.startService(intent);
    }

}
