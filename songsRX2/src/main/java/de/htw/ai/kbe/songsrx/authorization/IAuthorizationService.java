package de.htw.ai.kbe.songsrx.authorization;

public interface IAuthorizationService {
    public String authorize(String userId);
    public boolean isValidToken(String token);
    public String getUserIdByToken(String token);
}
