package de.htw.ai.kbe.servlet;

import de.htw.ai.kbe.servlet.data.IDataSource;
import de.htw.ai.kbe.servlet.marshalling.IMarshaller;
import de.htw.ai.kbe.servlet.marshalling.MarshallerFactory;
import de.htw.ai.kbe.servlet.marshalling.MarshallingException;
import de.htw.ai.kbe.servlet.pojo.Song;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class SongsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IDataSource dataSource;

    private MarshallerFactory factory;

    SongsServlet(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // Beispiel: Laden eines Konfigurationsparameters aus der web.xml
        String dataSourcePath = servletConfig.getInitParameter("datasource");
        try {
            dataSource.load(dataSourcePath);
        } catch (IOException e) {
            throw new ServletException(e);
        }

        this.factory = MarshallerFactory.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        // alle Parameter (keys)
        String accepts = request.getHeader("Accept");
        if (request.getHeader("Accept") == "application/json") {
            Map<String, String[]> params = request.getParameterMap();
            if (params.containsKey("all")) {
                sendResponse(response, accepts, dataSource.getAllSongs(), HttpServletResponse.SC_OK);
            } else if (params.containsKey("songId")) {
                getSongById(response, accepts, params.get("songId"));
            } else {
                sendResponse(response, "invalid operation given", HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            throw new IllegalArgumentException("Unsupported content type '" + accepts + "'");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contentType = request.getHeader("Content-Type");
        String accepts = request.getHeader("Accept");

        IMarshaller marshaller = factory.getMarshaller(contentType);
        Song song = marshaller.readSongFromStream(request.getInputStream());

        song = dataSource.addSong(song);

        try {
            sendResponse(response, "Saved song. Id: " + song.getId(), HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            sendResponse(response, e.getMessage(), HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

    }

    private void getSongById(HttpServletResponse response, String contentType, String[] params) throws IOException {
        System.out.println("getSongById()");

        int id = -1;
        try {
            id = parseIdParam(params);
        } catch (IllegalArgumentException e) {
            sendResponse(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Song song = dataSource.getSong(id);
        if (song == null) {
            String msg = "No song with id " + id;
            System.out.println(msg);
            sendResponse(response, msg, HttpServletResponse.SC_NOT_FOUND);
        } else {
            sendResponse(response, contentType, Arrays.asList(song), HttpServletResponse.SC_OK);
        }
    }

    private int parseIdParam(String[] params) throws IllegalArgumentException {
        if (params == null || params.length != 1) {
            throw new IllegalArgumentException("Invalid songId parameter");
        }

        try {
            int id = Integer.parseInt(params[0]);
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Given songId is not a number");
        }
    }

    private void sendResponse(HttpServletResponse response, String contentType, List<Song> songs, int status) throws
            IOException {
        try {
            IMarshaller marshaller = MarshallerFactory.getInstance().getMarshaller(contentType);
            response.setContentType(contentType);
            response.setStatus(status);
            marshaller.writeSongsToStream(songs, response.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to write songs response. " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendResponse(HttpServletResponse response, String msg, int code) {
        response.setContentType("text/plain");
        response.setStatus(code);
        try {
            response.getWriter().println(msg);
        } catch (IOException e) {
            System.out.println("Failed to send error message. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy()");

        try {
            this.dataSource.save();
        } catch (IOException e) {
            System.err.println("Failed to save JSON data to file. Exception: " + e);
        }

        super.destroy();
    }
}