package de.htw.ai.kbe.songsrx.service;

import de.htw.ai.kbe.songsrx.authorization.IAuthorizationService;
import de.htw.ai.kbe.songsrx.bean.User;
import de.htw.ai.kbe.songsrx.persistence.IUserList;
import javassist.NotFoundException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("auth")
public class AuthWebService {

    @Inject
    IAuthorizationService authService;

    @GET
    public String authorize(@QueryParam("userId") String userId) throws NotFoundException {
        authService.authorize(userId);
        //TO-DO fix this shit
        return null;
    }
}
