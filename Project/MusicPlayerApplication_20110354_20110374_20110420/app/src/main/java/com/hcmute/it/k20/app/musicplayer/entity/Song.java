package com.hcmute.it.k20.app.musicplayer.entity;

import java.io.Serializable;

public class Song implements Serializable {
    private Long id;
    private String title;
    private String artist;
    private String image;
    private String storageUrl;

    public Song() {
    }

    public Song(String title, String artist, String image, String storageUrl) {
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.storageUrl = storageUrl;
    }

    public Song(Long id, String title, String artist, String image, String storageUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.storageUrl = storageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Song(String title, String artist, String image) {
        this.title = title;
        this.artist = artist;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }
}
