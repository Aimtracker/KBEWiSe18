package de.htw.ai.kbe.servlet.data;

import java.io.IOException;
import java.util.List;

import de.htw.ai.kbe.servlet.pojo.Song;

public interface IDataSource {

    void load(String filePath) throws IOException;

    Song addSong(Song newSong);

    void save() throws IOException;

    Song getSong(int id);

    List<Song> getAllSongs();

}