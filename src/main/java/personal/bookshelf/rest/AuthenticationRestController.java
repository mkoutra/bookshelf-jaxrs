package personal.bookshelf.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import personal.bookshelf.core.exception.AppServerException;
import personal.bookshelf.core.exception.EntityInvalidArgumentsException;
import personal.bookshelf.core.util.validation.ValidatorUtil;
import personal.bookshelf.dto.user.UserInsertDTO;
import personal.bookshelf.dto.user.UserReadOnlyDTO;
import personal.bookshelf.mapper.Mapper;
import personal.bookshelf.model.User;
import personal.bookshelf.service.UserService;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/auth")
public class AuthenticationRestController {

    private final Mapper mapper;
    private final UserService userService;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserInsertDTO userInsertDTO) throws EntityInvalidArgumentsException, AppServerException {
        // Validation
        List<String> errors = ValidatorUtil.validateDTO(userInsertDTO);
        if(!errors.isEmpty()) {
            throw new EntityInvalidArgumentsException("User", String.join(", ", errors));
        }

        User user = mapper.mapToUser(userInsertDTO
        UserReadOnlyDTO userReadOnlyDTO = userService.insertUser(userInsertDTO);
        return Response.status(Response.Status.CREATED)
                       .entity(userReadOnlyDTO)
                       .build();
    }

//    @POST
//    @Path("/login")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response loginUser(UserLoginDTO userLoginDTO) {
//
//    }
}
