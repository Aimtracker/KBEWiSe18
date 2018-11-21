package de.htw.ai.kbe.servlet.marshalling.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.servlet.marshalling.IMarshaller;
import de.htw.ai.kbe.servlet.marshalling.MarshallingException;
import de.htw.ai.kbe.servlet.pojo.Song;

class JsonHandler implements IMarshaller {

    private static final Logger LOG = Logger.getLogger(JsonHandler.class);

    //Package wide
    JsonHandler() {
    }
    
    @Override
    public Song readSongFromStream(InputStream is) throws MarshallingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (Song) objectMapper.readValue(is, new TypeReference<Song>() {
            });
        } catch (Exception e) {
            String msg = "Failed to write Songs to stream. " + e.getMessage();
            LOG.warn(msg);
            throw new MarshallingException(msg);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Song> readSongsFromStream(InputStream is) throws MarshallingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        } catch (Exception e) {
            String msg = "Failed to write Songs to stream. " + e.getMessage();
            LOG.warn(msg);
            throw new MarshallingException(msg);
        }
    }

    @Override
    public void writeSongsToStream(List<Song> songs, OutputStream os) throws MarshallingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, songs);
        } catch (Exception e) {
            String msg = "Failed to write Songs to stream. " + e.getMessage();
            LOG.warn(msg);
            throw new MarshallingException(msg);
        }
    }
}
