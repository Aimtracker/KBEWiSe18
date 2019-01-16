package de.htw.ai.kbe.songsrx.persistence.song;

import de.htw.ai.kbe.songsrx.bean.Song;
import javassist.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestInMemorySong implements ISong {
    private static Map<Integer, Song> storage;

    public TestInMemorySong(){
        storage = new ConcurrentHashMap<Integer, Song>();
        init();
    }

    private static void init() {
        Song song = new Song.Builder("title").album("album").artist("artist").released(2019).build();
        song.setId(1);
        storage.put(song.getId(), song);
    }

    @Override
    public List<Song> getAll() {
        return null;
    }

    @Override
    public Song getById(int id) throws NotFoundException {
        Song song = storage.get(id);
        if (song == null)
            throw new NotFoundException("Song not found!");
        return song;
    }

    @Override
    public Integer add(Song song) {
        return null;
    }

    @Override
    public void update(Song song) throws NotFoundException {
        if (song.getId() == null || !storage.containsKey(song.getId()))
            throw new NotFoundException("Song not found!");
        storage.put(song.getId(), song);
    }
}
