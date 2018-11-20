//package de.htw.ai.kbe.servlet;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.mock.web.MockServletConfig;
//
//public class SongsServletTest {
//
//    private SongsServlet servlet;
//    private MockServletConfig config;
//    private MockHttpServletRequest request;
//    private MockHttpServletResponse response;
//
//    private final static String DATASOURCE_STRING = "data/songs.json";
//
//    @Before
//    public void setUp() throws ServletException {
//        servlet = new SongsServlet();
//        request = new MockHttpServletRequest();
//        response = new MockHttpServletResponse();
//        config = new MockServletConfig();
//        config.addInitParameter("datasource", DATASOURCE_STRING);
//        servlet.init(config); //throws ServletException
//    }
//
//    @Test
//    public void initShouldSetDBComponentURI() {
//        assertEquals(DATASOURCE_STRING, servlet.getDataSource());
//    }
//
//    @Test
//    public void doGetShouldEchoParameters() throws ServletException, IOException {
//        request.addParameter("username", "scott");
//        request.addParameter("password", "tiger");
//
//        servlet.doGet(request, response);
//
//        assertEquals("application/json", response.getContentType());
//        assertTrue(response.getContentAsString().contains("username=scott"));
//        assertTrue(response.getContentAsString().contains("password=tiger"));
//        assertTrue(response.getContentAsString().contains(DATASOURCE_STRING));
//    }
//
//    @Test
//    public void doPostShouldEchoBody() throws ServletException, IOException {
//        request.setContent("blablablabla".getBytes());
//        servlet.doPost(request, response);
//        assertEquals("application/json", response.getContentType());
//        assertTrue(response.getContentAsString().contains("blablablabla"));
//    }
//}


