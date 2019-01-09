package de.htw.ai.kbe.songsrx.persistence;

import de.htw.ai.kbe.songsrx.bean.User;
import javassist.NotFoundException;

public interface IUser {
    public User getUserById(String userId) throws NotFoundException;
}
