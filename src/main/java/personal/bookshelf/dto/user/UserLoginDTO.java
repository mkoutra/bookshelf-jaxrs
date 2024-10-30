package personal.bookshelf.dto.user;

import jakarta.validation.constraints.NotEmpty;

public class UserLoginDTO {
    @NotEmpty(message = "Username must not be empty.")
    private String username;

    @NotEmpty(message = "Password must not be empty.")
    private String password;
}
