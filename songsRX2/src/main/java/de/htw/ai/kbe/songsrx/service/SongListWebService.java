package de.htw.ai.kbe.songsrx.service;

import de.htw.ai.kbe.songsrx.bean.SongList;
import de.htw.ai.kbe.songsrx.persistence.songlist.ISongList;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("songLists")
public class SongListWebService {
    @Context
    UriInfo uriInfo;

    @Inject
    ISongList songList;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createSongList(@Valid SongList iSongList) {
        //Change this to a proper id!!!
        Integer id = 0;
        songList.persist(iSongList);
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
