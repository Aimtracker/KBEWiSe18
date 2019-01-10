//package de.htw.ai.kbe.songsrx.service;
//
//import de.htw.ai.kbe.songsrx.bean.Song;
//import de.htw.ai.kbe.songsrx.persistence.song.ISong;
//import de.htw.ai.kbe.songsrx.persistence.song.InMemorySong;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.inject.Singleton;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.HttpHeaders;
//
//import javax.ws.rs.core.Response;
//
//public class SongWebServiceTest extends JerseyTest {
//
//    @Override
//    protected Application configure() {
//        return new ResourceConfig(SongWebService.class).register(new AbstractBinder() {
//
//            @Override
//            protected void configure() {
//                bind(InMemorySong.class).to(ISong.class).in(Singleton.class);
//
//            }
//        });
//    }
//
//    // Testcases für Put
//    @Test
//    public void putWithNonExistingIdXML() {
//        Song s = new Song.Builder("XML Song").artist("XML Artist").album("XML Album").released(2018).build();
//        Response response = target("/songs/1337").request().header(HttpHeaders.AUTHORIZATION, "mmuster").put(Entity.xml(s));
//        assertEquals(404, response.getStatus());
//    }
//
//    @Test
//    public void putWithNonExistingIdJSON() {
//        Song s = new Song.Builder("JSON Song").artist("JSON Artist").album("JSON Album").released(2018).build();
//        Response response = target("/songs/1337").request().header(HttpHeaders.AUTHORIZATION, "mmuster")
//                .put(Entity.json(s));
//        assertEquals(404, response.getStatus());
//    }
//
//    @Test
//    public void putWithExistingIdXML() {
//        Song s = new Song.Builder("XML Song").artist("XML Artist").album("XML Album").released(2018).id(1)
//                .build();
//
//        Response response = target("/songs/1").request().header(HttpHeaders.AUTHORIZATION, "mmuster").put(Entity.xml(s));
//        assertEquals(204, response.getStatus());
//    }
//
//    @Test
//    public void putWithExistingIdJSON() {
//        Song s = new Song.Builder("JSON Song").artist("JSON Artist").album("JSON Album").released(2018).id(1)
//                .build();
//
//        Response response = target("/songs/1").request().header(HttpHeaders.AUTHORIZATION, "mmuster").put(Entity.json(s));
//        assertEquals(204, response.getStatus());
//    }
//
//
//}
