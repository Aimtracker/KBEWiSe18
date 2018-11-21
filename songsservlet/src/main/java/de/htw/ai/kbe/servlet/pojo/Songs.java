package de.htw.ai.kbe.servlet.pojo;

import java.util.List;


import de.htw.ai.kbe.servlet.utils.ListEntriesToString;

public class Songs {
    private List<Song> songList;

    public List<Song> getSongList() {
        return this.songList;
    }

    public void setSongList(List<Song> song) {
        this.songList = song;
    }

    @Override
    public String toString() {
        return ListEntriesToString.toString(this.songList,"\n");
    }
}