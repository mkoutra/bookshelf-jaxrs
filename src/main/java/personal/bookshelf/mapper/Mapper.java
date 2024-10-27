package personal.bookshelf.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import personal.bookshelf.dto.BookInsertDTO;
import personal.bookshelf.dto.BookReadOnlyDTO;
import personal.bookshelf.dto.BookUpdateDTO;
import personal.bookshelf.model.Book;

@ApplicationScoped
public class Mapper {

    public Mapper() {

    }

    public Book mapToBookEntity(BookInsertDTO bookInsertDTO) {
        return new Book(null, bookInsertDTO.getTitle(), bookInsertDTO.getAuthor(), bookInsertDTO.getReleaseYear());
    }

    public Book mapToBookEntity(BookUpdateDTO bookUpdateDTO) {
        return new Book(bookUpdateDTO.getId(), bookUpdateDTO.getTitle(), bookUpdateDTO.getAuthor(), bookUpdateDTO.getReleaseYear());
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        return new BookReadOnlyDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getReleaseYear());
    }
}
