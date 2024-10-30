package personal.bookshelf.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import personal.bookshelf.authentication.AuthenticationProvider;
import personal.bookshelf.authentication.AuthenticationResponseDTO;
import personal.bookshelf.core.exception.AppServerException;
import personal.bookshelf.core.exception.EntityInvalidArgumentsException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.core.util.validation.ValidatorUtil;
import personal.bookshelf.dto.user.UserInsertDTO;
import personal.bookshelf.dto.user.UserLoginDTO;
import personal.bookshelf.dto.user.UserReadOnlyDTO;
import personal.bookshelf.mapper.Mapper;
import personal.bookshelf.security.JWTService;
import personal.bookshelf.service.UserService;

import java.security.Principal;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/auth")
public class AuthenticationRestController {

    private final Mapper mapper;
    private final UserService userService;
    private final AuthenticationProvider authenticationProvider;
    private final JWTService jwtService;

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

        UserReadOnlyDTO userReadOnlyDTO = userService.insertUser(userInsertDTO);
        return Response.status(Response.Status.CREATED)
                       .entity(userReadOnlyDTO)
                       .build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserLoginDTO userLoginDTO, @Context Principal principal) throws EntityNotFoundException, EntityInvalidArgumentsException {

        // Bean Validation
        List<String> errors = ValidatorUtil.validateDTO(userLoginDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentsException("User", String.join(", ", errors));
        }

        // Authentication
        boolean isAuthenticated = authenticationProvider.authenticate(userLoginDTO);
        if (!isAuthenticated) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        // If principal != null then user has already logged in.
        if (principal != null) {
            String username = principal.getName();
            if (userLoginDTO.getUsername().equals(username)) {
                return Response.status(Response.Status.OK).entity("Already authenticated").build();
            }
        }

        UserReadOnlyDTO userReadOnlyDTO = userService.findUserByUsername(userLoginDTO.getUsername());
        String role = userReadOnlyDTO.getRole();
        String token = jwtService.generateToken(userLoginDTO.getUsername(), role);
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO(token);
        return Response.status(Response.Status.OK).entity(authenticationResponseDTO).build();
    }
}
