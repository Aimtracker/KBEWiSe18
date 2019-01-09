//package de.htw.ai.kbe.songsrx.service;
//import de.htw.ai.kbe.songsrx.authorization.AuthorizationService;
//import de.htw.ai.kbe.songsrx.authorization.IAuthorizationService;
//import de.htw.ai.kbe.songsrx.persistence.IUser;
//import de.htw.ai.kbe.songsrx.persistence.InMemoryUser;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.junit.Test;
//
//import javax.inject.Singleton;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.Response;
//
//import static org.junit.Assert.assertEquals;
//
//public class AuthWebServiceTest extends JerseyTest{
//
//    @Override
//    protected Application configure() {
//        return new ResourceConfig(AuthWebService.class).register(new AbstractBinder() {
//
//            @Override
//            protected void configure() {
//                bind(AuthorizationService.class).to(IAuthorizationService.class).in(Singleton.class);
//                bind(InMemoryUser.class).to(IUser.class).in(Singleton.class);
//
//            }
//        });
//    }
//
//
//    @Test
//    public void getTokenWithNonExisitingUserId() {
//        Response response = target("/auth").queryParam("userId", "NonExisitingUser").request().header(HttpHeaders.AUTHORIZATION, "test").get();
//        assertEquals(403, response.getStatus());
//    }
//
//    @Test
//    public void getTokenWithExisitingUserId() {
//        Response response = target("/auth").queryParam("userId", "mmuster").request().header(HttpHeaders.AUTHORIZATION, "test").get();
//        assertEquals(200, response.getStatus());
//    }
//
//}
