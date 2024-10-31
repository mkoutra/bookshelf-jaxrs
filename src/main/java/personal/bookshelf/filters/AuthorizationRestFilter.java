package personal.bookshelf.filters;

import jakarta.annotation.Priority;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationRestFilter implements ContainerRequestFilter {

    // Paths of the form /users/**
    private static final Set<String> adminOnlyPaths = Set.of("users");

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        SecurityContext securityContext = containerRequestContext.getSecurityContext();
        /* Use containerRequestContext.getSecurityContext() to get the updated SecurityContext
         * set by JwtAuthenticationFilter. @Context SecurityContext would not have these updates.*/

        if (isAdminOnlyPath(uriInfo.getPath()) && !securityContext.isUserInRole("ADMIN")) {
            throw new NotAuthorizedException("User: " + securityContext.getUserPrincipal().getName() + " is not authorized.");
        }
    }

    private boolean isAdminOnlyPath(String path) {
        for (String s : adminOnlyPaths) {
            if (path.startsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
