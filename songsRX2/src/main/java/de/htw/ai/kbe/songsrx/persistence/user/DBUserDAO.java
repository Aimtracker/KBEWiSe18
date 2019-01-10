package de.htw.ai.kbe.songsrx.persistence.user;

import de.htw.ai.kbe.songsrx.bean.User;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DBUserDAO implements IUser {
    @Inject
    EntityManagerFactory emf;

    @Override
    public User getUserById(String userId) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        User entity = null;
        try{
            entity = em.find(User.class, userId);
        }finally {
            em.close();
        }
        if(entity == null){
            throw new NotFoundException("User with userId: " + userId + "not found!");
        }
        return entity;
    }
}
