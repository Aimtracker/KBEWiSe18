package de.htw.ai.kbe.songsrx.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsrx.bean.Song;
import javassist.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InMemorySongList implements ISongList {
    private Map<Integer, Song> songs;
    private int idCounter = 0;

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
        //idCounter = defaultList.stream().filter(s -> s.getId() != null).map(Song::getId).max(Comparator.naturalOrder()).orElse(-1)+1;
    }


    @Override
    public List<Song> getAll() {
        return new ArrayList<>(songs.values());
    }

    @Override
    public Song getById(int id) throws NotFoundException {
        Song song = songs.get(id);
        if(song == null)
            throw new NotFoundException("deine mutter");
        return song;
    }

    @Override
    public void add(Song song) {
        song.setId(idCounter++);
        songs.put(song.getId(), song);
    }

    @Override
    public void update(Song song) throws NotFoundException {
        if(song.getId() == null || !songs.containsKey(song.getId()))
            throw new NotFoundException("Song not found!");
        songs.put(song.getId(), song);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        if(!songs.containsKey(id))
            throw new NotFoundException("Song not found!");
        songs.remove(id);
    }

    static List<Song> getDefaultList(){
        List<Song> songs = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try(InputStream inputStream = InMemorySongList.class.getResourceAsStream("songs.json")){
            songs = objectMapper.readValue(inputStream,new TypeReference<List<Song>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }
}