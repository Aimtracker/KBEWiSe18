package de.htw.ai.kbe.songsrx.persistence;

import de.htw.ai.kbe.songsrx.bean.User;
import javassist.NotFoundException;

public interface IUserList {
    public User getUserById(String userId) throws NotFoundException;
}
