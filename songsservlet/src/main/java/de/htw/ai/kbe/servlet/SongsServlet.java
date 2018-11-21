package de.htw.ai.kbe.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import de.htw.ai.kbe.servlet.data.DataSource;
import de.htw.ai.kbe.servlet.data.IDataSource;
import de.htw.ai.kbe.servlet.marshalling.IMarshaller;
import de.htw.ai.kbe.servlet.marshalling.MarshallingException;
import de.htw.ai.kbe.servlet.marshalling.impl.MarshallerFactory;
import de.htw.ai.kbe.servlet.pojo.Song;
import de.htw.ai.kbe.servlet.utils.Constants;

public class SongsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(SongsServlet.class.getName());

    private IDataSource dataSource;
    
    private MarshallerFactory factory;

    public SongsServlet() {
        this(new DataSource());
    }

    SongsServlet(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        BasicConfigurator.configure();
        String filePath = config.getServletContext().getRealPath(config.getInitParameter(Constants.DATASOURCE_PARAM));

        try {
            dataSource.load(filePath);
        } catch (IOException e) {
            throw new ServletException(e);
        }
        
        this.factory = MarshallerFactory.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String contentType = req.getHeader(Constants.CONTENT_TYPE_HEADER);
        String accepts = req.getHeader(Constants.ACCEPT_HEADER);
        
        try {
            IMarshaller marshaller = factory.getMarshaller(contentType);
            Song song = marshaller.readSongFromStream(req.getInputStream());
            
            log.info(song);
            
            song = dataSource.addSong(song);
            
            
            if(accepts != null && accepts.equals(Constants.CONTENTTYPE_TEXT)) {
                sendResponse(resp, "Saved song. Id: " + song.getId(), HttpServletResponse.SC_CREATED);
            } else {
                sendResponse(resp, contentType, Arrays.asList(song), HttpServletResponse.SC_CREATED);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        } catch (MarshallingException e) {
            log.error(e.getMessage());
            sendResponse(resp, "Malformed body", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accepts = null;
        try {
            accepts = readAcceptHeader(request);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            sendResponse(response, e.getMessage(), HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }
        

        Map<String, String[]> params = request.getParameterMap();
        if (params.isEmpty() || params.containsKey(Constants.ALL_PARAM)) {
            // handle all songs
            log.info("getting all songs.");
            sendResponse(response, accepts, dataSource.getAllSongs(), HttpServletResponse.SC_OK);
            return;
        } else if (params.containsKey(Constants.SONGID_PARAM)) {
            // handle id

             getSongById(response, accepts, params.get(Constants.SONGID_PARAM));
             return;
        } else {
            // unsupported
            sendResponse(response, "invalid operation given", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String readAcceptHeader(HttpServletRequest request) {
        String accepts = request.getHeader(Constants.ACCEPT_HEADER);
        if (accepts == null || accepts.isEmpty() || accepts.equals("*/*")) {
            accepts = Constants.CONTENTTYPE_JSON;
            log.warn("no accept header set in request. Using default: " + accepts);
        }
        if(accepts == Constants.CONTENTTYPE_JSON){
            return accepts;
        }else{
            throw new IllegalArgumentException("Unsupported content type '" + accepts + "'");
        }
    }

    @Override
    public void destroy() {

        try {
            this.dataSource.save();
        } catch (IOException e) {
            log.fatal("Failed to save JSON data to file.", e);
        }

        super.destroy();
    }

    private void getSongById(HttpServletResponse response, String contentType, String[] params) throws IOException {

        int id = -1;
        try {
            id = parseIdParam(params);
        } catch (IllegalArgumentException e) {
            log.debug("invalid parameters given: " + Arrays.toString(params));
            sendResponse(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Song song = dataSource.getSong(id);
        if (song == null) {
            String msg = "No song with id " + id;
            log.debug(msg);
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

    private void sendResponse(HttpServletResponse response, String contentType, List<Song> songs, int status) throws IOException {
        try {
            IMarshaller marshaller = MarshallerFactory.getInstance().getMarshaller(contentType);
            response.setContentType(contentType);
            response.setStatus(status);
            marshaller.writeSongsToStream(songs, response.getOutputStream());
        } catch (IOException e) {
            log.warn("Failed to write songs response. " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void sendResponse(HttpServletResponse response, String msg, int code) {
        response.setContentType(Constants.CONTENTTYPE_TEXT);
        response.setStatus(code);
        try {
            response.getWriter().println(msg);
        } catch (IOException e) {
            log.warn("Failed to send error message. " + e.getMessage());
        }
    }
}
