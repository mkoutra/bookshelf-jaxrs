package personal.bookshelf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookInsertDTO {
    @NotEmpty(message = "Title is mandatory.")
    private String title;

    private String author;

    @Pattern(regexp = "^[12]\\d{3}$")
    private String releaseYear;
}
