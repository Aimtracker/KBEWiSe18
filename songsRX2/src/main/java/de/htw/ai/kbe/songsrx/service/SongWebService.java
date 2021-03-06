package de.htw.ai.kbe.songsrx.service;

import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.persistence.song.ISong;
import de.htw.ai.kbe.songsrx.service.filter.Secured;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


@Path("songs")
public class SongWebService {

    @Context
    UriInfo uriInfo;

    @Inject
    ISong songList;

    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Song> getSongs() {
        return songList.getAll();
    }

    @GET
    @Secured
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSong(@PathParam("id") Integer id) {
        try {
            Song song = songList.getById(id);
            return Response.ok(song).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSong(@Valid Song song) {
        Integer id = songList.add(song);
        //return Response.ok("Song added (new id: " + id + ")").build();
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(id.toString());
        return Response.created(uriBuilder.build()).entity("Song added (new id: " + id + ")").build();
    }

    @PUT
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, @Valid Song song){
        if (song.getId() != null && id != song.getId()) {
            throw new BadRequestException("Something is not right here fam");
        }

        try {
            song.setId(id);
            songList.update(song);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

//    Not needed for this assignment
//    @DELETE
//    @Secured
//    @Path("/{id}")
//    public Response deleteSong(@PathParam("id") Integer id) {
//        try {
//            songList.delete(id);
//            return Response.status(Response.Status.NO_CONTENT).build();
//        } catch (NotFoundException e) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//    }
}
