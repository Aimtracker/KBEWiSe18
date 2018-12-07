package de.htw.ai.kbe.songsrx.authorization;

import de.htw.ai.kbe.songsrx.bean.User;
import de.htw.ai.kbe.songsrx.persistence.IUserList;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationService implements IAuthorizationService{
    @Inject
    private IUserList userList;

    private Map<String, String> userTokens;

    public AuthorizationService(){
        userTokens = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public synchronized String authorize(String userId) {
        User user;
        try {
            user = userList.getUserById(userId);
        } catch (NotFoundException e) {
            throw new NotAuthorizedException("User does not exist!");
        }

        String token = generateToken();
        userTokens.put(userId, token);
        return token;
    }

    public String generateToken(){
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("SHA-256");
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            String token = bytes.toString();
            return token;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isValidToken(String token){
        return userTokens.containsValue(token);
    }
}
