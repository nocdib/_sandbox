package com.nocdib;

import static java.lang.String.format;

public class Song {
    private String title;
    private int duration;

    public Song(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public String getDurationInMinutes() {
        return String.format("%d minutes, %d seconds", duration/60, duration%60);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title: " + title + '\'' +
                ", duration: " + duration + " (" + this.getDurationInMinutes() + ")" +
                '}';
    }
}
