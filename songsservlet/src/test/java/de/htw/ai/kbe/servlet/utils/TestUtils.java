package de.htw.ai.kbe.servlet.utils;

import de.htw.ai.kbe.servlet.pojo.Song;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestUtils {

    public static void assertSong(Song song, int id, String artist, String album, String title, int released) {
        assertEquals(artist, song.getArtist());
        assertEquals(album, song.getAlbum());
        assertEquals(id, song.getId().intValue());
        assertEquals(title, song.getTitle());
        assertEquals(released, song.getReleased().intValue());
    }

    public static List<Song> getTestSongs() {
        List<Song> songList = new ArrayList<>();

        Song song1 = new Song();
        songList.add(song1);

        song1.setAlbum("Unknown");
        song1.setArtist("ILLENIUM");
        song1.setId(1);
        song1.setReleased(2018);
        song1.setTitle("God Damnit");

        Song song2 = new Song();
        songList.add(song2);

        song2.setAlbum("Before the Storm");
        song2.setArtist("Darude");
        song2.setId(2);
        song2.setReleased(2000);
        song2.setTitle("Sandstorm");

        return songList;
    }

    public static Song getTestSong() {
        Song song = new Song();
        song.setId(3);
        song.setArtist("Tyga");
        song.setAlbum("Unknown");
        song.setTitle("Taste");
        song.setReleased(2018);
        return song;
    }

    public static String getJsonPayload() {
        return "{\n  \"title\" : \"Taste\",\n  \"artist\" : \"Tyga\",\n  \"album\" : \"Unknown\",\n  \"released\" : 2018\n}";
    }

}
