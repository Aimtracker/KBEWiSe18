package de.htw.ai.kbe.songsrx.service;

import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.bean.SongList;
import de.htw.ai.kbe.songsrx.bean.User;
import de.htw.ai.kbe.songsrx.persistence.song.ISong;
import de.htw.ai.kbe.songsrx.persistence.songlist.ISongList;
import de.htw.ai.kbe.songsrx.persistence.user.IUser;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("songLists")
public class SongListWebService {
    @Context
    UriInfo uriInfo;

    @Inject
    ISongList songList;

    @Inject
    ISong songPersistence;

    @Inject
    IUser userPersistence;

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getListById(@PathParam("id") Integer id){
        SongList list = songList.getListById(id);
        return Response.ok(list).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getUserLists(@QueryParam("userId") String userId){
        try{
            User user = userPersistence.getUserById(userId);
            List<SongList> list = songList.getListsOfUser(user);
            GenericEntity<List<SongList>> generic = new GenericEntity<List<SongList>>(list) {
            };

            return Response.ok(generic).build();
        } catch (javassist.NotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createSongList(@Valid SongList iSongList) {

        List<Song> songs = iSongList.getSongs().stream()
                .map(s -> {
                    if(s == null || s.getId() == null) { throw new IllegalArgumentException("Invalid song payload"); }
                    try {
                        return songPersistence.getById(s.getId());
                    } catch (javassist.NotFoundException e) {
                        e.printStackTrace();
                        throw new BadRequestException();
                    }
                }).collect(Collectors.toList());

        iSongList.setSongs(songs);

        songList.persist(iSongList);
        Integer id = iSongList.getId();
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(id.toString());
        return Response.created(uriBuilder.build()).entity("Song List added (new id: " + id + ")").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSongList(@PathParam("id") int id) {
        try {
            songList.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
