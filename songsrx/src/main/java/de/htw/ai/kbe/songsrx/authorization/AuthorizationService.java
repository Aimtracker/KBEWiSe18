package de.htw.ai.kbe.songsrx.authorization;

import de.htw.ai.kbe.songsrx.bean.User;
import de.htw.ai.kbe.songsrx.persistence.IUserList;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

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
            throw new ForbiddenException("User does not exist!");
        }

        String token = generateToken();
        userTokens.put(userId, token);
        return token;
    }

    public String generateToken(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        return token;
    }

    @Override
    public boolean isValidToken(String token){
        return userTokens.containsValue(token);
    }
}
