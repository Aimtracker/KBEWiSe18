package de.htw.ai.kbe.songsrx.persistence.song;

import de.htw.ai.kbe.songsrx.bean.Song;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

public class DBSongDAO implements ISong {

    @Inject
    private EntityManagerFactory emf;

    @Override
    public List<Song> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Song> query = em.createQuery("SELECT s FROM songs s", Song.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Song getById(int id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Song entity = null;
        try {
            entity = em.find(Song.class, id);
        } finally {
            em.close();
        }
        if (entity == null) {
            throw new NotFoundException("No Song with id: " + id + " found!");
        }
        return entity;
    }

    @Override
    public Integer add(Song song) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Song songPOST = new Song.Builder(song.getTitle()).artist(song.getArtist()).album(song.getAlbum()).released(song.getReleased()).build();
            em.persist(songPOST);
            transaction.commit();
            return songPOST.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding contact: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Song song) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Song updatedSong = em.find(Song.class, song.getId());
            updatedSong.setAlbum(song.getAlbum());
            updatedSong.setArtist(song.getArtist());
            updatedSong.setReleased(song.getReleased());
            updatedSong.setTitle(song.getTitle());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        }
    }
}