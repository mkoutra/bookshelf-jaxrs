package personal.bookshelf.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookReadOnlyDTO {
    private Long id;
    private String title;
    private String author;
    private String releaseYear;
}
