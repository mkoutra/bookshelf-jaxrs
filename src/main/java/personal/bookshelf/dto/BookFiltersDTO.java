package personal.bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookFiltersDTO {
    private String title;
    private String author;
    private String releaseYear;
}
