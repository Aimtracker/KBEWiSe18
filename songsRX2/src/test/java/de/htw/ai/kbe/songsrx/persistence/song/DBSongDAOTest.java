package de.htw.ai.kbe.songsrx.persistence.song;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.htw.ai.kbe.songsrx.bean.Song;
import javassist.NotFoundException;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;

public class DBSongDAOTest {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("song-test");;


    @Test
    public void saveSongShouldSaveSong() throws NotFoundException {
        DBSongDAO songPersistence = new DBSongDAO(emf);
        Song song1 = new Song.Builder("title").album("album").artist("artist").released(2019).build();
        Integer id = songPersistence.add(song1);

        Song songFromDB = songPersistence.getById(id);
        assertEquals(song1.getTitle(), songFromDB.getTitle());
        assertEquals(song1.getAlbum(), songFromDB.getAlbum());
        assertEquals(song1.getArtist(), songFromDB.getArtist());
        assertEquals(song1.getReleased(), songFromDB.getReleased());
    }

    @Test
    public void findAllSongsShouldReturnAllSongs(){
        DBSongDAO songPersistence = new DBSongDAO(emf);
        Collection<Song> songs = songPersistence.getAll();
        assertTrue(songs.size()>=1);
        Song songFromDB = songs.stream().findFirst().get();
        songMatchesExpected(songFromDB);

    }

    @Test
    public void findSongByIdShouldReturnSongWithId() throws NotFoundException {
        DBSongDAO songPersistence = new DBSongDAO(emf);
        Song song = songPersistence.getById(1);
        songMatchesExpected(song);
    }

    private void songMatchesExpected(Song song){
        assertEquals(new Integer(1), song.getId());
        assertEquals("title", song.getTitle());
        assertEquals("artist", song.getArtist());
        assertEquals("album", song.getAlbum());
        assertEquals(new Integer(2019), song.getReleased());
    }

}
