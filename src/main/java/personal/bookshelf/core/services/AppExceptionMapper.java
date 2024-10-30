package personal.bookshelf.core.services;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import personal.bookshelf.core.exception.*;
import personal.bookshelf.dto.ErrorMessageDTO;

@Provider
public class AppExceptionMapper implements ExceptionMapper<GenericException> {

    @Override
    public Response toResponse(GenericException e) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        if (e instanceof EntityInvalidArgumentsException) {
            status = Response.Status.BAD_REQUEST;
        } else if (e instanceof EntityAlreadyExistsException) {
            status = Response.Status.CONFLICT;
        } else if (e instanceof EntityNotFoundException) {
            status = Response.Status.NOT_FOUND;
        } else if (e instanceof AppServerException) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        }

        return Response
                .status(status)
                .entity(new ErrorMessageDTO(e.getCode(), e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
