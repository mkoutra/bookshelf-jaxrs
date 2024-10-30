package personal.bookshelf.service;

import personal.bookshelf.core.exception.EntityAlreadyExistsException;
import personal.bookshelf.core.exception.EntityInvalidArgumentsException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.dto.book.BookInsertDTO;
import personal.bookshelf.dto.book.BookReadOnlyDTO;
import personal.bookshelf.dto.book.BookUpdateDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    BookReadOnlyDTO insertBook(BookInsertDTO bookInsertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentsException;
    BookReadOnlyDTO updateBook(BookUpdateDTO bookUpdateDTO) throws EntityNotFoundException, EntityInvalidArgumentsException;
    void deleteBook(Long id) throws EntityNotFoundException;
    BookReadOnlyDTO getBookById(Long id) throws EntityNotFoundException;
    BookReadOnlyDTO getBookByTitle(String title) throws EntityNotFoundException;
    List<BookReadOnlyDTO> getBooksByAuthor(String author);
    List<BookReadOnlyDTO> getBooksByReleaseYear(String releaseYear);
    List<BookReadOnlyDTO> getBooksByCriteria(Map<String, Object> criteria);
    List<BookReadOnlyDTO> getAllBooks();
}
