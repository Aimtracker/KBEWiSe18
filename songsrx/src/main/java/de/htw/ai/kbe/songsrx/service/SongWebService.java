package de.htw.ai.kbe.songsrx.service;

import de.htw.ai.kbe.songsrx.bean.Song;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("songs")
public class SongWebService {

        @GET
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Song getSongs() {
            return new Song.Builder("It's raining men").build();
        }

        @GET
        @Path("/{id}")
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Song getSong(@PathParam("id") Integer id){
                return new Song.Builder("Song").id(id).build();
        }

        @POST
        @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        @Produces(MediaType.TEXT_PLAIN)
        public Response createSong(@Valid Song song){
                return Response.ok("Song created! \n" + song).build();
        }

        @PUT
        @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        @Path("/{id}")
        public Response updateSong(@PathParam("id") Integer id, @Valid Song song){
                return Response.ok("Song updated").build();
        }

        @DELETE
        @Path("/{id}")
        public Response deleteSong(@PathParam("id") Integer id){
                return Response.ok("Song deleted").build();
        }
}
