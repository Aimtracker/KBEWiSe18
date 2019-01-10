package de.htw.ai.kbe.songsrx.persistence.songlist;

import de.htw.ai.kbe.songsrx.bean.SongList;
import de.htw.ai.kbe.songsrx.bean.User;

import java.util.List;
import java.util.NoSuchElementException;

public interface ISongList {
    List<SongList> getListsOfUser(User user);
    SongList getListByIdAndUser(int listId, User user) throws NoSuchElementException;
    void delete(int listId) throws NoSuchElementException;
    void persist(SongList list);
}
