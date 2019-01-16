package de.htw.ai.kbe.songsrx.persistence.songlist;

import de.htw.ai.kbe.songsrx.bean.SongList;
import de.htw.ai.kbe.songsrx.bean.User;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;
import java.util.NoSuchElementException;

public class DBSongListDAO implements ISongList {

    EntityManagerFactory emf;

    @Inject
    public DBSongListDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public List<SongList> getListsOfUser(User user) {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<SongList> query = em.createQuery("SELECT sl FROM SongList sl WHERE sl.owner = :user", SongList.class);
            query.setParameter("user", user);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public SongList getListById(int listId){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SongList> query = em.createQuery("SELECT l FROM SongList l WHERE l.id = :id", SongList.class);
            query.setParameter("id", listId);
            return query.getSingleResult();
        }catch (NoResultException e){
            throw new NoSuchElementException("No list with id " + listId + " found");
        }
    }

    @Override
    public SongList getListByIdAndUser(int listId, User user) throws NoSuchElementException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SongList> query = em.createQuery("SELECT l FROM SongList l WHERE l.id = :id AND l.owner = :user", SongList.class);
            query.setParameter("id", listId);
            query.setParameter("user", user);
            return query.getSingleResult();
        }catch (NoResultException e){
            throw new NoSuchElementException("No list with id " + listId + " for user '" + user.getId() + " found");
        }
    }

    @Override
    public void delete(int listId) throws NoSuchElementException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SongList> query = em.createQuery("SELECT l FROM SongList l WHERE l.id = :id", SongList.class);
            query.setParameter("id", listId);
            SongList list = query.getSingleResult();
            em.getTransaction().begin();
            em.remove(list);
            em.getTransaction().commit();
        } catch (NoResultException e){
            throw new NoSuchElementException("No list with id " + listId + " found");
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        }finally {
            em.close();
        }
    }

//     Old Solution
//    @Override
//    public void delete(SongList list) throws NoSuchElementException {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.remove(list);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            em.getTransaction().rollback();
//            throw new PersistenceException("Could not persist entity: " + e.toString());
//        }finally {
//            em.close();
//        }
//    }

    @Override
    public void persist(SongList list) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(list);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        }
    }
}
