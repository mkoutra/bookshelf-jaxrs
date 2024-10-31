package personal.bookshelf.filters;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.bookshelf.dao.IUserDAO;
import personal.bookshelf.model.User;
import personal.bookshelf.security.CustomSecurityContext;
import personal.bookshelf.security.JWTService;

import java.io.IOException;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JwtAuthenticationFilter implements ContainerRequestFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final static Set<String> PUBLIC_PATHS = Set.of(
            "auth/register",
            "auth/login"
    );

    private final IUserDAO userDAO;
    private final JWTService jwtService;

    @Context
    private SecurityContext securityContext;

    /**
     * Checks the validity of JWT for every request that does not belong to the "Public paths".
     * If there is no SecurityContext associated with current user, it creates one.
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        UriInfo uriInfo = containerRequestContext.getUriInfo();

        // If current path belongs to "public paths" ignore filtering
        if (isPublicPath(uriInfo.getPath())) {
            return;
        }

        // Extract HTTP authorization header
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization Header does not exist, or is invalid.");
        }

        // Extract token from authorization header
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        try {
            // Extract username from token
            String username = jwtService.extractSubject(token);

            // No security context has been created for this user.
            if (username != null && (securityContext == null || securityContext.getUserPrincipal() == null)) {

                User user = userDAO.findUserByUsername(username).orElse(null);

                // If user is not null and token is valid, create a security context for this user
                if (user != null && jwtService.isTokenValid(token, user)) {
                    containerRequestContext.setSecurityContext(new CustomSecurityContext(user));
                } else {
                    LOGGER.error("Invalid token.");
                }
            }
        } catch (Exception e) {
            throw new NotAuthorizedException("Invalid token.");
        }
    }

    // Checks if a path belongs to public paths
    private boolean isPublicPath(String path) {
        return  PUBLIC_PATHS.contains(path);
    }
}
