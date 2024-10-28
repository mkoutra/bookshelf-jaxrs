package personal.bookshelf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDTO {
    @NotNull(message = "Id must not be empty.")
    private Long id;

    @NotEmpty(message = "Title must not be empty.")
    private String title;

    private String author;

    @Pattern(regexp = "^[12]\\d{3}$", message = "Invalid year.")
    private String releaseYear;
}
