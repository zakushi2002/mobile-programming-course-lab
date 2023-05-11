package com.hcmute.it.k20.app.musicplayer.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hcmute.it.k20.app.musicplayer.MainActivity;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.adapter.SongAdapter;
import com.hcmute.it.k20.app.musicplayer.entity.Song;
import com.hcmute.it.k20.app.musicplayer.service.Mp3Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PopularSongFragment extends Fragment implements SongAdapter.OnItemClickListener{
    private SongAdapter adapter;
    private List<Song> songArrayList;
    private Song songs;
    private MainActivity mainActivity;
    public static int selectedIndex;
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage mStorage;
    public PopularSongFragment() {
        // Required empty public constructor
    }
    private static RecyclerView recyclerViewSongs;
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("send_data_to_activity"));
        songArrayList = new ArrayList<>();
        recyclerViewSongs = v.findViewById(R.id.rcv_songs);
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerViewSongs.setLayoutManager(linearLayoutManager);*/
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("song");
        return v;
    }

    private class SongHolder extends RecyclerView.ViewHolder{
        View mview;
        private SongHolder(View itemview){
            super(itemview);
            mview = itemview;
        }
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) {
                return;
            }
            songs = (Song) bundle.get("object_song");
            int actionMusic = bundle.getInt("action_music");
        }
    };
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        } else {
            throw new RuntimeException("MainActivity is required for this fragment");
        }
    }
    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        Song selectedSong = songArrayList.get(position);

        StorageReference imageRef = mStorage.getReference("images/"+selectedSong.getTitle()+".jpg");
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(String.valueOf(SongAdapter.currentSongIndex)).removeValue();
                Toast.makeText(getActivity(), "Song deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendActionToService(int action) {
        Intent intent = new Intent(mainActivity, Mp3Service.class);
        intent.putExtra("action_music", action);

        mainActivity.startService(intent);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReferenceFromUrl("https://application-music-player-default-rtdb.firebaseio.com");
        DatabaseReference projectDetailsRef = rootRef.child("/song");
        projectDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                songArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Get songs from firebase
                    Song song = dataSnapshot.getValue(Song.class);
                    songArrayList.add(song);
                    adapter = new SongAdapter(songArrayList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    recyclerViewSongs.setLayoutManager(linearLayoutManager);
                    // setting our adapter to recycler view.
                    recyclerViewSongs.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if(songs!=null){
            selectedIndex=Mp3Service.currentSongIndex;
        }
//        Toast.makeText(mainActivity, "Index on resume: "+selectedIndex, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }
}