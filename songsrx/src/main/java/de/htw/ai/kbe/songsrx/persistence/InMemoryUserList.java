package de.htw.ai.kbe.songsrx.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsrx.bean.User;
import javassist.NotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class InMemoryUserList implements IUserList{

    ObjectMapper objectMapper;
    File file;

    public InMemoryUserList(){
        this.file = new File("/home/s0558234/Downloads/apache-tomcat-8.5.31/webapps/data/users.json");
    }

    @Override
    public User getUserById(String userId) throws NotFoundException {
        List<User> userList = getUsers();
        for(User u : userList){
            if(u.getUserId().equals(userId)){
                return u;
            }

        }
        throw new NotFoundException("No user with userId " + userId + " found!");
    }

    private List<User> getUsers(){
        objectMapper = new ObjectMapper();

        try(InputStream inputStream = new FileInputStream(file)){
            return objectMapper.readValue(inputStream, new TypeReference<List<User>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load user file!");
        }

    }
}
