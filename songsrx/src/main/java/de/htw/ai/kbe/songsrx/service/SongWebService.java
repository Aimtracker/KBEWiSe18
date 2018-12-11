package de.htw.ai.kbe.songsrx.service;

import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.persistence.ISongList;
import de.htw.ai.kbe.songsrx.service.filter.Secured;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("songs")
public class SongWebService {

        @Inject
        ISongList songList;

        @GET
        @Secured
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public List<Song> getSongs() {
            return songList.getAll();
        }

        @GET
        @Secured
        @Path("/{id}")
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Response getSong(@PathParam("id") Integer id) throws NotFoundException {
                Song song = songList.getById(id);
                return Response.ok(song).build();
        }

        @POST
        @Secured
        @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        @Produces(MediaType.TEXT_PLAIN)
        public Response createSong(@Valid Song song){
                Integer id = songList.add(song);
                return Response.ok("Song added (new id: " + id + ")").build();
        }

        @PUT
        @Secured
        @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        @Path("/{id}")
        public Response updateSong(@PathParam("id") Integer id, @Valid Song song) throws NotFoundException {
                if(song.getId() != null && id != song.getId()){
                        throw new BadRequestException("Something is not right here fam");
                }

                song.setId(id);
                songList.update(song);
                return Response.ok("Song updated").build();
        }

        @DELETE
        @Secured
        @Path("/{id}")
        public Response deleteSong(@PathParam("id") Integer id) throws NotFoundException {
                songList.delete(id);
                return Response.ok("Deleted Song with id " + id).build();
        }
}
