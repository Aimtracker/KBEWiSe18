package de.htw.ai.kbe.songsrx.persistence.user;

import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.bean.User;
import de.htw.ai.kbe.songsrx.persistence.song.TestInMemorySong;
import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestInMemoryUser implements IUser {
    public static List<User> storage;
    public TestInMemoryUser(){
        storage = new ArrayList<User>();
        init();
    }

    private void init(){
        User user = new User.Builder("mmuster").firstName("maxime").lastName("muster").build();
        storage.add(user);
    }

    @Override
    public User getUserById(String userId) throws NotFoundException {
        for(User u : storage){
            if(u.getUserId().equals(userId)){
                return u;
            }

        }
        throw new NotFoundException("No user with userId " + userId + " found!");
    }
}
