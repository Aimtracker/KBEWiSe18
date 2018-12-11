package de.htw.ai.kbe.songsrx.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsrx.bean.Song;
import javassist.NotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InMemorySongList implements ISongList {
    private Map<Integer, Song> songs;
    private int idCounter = 0;
    private static ObjectMapper objectMapper;

    public InMemorySongList(){
        this(getDefaultList());
    }

    public InMemorySongList(List<Song> defaultList) {
        songs = Collections.synchronizedMap(new HashMap<>());
        if (defaultList == null){
            defaultList=new ArrayList<>();
        }
        defaultList.stream().filter(s -> s.getId() != null).sorted(Comparator.comparing(Song::getId)).forEach(s -> songs.put(s.getId(),s));
        for(Song s : songs.values()){
            if(s.getId() > idCounter)
                idCounter = s.getId();
        }
    }


    @Override
    public synchronized List<Song> getAll() {
        return new ArrayList<>(songs.values());
    }

    @Override
    public synchronized Song getById(int id) throws NotFoundException {
        Song song = songs.get(id);
        if(song == null)
            throw new NotFoundException("Song not found!");
        return song;
    }

    @Override
    public synchronized Integer add(Song song) {
        song.setId(++idCounter);
        songs.put(song.getId(), song);
        System.err.println(idCounter);
        return idCounter;
    }

    @Override
    public synchronized void update(Song song) throws NotFoundException {
        if(song.getId() == null || !songs.containsKey(song.getId()))
            throw new NotFoundException("Song not found!");
        songs.put(song.getId(), song);
    }

    @Override
    public synchronized void delete(int id) throws NotFoundException {
        if(!songs.containsKey(id))
            throw new NotFoundException("Song not found!");
        songs.remove(id);
    }

    static List<Song> getDefaultList(){
        objectMapper = new ObjectMapper();
        try(InputStream inputStream = new FileInputStream(new File("/Users/dominikwegner/Documents/Dev/University/kbe/data/songs.json"))){
            return objectMapper.readValue(inputStream,new TypeReference<List<Song>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load songs file!");
        }
    }
}
