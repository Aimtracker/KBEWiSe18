package de.htw.ai.kbe.songsrx.service.filter;

import javax.json.stream.JsonParsingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JSONParsingExceptionMapper implements ExceptionMapper<JsonParsingException> {
    @Override
    public Response toResponse(JsonParsingException exception) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
