package com.hcmute.it.k20.app.musicplayer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {
    private int playlistIndex;
    private String namePlaylist;
    private List<Song> arrayList = new ArrayList<>();

    public Playlist() {
    }

    public Playlist(int playlistIndex, String namePlaylist, List<Song> arrayList) {
        this.playlistIndex = playlistIndex;
        this.namePlaylist = namePlaylist;
        this.arrayList = arrayList;
    }

    public int getPlaylistIndex() {
        return playlistIndex;
    }

    public void setPlaylistIndex(int playlistIndex) {
        this.playlistIndex = playlistIndex;
    }

    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }

    public List<Song> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<Song> arrayList) {
        this.arrayList = arrayList;
    }
}
