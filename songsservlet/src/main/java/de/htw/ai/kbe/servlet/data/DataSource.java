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

import org.apache.log4j.Logger;

import de.htw.ai.kbe.servlet.marshalling.IMarshaller;
import de.htw.ai.kbe.servlet.marshalling.MarshallingException;
import de.htw.ai.kbe.servlet.marshalling.impl.MarshallerFactory;
import de.htw.ai.kbe.servlet.pojo.Song;
import de.htw.ai.kbe.servlet.utils.Constants;

public class DataSource implements IDataSource {

    private Map<Integer, Song> songs;
    private static Logger log = Logger.getLogger(DataSource.class);
    private int idCounter;
    private IMarshaller marshaller;
    private String filePath;

    public DataSource() {
        this.songs = new HashMap<>();
    }

    @Override
    public synchronized void load(String filePath) throws IOException {
        log.info("Loading json data from " + filePath);
        this.filePath = filePath;
        List<Song> songList = new ArrayList<>();
        try {
            if (checkFileForExistance(this.filePath)) {
                InputStream is = new BufferedInputStream(new FileInputStream(this.filePath));
                this.marshaller = MarshallerFactory.getInstance().getMarshaller(Constants.CONTENTTYPE_JSON);
                songList = marshaller.readSongsFromStream(is);
            }
        } catch (IllegalArgumentException e) {
            // Can not be thrown at this point
            // But if so, something's really wrong
            log.fatal(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (MarshallingException e) {
            log.fatal("Failed to read json data! " + e.getMessage());
            throw new IOException(e.getMessage());
        }
        this.songs = songList.stream().collect(Collectors.toMap(Song::getId, Function.identity()));
        this.idCounter = songs.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }

    private boolean checkFileForExistance(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            log.warn("File does not exist. Creating new file");
            f.getParentFile().mkdirs();
            f.createNewFile();
            return false;
        }
        return true;
    }

    @Override
    public synchronized Song addSong(Song newSong) {
        newSong.setId(idCounter++);
        this.songs.put(newSong.getId(), newSong);
        return newSong;
    }

    @Override
    public synchronized void save() throws IOException {
        log.info("Saving data to file " + this.filePath);
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(this.filePath))) {
            this.marshaller.writeSongsToStream(getAllSongs(), os);
            log.info("Sucessfully saved data");
        } catch (MarshallingException e) {
            log.warn(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public synchronized Song getSong(int id) {
        log.info("getSong(" + id + ")");
        return songs.get(id);
    }

    @Override
    public synchronized List<Song> getAllSongs() {
        return new ArrayList<>(songs.values());
    }
}
