package com.example.foregroundservice;

import java.io.Serializable;

public class Song implements Serializable {
    private String title;
    private String singer;
    private Integer image;
    private Integer resource;

    public Song(String title, String singer, Integer image, Integer resource) {
        this.title = title;
        this.singer = singer;
        this.image = image;
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }
}
