//package de.htw.ai.kbe.songsrx.service;
//
//import de.htw.ai.kbe.songsrx.bean.Song;
//import javax.inject.Singleton;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.junit.Assert;
//import org.junit.Test;
//
//import javax.inject.Singleton;
//import javax.ws.rs.core.Application;
//
//public class SongWebServiceTest extends JerseyTest {
//
//    @Override
//    protected Application configure() {
//        return new ResourceConfig(SongsResource.class).register(
//                new AbstractBinder() {
//                    @Override
//                    protected void configure() {
//                        bind(InMemorySongsPersistence.class).to(ISongPersistence.class).in(Singleton.class);
//                    }
//                });
//    }
//
//    // -------------
//    // get Tests
//    // -------------
//
////    @Test
////    public void getSongWithValidIdShouldReturnSong() {
////        Song song = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
////        System.out.println(song);
////        Assert.assertEquals(1, song.getId().intValue());
////    }
////
////    @Test
////    public void getSongWithStringIdShouldReturn404() {
////        Response response = target("/songs/stringAndNoId").request().get();
////        Assert.assertEquals(404, response.getStatus());
////    }
////
////    @Test
////    public void getSongWithNonExistingIdShouldReturn404(){
////        Response response = target("/songs/999").request().get();
////        Assert.assertEquals(404, response.getStatus());
////    }
//
//    // -------------
//    // put Tests
//    // -------------
//
//    // JSON
//    @Test
//    public void updateSongJsonWithNonExistingIdShouldReturn404() {
//        Song song = getTestSongWithoutId();
//
//        Response response = target("/songs/999").request().put(Entity.json(song));
//        Assert.assertEquals(404, response.getStatus());
//    }
//
//    // XML
//    @Test
//    public void updateSongXmlWithNonExistingIdShouldReturn404() {
//        Song song = getTestSongWithoutId();
//
//        Response response = target("/songs/999").request().put(Entity.xml(song));
//        Assert.assertEquals(404, response.getStatus());
//    }
//
//    // JSON
//    @Test
//    public void updateSongJsonShouldReturn204AndUpdatedSong() {
//        Song songPutTest = getTestSongWithIdOne();
//
//        // the original song
//        Song songGetTest = target("/songs/1").request(MediaType.APPLICATION_JSON).get(Song.class);
//        assertSong(songGetTest, 1, "Justin Timberlake", "Trolls", "Can’t Stop the Feeling", 2016);
//
//        Response response = target("/songs/1").request().put(Entity.json(songPutTest));
//        Assert.assertEquals(204, response.getStatus());
//
//        // the new overwritten song
//        songGetTest = target("/songs/1").request(MediaType.APPLICATION_JSON).get(Song.class);
//        assertSong(songGetTest, 1, "Jimi Hendrix", "Axis: Bold As Love", "Little Wing", 1967);
//    }
//
//    // XML
//    @Test
//    public void updateSongXmlShouldReturn204AndUpdatedSong() {
//        Song songPutTest = getTestSongWithIdOne();
//
//        // the original song
//        Song songGetTest = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
//        assertSong(songGetTest, 1, "Justin Timberlake", "Trolls", "Can’t Stop the Feeling", 2016);
//
//        Response response = target("/songs/1").request().put(Entity.xml(songPutTest));
//        Assert.assertEquals(204, response.getStatus());
//
//        // the new overwritten song
//        songGetTest = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
//        assertSong(songGetTest, 1, "Jimi Hendrix", "Axis: Bold As Love", "Little Wing", 1967);
//    }
//
//    // JSON
//    @Test
//    public void updateSongJsonWithDifferentIdInBodyShouldReturn400() {
//        Song song = getTestSongWithIdOne();
//
//        Response response = target("/songs/2").request().put(Entity.json(song));
//        Assert.assertEquals(400, response.getStatus());
//    }
//
//    // XML
//    @Test
//    public void updateSongXmlWithDifferentIdInBodyShouldReturn400() {
//        Song song = getTestSongWithIdOne();
//
//        Response response = target("/songs/2").request().put(Entity.xml(song));
//        Assert.assertEquals(400, response.getStatus());
//    }
//
//    // JSON
//    @Test
//    public void updateSongJsonWithNoIdWithinPayloadShouldReturn204AndUpdatedSong() {
//        Song songPutTest = getTestSongWithoutId();
//
//        // the original song
//        Song songGetTest = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
//        assertSong(songGetTest, 1, "Justin Timberlake", "Trolls", "Can’t Stop the Feeling", 2016);
//
//        Response response = target("/songs/1").request().put(Entity.json(songPutTest));
//        Assert.assertEquals(204, response.getStatus());
//
//        // the new overwritten song
//        songGetTest = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
//        assertSong(songGetTest, 1, "Jimi Hendrix", "Axis: Bold As Love", "Little Wing", 1967);
//    }
//
//    // XML
//    @Test
//    public void updateSongXmlWithNoIdWithinPayloadShouldReturn204AndUpdatedSong() {
//        Song songPutTest = getTestSongWithoutId();
//
//        // the original song
//        Song songGetTest = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
//        assertSong(songGetTest, 1, "Justin Timberlake", "Trolls", "Can’t Stop the Feeling", 2016);
//
//        Response response = target("/songs/1").request().put(Entity.xml(songPutTest));
//        Assert.assertEquals(204, response.getStatus());
//
//        // the new overwritten song
//        songGetTest = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
//        assertSong(songGetTest, 1, "Jimi Hendrix", "Axis: Bold As Love", "Little Wing", 1967);
//    }
//
//    @Test
//    public void updateSongWithWrongParamaterShouldReturn404() {
//        Song songPutTest = getTestSongWithoutId();
//
//        Response response = target("/songs/garbage").request().put(Entity.xml(songPutTest));
//        Assert.assertEquals(404, response.getStatus());
//    }
//
//    @Test
//    public void updateSongWithWrongPayloadShouldReturn400() {
//        Song songPutTest = getTestSongWithWrongPayload();
//
//        Response response = target("/songs/1").request().put(Entity.xml(songPutTest));
//        Assert.assertEquals(400, response.getStatus());
//    }
//
//    @Test
//    public void updateSongJsonJustWithTitleShouldReturn204AndUpdatedSongs() {
//        Song songPutTest = getTestSongWithJustATitle();
//
//        // the original song
//        Song songGetTest = target("/songs/1").request(MediaType.APPLICATION_JSON).get(Song.class);
//        assertSong(songGetTest, 1, "Justin Timberlake", "Trolls", "Can’t Stop the Feeling", 2016);
//
//        Response response = target("/songs/1").request().put(Entity.xml(songPutTest));
//        Assert.assertEquals(204, response.getStatus());
//
//        // the new overwritten song
//        songGetTest = target("/songs/1").request(MediaType.APPLICATION_JSON).get(Song.class);
//        assertSong(songGetTest, 1, null, null, "Supermix 2000", null);
//    }
//}
//
