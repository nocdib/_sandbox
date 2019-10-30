package com.nocdib;

/*
 Create a program that implements a playlist for songs
 The program will have an Album class containing a list of songs.
 The albums will be stored in an ArrayList
 Songs from different albums can be added to the playlist and will appear in the list in the order
 they are added.
 Once the songs have been added to the playlist, create a menu of options to:-
 Quit,Skip forward to the next song, skip backwards to a previous song.  Replay the current song.
 List the songs in the playlist
 A song must exist in an album before it can be added to the playlist (so you can only play songs that
 you own).
 Hint:  To replay a song, consider what happened when we went back and forth from a city before we
 started tracking the direction we were going.
 As an optional extra, provide an option to remove the current song from the playlist
 (hint: listiterator.remove()
 */

public class Main {

    public static void main(String[] args) {

        Playlist hiphopPlaylist = new Playlist();

        Album readyToDie = new Album("Notorious B.I.G", "Ready To Die");
        Song warning = new Song("Warning", 300);
        Song juicy = new Song("Juicy", 300);
        Song everydayStruggle = new Song("Everyday Struggle", 300);
        readyToDie.addTrack(warning);
        readyToDie.addTrack(juicy);
        readyToDie.addTrack(everydayStruggle);

        Album enter36Chambers = new Album("Wu-Tang Clan", "Enter Da 36 Chambers");
        Song methodMan = new Song("Method Man", 400);
        Song protectYaNeck = new Song("Protect Ya Neck",400);
        Song bringDaRuckus = new Song("Bring Da Ruckus", 350);
        enter36Chambers.addTrack(methodMan);
        enter36Chambers.addTrack(protectYaNeck);
        enter36Chambers.addTrack(bringDaRuckus);
        System.out.println("----------------------------------");
        hiphopPlaylist.addTrack(enter36Chambers, methodMan);
        hiphopPlaylist.addTrack(readyToDie, warning);
        hiphopPlaylist.addTrack(enter36Chambers, protectYaNeck);
        hiphopPlaylist.addTrack(readyToDie, juicy);

        hiphopPlaylist.showMenu();
    }
}

