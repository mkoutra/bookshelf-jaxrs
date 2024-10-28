package personal.bookshelf.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import personal.bookshelf.dto.BookFiltersDTO;
import personal.bookshelf.dto.BookInsertDTO;
import personal.bookshelf.dto.BookReadOnlyDTO;
import personal.bookshelf.dto.BookUpdateDTO;
import personal.bookshelf.model.Book;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@ApplicationScoped
public class Mapper {

    public Book mapToBookEntity(BookInsertDTO bookInsertDTO) {
        return new Book(null, bookInsertDTO.getTitle(), bookInsertDTO.getAuthor(), bookInsertDTO.getReleaseYear());
    }

    public Book mapToBookEntity(BookUpdateDTO bookUpdateDTO) {
        return new Book(bookUpdateDTO.getId(), bookUpdateDTO.getTitle(), bookUpdateDTO.getAuthor(), bookUpdateDTO.getReleaseYear());
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        return new BookReadOnlyDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getReleaseYear());
    }

    public Map<String, Object> mapToCriteria(BookFiltersDTO bookFiltersDTO) {
        Map<String, Object> filters = new HashMap<>();

        if (!(bookFiltersDTO.getTitle() == null) && !(bookFiltersDTO.getTitle().isEmpty())) {
            filters.put("title", bookFiltersDTO.getTitle());
        }

        if (!(bookFiltersDTO.getAuthor() == null) && !(bookFiltersDTO.getAuthor().isEmpty())) {
            filters.put("author", bookFiltersDTO.getAuthor());
        }

        if (!(bookFiltersDTO.getReleaseYear() == null) && !(bookFiltersDTO.getReleaseYear().isEmpty())) {
            filters.put("releaseYear", bookFiltersDTO.getReleaseYear());
        }

        return filters;
    }
}
