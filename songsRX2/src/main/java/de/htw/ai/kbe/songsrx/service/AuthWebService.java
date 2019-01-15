package de.htw.ai.kbe.songsrx.service;

import de.htw.ai.kbe.songsrx.authorization.IAuthorizationService;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth")
public class AuthWebService {

    @Inject
    IAuthorizationService authService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String authorize(@QueryParam("userId") String userId) throws NotFoundException {
        if(userId == null || userId.isEmpty())
            throw new BadRequestException("No userId was given!");
        String token = authService.authorize(userId);
        return token;
    }
}
