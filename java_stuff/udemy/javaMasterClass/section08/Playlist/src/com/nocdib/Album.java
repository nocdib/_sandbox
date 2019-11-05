package com.nocdib;

import java.util.ArrayList;

public class Album {
    private String artist;
    private String title;
    private ArrayList<Song> tracks;

    public Album(String artist, String title) {
        this.artist = artist;
        this.title = title;
        this.tracks = new ArrayList<>();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Song> getTracks() {
        return tracks;
    }

    public void addTrack(Song track) {
        this.tracks.add(track);
    }

    public void removeTrack(Song track) {
        this.tracks.remove(track);
    }

    @Override
    public String toString() {
        return "Album{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", tracks=" + tracks +
                '}';
    }
}
