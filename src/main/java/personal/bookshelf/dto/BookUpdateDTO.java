package personal.bookshelf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class BookUpdateDTO {
    @NotEmpty(message = "Title is mandatory.")
    private String title;

    private String author;

    @Pattern(regexp = "^[12]\\d{3}]$")
    private String releaseYear;
}
