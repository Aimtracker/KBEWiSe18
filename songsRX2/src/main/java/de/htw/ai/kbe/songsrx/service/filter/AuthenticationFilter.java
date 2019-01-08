package de.htw.ai.kbe.songsrx.service.filter;

import de.htw.ai.kbe.songsrx.authorization.IAuthorizationService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Inject
    IAuthorizationService authService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request --> This is the extracted Token
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(authService.isValidToken(authorizationHeader)){
            System.out.println("Valid Token.");
        }else{
            System.err.println("Invalid Token!");
            abortWithUnauthorized(requestContext);
        }
        System.err.println(authService.isValidToken(authorizationHeader));
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
    }
}