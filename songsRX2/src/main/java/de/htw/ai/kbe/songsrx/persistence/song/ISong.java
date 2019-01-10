package de.htw.ai.kbe.songsrx.persistence.song;

import de.htw.ai.kbe.songsrx.bean.Song;
import javassist.NotFoundException;

import java.util.List;

public interface ISong {
    List<Song> getAll();

    Song getById(int id) throws NotFoundException;

    Integer add(Song song);

    void update(Song song) throws NotFoundException;
}