package personal.bookshelf.dao;

import personal.bookshelf.model.Book;

import java.util.List;
import java.util.Optional;

public class BookDAO extends GenericCRUDImpl<Book> implements IBookDAO {
    @Override
    public Optional<Book> getByTitle() {
        return Optional.empty();
    }

    // TODO: Improve when author is a separate table
    @Override
    public List<Book> getByAuthor() {
        return List.of();
    }

    @Override
    public List<Book> getByReleaseYear() {
        return List.of();
    }
}
