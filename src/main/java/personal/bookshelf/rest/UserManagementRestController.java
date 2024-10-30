package personal.bookshelf.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.dto.user.UserReadOnlyDTO;
import personal.bookshelf.service.IUserService;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/users")
public class UserManagementRestController {

    private final IUserService userService;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") Long id) throws EntityNotFoundException {
        UserReadOnlyDTO userReadOnlyDTO = userService.getUserById(id);
        return Response.status(Response.Status.OK).entity(userReadOnlyDTO).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserReadOnlyDTO> users = userService.getAllUsers();
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") Long id) throws EntityNotFoundException {
        UserReadOnlyDTO userReadOnlyDTO = userService.getUserById(id);
        userService.deleteUser(id);
        return Response.status(Response.Status.OK).entity(userReadOnlyDTO).build();
    }
}
