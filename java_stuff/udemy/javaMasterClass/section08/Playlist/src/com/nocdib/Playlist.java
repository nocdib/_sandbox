package com.nocdib;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Playlist {
    LinkedList<Song> playlist;
    ListIterator<Song> playlistIterator;
    int currentTrackIndex;

    public Playlist() {
        this.playlist = new LinkedList<>();
        this.playlistIterator = playlist.listIterator();
        this.currentTrackIndex = 0;
    }

    public LinkedList<Song> getPlaylist() {
        return playlist;
    }

    public ListIterator<Song> getPlaylistIterator() {
        return playlistIterator;
    }

    public void showMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an option: \n" +
                            "0) Start From The Beginning\n" +
                            "1) Remove Track From Playlist\n" +
                            "2) List Tracks\n" +
                            "3) Next Track\n" +
                            "4) Previous Track\n" +
                            "5) Replay Current Track\n" +
                            "6) Quit\n"
                            );
        int menuSelection = scanner.nextInt();
        switch(menuSelection){
            case 0:
                this.startFromBeginning();
                break;
            case 1:
                this.removeTrack();
                break;
            case 2:
                this.listTracks();
                break;
            case 3:
                this.nextTrack();
                break;
            case 4:
                this.previousTrack();
                break;
            case 5:
                this.replayTrack();
                break;
            case 6:
                this.quit();
                break;
            default:
                this.showMenu();
        }
    }

    public void startFromBeginning(){
        while(playlistIterator.hasPrevious()){
            playlistIterator.previous();
        }
        this.nextTrack();
    }

    public void addTrack(Album album, Song song){
        if(album.getTracks().contains(song)){
            playlistIterator.add(song);
            System.out.println("Added Track: " + song.getTitle());
        } else {
            System.out.printf("The song \"%s\" is not found on the album \"%s\"\n", song.getTitle(), album.getTitle());
        }
    }

    public void removeTrack() {
        if (playlistIterator.hasPrevious()){
            System.out.println("Removing Track: " + playlistIterator.previous());
            playlistIterator.remove();
        } else {
            System.out.println("Removing Track: " + playlistIterator.next());
            playlistIterator.remove();
        }
        showMenu();
    }

    public void listTracks(){
        System.out.println("Playlist Tracks: \n" + playlist);
        showMenu();
    }

    public void nextTrack(){
        if(playlistIterator.hasNext()){
            System.out.println("Playing : " + playlistIterator.next().getTitle());
        } else {
            System.out.println("There is no next track.");
        }
        showMenu();
    }

    public void previousTrack(){
        if(playlistIterator.hasPrevious()){
            System.out.println("Playing: " + playlistIterator.previous().getTitle());
        } else {
            System.out.println("There is no previous track.");
        }
        showMenu();
    }

    public void replayTrack(){
        if(playlistIterator.hasPrevious()){
            playlistIterator.previous();
        }
        System.out.println("Replaying: " + playlistIterator.next());
        showMenu();
    }

    public void quit(){
        System.out.println("Exiting playlist...");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlist=" + playlist +
                '}';
    }
}
