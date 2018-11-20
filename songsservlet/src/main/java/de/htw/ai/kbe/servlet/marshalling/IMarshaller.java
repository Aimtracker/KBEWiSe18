package de.htw.ai.kbe.servlet.marshalling;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import de.htw.ai.kbe.servlet.pojo.Song;

public interface IMarshaller {
    List<Song> readSongsFromStream(InputStream is) throws MarshallingException;

    Song readSongFromStream(InputStream is) throws MarshallingException;

    void writeSongsToStream(List<Song> songs, OutputStream os) throws MarshallingException;
}