package personal.bookshelf.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {
    @Pattern(regexp = "\\w{2,30}", message = "Username must be 2-30 long and contain only letters, digits, or underscores.")
    private String username;

    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[!@#%&*]).{8,}$", message = "Invalid password.")
    private String password;

    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[!@#%&*]).{8,}$", message = "Invalid password.")
    private String confirmPassword;

    @NotEmpty(message = "Role must not be empty.")
    private String role;
}
