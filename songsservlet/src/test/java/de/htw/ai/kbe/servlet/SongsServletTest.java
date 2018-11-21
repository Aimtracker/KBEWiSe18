package de.htw.ai.kbe.servlet;

import de.htw.ai.kbe.servlet.data.IDataSource;
import de.htw.ai.kbe.servlet.marshalling.IMarshaller;
import de.htw.ai.kbe.servlet.marshalling.MarshallingException;
import de.htw.ai.kbe.servlet.marshalling.impl.MarshallerFactory;
import de.htw.ai.kbe.servlet.pojo.Song;
import de.htw.ai.kbe.servlet.utils.ByteServletInputStream;
import de.htw.ai.kbe.servlet.utils.ByteServletOutputStream;
import de.htw.ai.kbe.servlet.utils.Constants;
import de.htw.ai.kbe.servlet.utils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SongsServletTest {

    private static final String DATASOURCE_FILE = "datasource.json";
    private static SongsServlet servlet;
    private static String all_response;
    private static String single_response;
    private static String payload;
    @Mock
    IDataSource dataSource;
    @Mock
    ServletConfig servletConfig;
    @Mock
    ServletContext servletContext;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    private Map<String, String[]> params;
    private ByteServletOutputStream output;

    @BeforeClass
    public static void setUpClass() throws Exception {
        all_response = getStringListResponse(TestUtils.getTestSongs(), Constants.CONTENTTYPE_JSON);
        single_response = getStringListResponse(Arrays.asList(TestUtils.getTestSong()), Constants.CONTENTTYPE_JSON);
    }

    private static String getStringListResponse(List<Song> songs, String contenttype) throws MarshallingException {
        // Use marshaller to write songlist to string for later comparison
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MarshallerFactory factory = MarshallerFactory.getInstance();
        IMarshaller marshaller = factory.getMarshaller(contenttype);
        marshaller.writeSongsToStream(songs, bos);
        String result = bos.toString();
        bos.reset();
        return result;
    }

    @Before
    public void setUp() throws Exception {
        params = new HashMap<>();
        output = new ByteServletOutputStream();

        //when(servletConfig.getServletContext()).thenReturn(servletContext);
        //when(servletConfig.getInitParameter(Constants.DATASOURCE_PARAM)).thenReturn(DATASOURCE_FILE);
        //when(servletContext.getRealPath(DATASOURCE_FILE)).thenReturn(DATASOURCE_FILE);

        servlet = new SongsServlet(dataSource);
        servlet.init(servletConfig);
        //verify(dataSource, times(1)).load(anyString());
        //verify(servletConfig, times(1)).getServletContext();
        //verify(servletConfig, times(1)).getInitParameter(Constants.DATASOURCE_PARAM);
        //verify(servletContext, times(1)).getRealPath(DATASOURCE_FILE);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("#############################################################");
    }

    // -------------
    // doGet Tests
    // -------------

    @Test
    public void testDoGetAllShouldReturnJSON() throws IOException, ServletException {
        testDoGetAll(Constants.CONTENTTYPE_JSON, Constants.CONTENTTYPE_JSON, all_response);
    }

    @Test
    public void testDoGetWithoutAcceptShouldReturnJSON() throws IOException, ServletException {
        testDoGetAll(Constants.CONTENTTYPE_JSON, null, all_response);
    }

    private void testDoGetAll(String contentType, String accept, String expected) throws IOException, ServletException {
        ByteServletOutputStream output = new ByteServletOutputStream();

        params.put(Constants.ALL_PARAM, new String[0]);
        record(accept, null, params, null, output);

        when(dataSource.getAllSongs()).thenReturn(TestUtils.getTestSongs());

        servlet.doGet(request, response);

        assertEquals(expected, output.getByteStream().toString());

        verify(dataSource, times(1)).getAllSongs();
        verifyRequestResponse(contentType, HttpServletResponse.SC_OK);
        verify(response, times(1)).getOutputStream();
    }

    @Test
    public void testDoGetWithUnsupportedAccept() throws IOException, ServletException {
        when(request.getHeader(Constants.ACCEPT_HEADER)).thenReturn("text/html");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bos);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);
        verify(request, times(1)).getHeader(anyString());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        verify(response, times(1)).setContentType(Constants.CONTENTTYPE_TEXT);
    }

    @Test
    public void testDoGetSingleShouldReturnJSON() throws IOException, ServletException {
        testSingle(Constants.CONTENTTYPE_JSON, Constants.CONTENTTYPE_JSON, single_response);
    }

    @Test
    public void testDoGetSingleWithoutAcceptShouldReturnJSON() throws IOException, ServletException {
        testSingle(Constants.CONTENTTYPE_JSON, null, single_response);
    }

    private void testSingle(String contentType, String accept, String expected) throws IOException, ServletException {
        ByteServletOutputStream output = new ByteServletOutputStream();

        params.put(Constants.SONGID_PARAM, new String[]{"3"});
        record(accept, null, params, null, output);

        when(dataSource.getSong(3)).thenReturn(TestUtils.getTestSong());

        servlet.doGet(request, response);

        assertEquals(expected, output.getByteStream().toString());

        verify(dataSource, times(1)).getSong(3);
        verifyRequestResponse(contentType, HttpServletResponse.SC_OK);
        verify(response, times(1)).getOutputStream();
    }


    @Test
    public void testDoGetSingleNotFound() throws IOException, ServletException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bos);
        when(response.getWriter()).thenReturn(writer);

        params.put(Constants.SONGID_PARAM, new String[]{"6"});
        record(Constants.CONTENTTYPE_JSON, null, params, null, output);

        when(dataSource.getSong(6)).thenReturn(null);

        servlet.doGet(request, response);

        verify(dataSource, times(1)).getSong(6);
        verifyRequestResponse(Constants.CONTENTTYPE_TEXT, HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testDoGetWithUnsupportedParam() throws IOException, ServletException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bos);
        when(response.getWriter()).thenReturn(writer);

        params.put("unsupported param", new String[0]);
        record(Constants.CONTENTTYPE_JSON, null, params, null, output);

        servlet.doGet(request, response);

        verifyRequestResponse(Constants.CONTENTTYPE_TEXT, HttpServletResponse.SC_BAD_REQUEST);
    }

    private void verifyRequestResponse(String contentType, int code) throws IOException {
        verify(request, times(1)).getHeader(anyString());
        verify(request, times(1)).getParameterMap();
        verify(response, times(1)).setStatus(code);
        verify(response, times(1)).setContentType(contentType);
    }

    // -------------
    // doPost Tests
    // -------------

    @Test
    public void testDoPostJsonPayloadReturnText() throws IOException, ServletException {
        payload = TestUtils.getJsonPayload();

        ByteServletOutputStream output = new ByteServletOutputStream();
        ByteServletInputStream input = new ByteServletInputStream(payload.getBytes());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bos);
        when(response.getWriter()).thenReturn(writer);

        record(Constants.CONTENTTYPE_TEXT, Constants.CONTENTTYPE_JSON, null, input, output);

        when(dataSource.addSong(isA(Song.class))).thenReturn(TestUtils.getTestSong());

        servlet.doPost(request, response);

        verify(dataSource, times(1)).addSong(isA(Song.class));
        verify(request, times(2)).getHeader(anyString());
        verify(request, times(1)).getInputStream();

        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
        verify(response, times(1)).setContentType(Constants.CONTENTTYPE_TEXT);
    }

    @Test
    public void testDoPostJsonPayloadReturnJson() throws IOException, ServletException {
        payload = TestUtils.getJsonPayload();

        ByteServletOutputStream output = new ByteServletOutputStream();
        ByteServletInputStream input = new ByteServletInputStream(payload.getBytes());

        record(Constants.CONTENTTYPE_JSON, Constants.CONTENTTYPE_JSON, null, input, output);

        when(dataSource.addSong(isA(Song.class))).thenReturn(TestUtils.getTestSong());

        servlet.doPost(request, response);

        assertEquals(single_response, output.getByteStream().toString());

        verify(dataSource, times(1)).addSong(isA(Song.class));
        verify(request, times(2)).getHeader(anyString());
        verify(request, times(1)).getInputStream();

        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
        verify(response, times(1)).setContentType(Constants.CONTENTTYPE_JSON);
        verify(response, times(1)).getOutputStream();
    }

    @Test
    public void testDoPostWithBrokenJsonPayload() throws IOException, ServletException {
        payload = "jsonusKaputtus";

        ByteServletOutputStream output = new ByteServletOutputStream();
        ByteServletInputStream input = new ByteServletInputStream(payload.getBytes());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(baos);
        when(response.getWriter()).thenReturn(writer);

        record(Constants.CONTENTTYPE_JSON, Constants.CONTENTTYPE_JSON, null, input, output);

        servlet.doPost(request, response);

        verify(request, times(2)).getHeader(anyString());
        verify(request, times(1)).getInputStream();

        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response, times(1)).setContentType(Constants.CONTENTTYPE_TEXT);
    }

    // -------------
    // Methods for doGet and doPost Tests
    // -------------

    private void record(String accept, String contentType, Map<String, String[]> params, ByteServletInputStream input, ByteServletOutputStream out) throws IOException {
        if (accept != null) {
            when(request.getHeader(Constants.ACCEPT_HEADER)).thenReturn(accept);
        }
        if (contentType != null) {
            when(request.getHeader(Constants.CONTENT_TYPE_HEADER)).thenReturn(contentType);
        }
        when(request.getParameterMap()).thenReturn(params);
        when(request.getInputStream()).thenReturn(input);

        when(response.getOutputStream()).thenReturn(out);
    }
}
