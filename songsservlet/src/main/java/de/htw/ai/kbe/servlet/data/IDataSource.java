package de.htw.ai.kbe.servlet.data;

import java.io.IOException;
import java.util.List;

import de.htw.ai.kbe.servlet.pojo.Song;

public interface IDataSource {

    /**
     * Load content of db-file into memory.<br>
     * If the given file does not exist, a new empty one will be created
     *
     * @param filePath
     *            location of "db"
     * @throws IOException
     *             on failed load operation (i.e. corrupt data file)
     */
    void load(String filePath) throws IOException;

    /**
     * Add song to in-memory data
     *
     * @param newSong
     *            song to add
     * @return added Song (with set id)
     */
    Song addSong(Song newSong);

    /**
     * Save in-memory data to file
     *
     * @throws IOException
     */
    void save() throws IOException;

    /**
     * Get song from in-memory db by id
     *
     * @param id
     *            of song
     * @return Song or null
     */
    Song getSong(int id);

    /**
     * @return all Songs
     */
    List<Song> getAllSongs();

}