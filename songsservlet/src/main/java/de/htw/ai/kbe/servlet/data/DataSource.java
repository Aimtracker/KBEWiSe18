package de.htw.ai.kbe.servlet.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.htw.ai.kbe.servlet.marshalling.IMarshaller;
import de.htw.ai.kbe.servlet.marshalling.MarshallerFactory;
import de.htw.ai.kbe.servlet.pojo.Song;

public class DataSource{

    private Map<Integer, Song> songs;
    private int idCounter;
    private IMarshaller marshaller;
    private String filePath;

    /**
     * Constructor for datasource
     *
     */
    public DataSource() {
        this.songs = new HashMap<>();
    }

    /* (non-Javadoc)
     * @see de.htw.ai.kbe.servlet.data.IDataSource#load(java.lang.String)
     */
    public synchronized void load(String filePath) throws IOException {
        System.out.println("Loading json data from " + filePath);
        this.filePath = filePath;
        List<Song> songList = new ArrayList<>();
        try {
            if (checkFileForExistance(this.filePath)) {
                InputStream is = new BufferedInputStream(new FileInputStream(this.filePath));
                this.marshaller = MarshallerFactory.getInstance().getMarshaller("application/json");
                songList = marshaller.readSongsFromStream(is);
            }
        } catch (IllegalArgumentException e) {
            // Can not be thrown at this point
            // But if so, something's really wrong
            System.err.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        this.songs = songList.stream().collect(Collectors.toMap(Song::getId, Function.identity()));
        this.idCounter = songs.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }

    private boolean checkFileForExistance(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            System.out.println("File does not exist. Creating new file");
            f.getParentFile().mkdirs();
            f.createNewFile();
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see de.htw.ai.kbe.servlet.data.IDataSource#addSong(de.htw.ai.kbe.servlet.pojo.Song)
     */
    public synchronized Song addSong(Song newSong) {
        newSong.setId(idCounter++);
        this.songs.put(newSong.getId(), newSong);
        return newSong;
    }

    /* (non-Javadoc)
     * @see de.htw.ai.kbe.servlet.data.IDataSource#save()
     */
    public synchronized void save() throws IOException {
        System.out.println("Saving data to file " + this.filePath);
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(this.filePath))) {
            this.marshaller.writeSongsToStream(getAllSongs(), os);
            System.out.println("Sucessfully saved data");
        }
    }

    /* (non-Javadoc)
     * @see de.htw.ai.kbe.servlet.data.IDataSource#getSong(int)
     */
    public synchronized Song getSong(int id) {
        System.out.println("getSong(" + id + ")");
        return songs.get(id);
    }

    /* (non-Javadoc)
     * @see de.htw.ai.kbe.servlet.data.IDataSource#getAllSongs()
     */
    public synchronized List<Song> getAllSongs() {
        return new ArrayList<>(songs.values());
    }
}