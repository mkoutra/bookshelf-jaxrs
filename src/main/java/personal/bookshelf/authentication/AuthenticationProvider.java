package personal.bookshelf.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import personal.bookshelf.dto.user.UserLoginDTO;
import personal.bookshelf.service.IUserService;

@ApplicationScoped
public class AuthenticationProvider {

    private final IUserService userService;

    @Inject
    public AuthenticationProvider(IUserService userService) {
        this.userService = userService;
    }

    public boolean authenticate(UserLoginDTO userLoginDTO) {
        return userService.isUserValid(userLoginDTO.getUsername(), userLoginDTO.getPassword());
    }
}
