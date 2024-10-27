package personal.bookshelf.dao;

import personal.bookshelf.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookDAO extends IGenericCRUD<Book> {
    Optional<Book> findBookByTitle(String title);
    List<Book> findBooksByAuthor(String author);
    List<Book> findBooksByReleaseYear(String releaseYear);
}
