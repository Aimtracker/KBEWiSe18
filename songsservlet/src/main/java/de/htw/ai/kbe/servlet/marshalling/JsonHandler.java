package de.htw.ai.kbe.servlet.marshalling;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.servlet.marshalling.IMarshaller;
import de.htw.ai.kbe.servlet.marshalling.MarshallingException;
import de.htw.ai.kbe.servlet.pojo.Song;

/**
 * Can read and write JSON files for one or more {@link Song}
 *
 * @version 0.5
 */
class JsonHandler implements IMarshaller {

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
            System.out.println(msg);
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
            System.out.println(msg);
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
            System.out.println(msg);
            throw new MarshallingException(msg);
        }
    }
}