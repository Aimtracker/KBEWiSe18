package de.htw.ai.kbe.songsrx.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.bean.User;
import javassist.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUserList implements IUserList{
    private static InMemoryUserList instance = null;

    ObjectMapper objectMapper;
    File file;

    public static InMemoryUserList getInstance(){
        if(instance == null)
            instance = new InMemoryUserList(new File("/Users/dominikwegner/Documents/Dev/University/kbe/data/users.json"));
        return instance;
    }

    private InMemoryUserList(File file){
        objectMapper = new ObjectMapper();
        this.file = file;
    }

    @Override
    public User getUserById(String userId) throws NotFoundException {
        List<User> userList = getUsers();
        for(User u : userList){
            if(u.getUserId() == userId)
                return u;
        }
        throw new NotFoundException("No user with userId " + userId + " found!");
    }

    private List<User> getUsers(){
        List<User> songs = new ArrayList<>();
        try {
            return objectMapper.readValue(file, new TypeReference<List<Song>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load user file!");
        }

    }
}
