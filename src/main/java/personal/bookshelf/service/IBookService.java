package personal.bookshelf.service;

import personal.bookshelf.core.exception.EntityAlreadyExistsException;
import personal.bookshelf.core.exception.EntityInvalidArgumentsException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.dto.BookInsertDTO;
import personal.bookshelf.dto.BookReadOnlyDTO;
import personal.bookshelf.dto.BookUpdateDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    BookReadOnlyDTO insertBook(BookInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentsException;
    BookReadOnlyDTO updateBook(BookUpdateDTO updateDTO) throws EntityNotFoundException, EntityInvalidArgumentsException;
    void deleteBook(Long id) throws EntityNotFoundException;
    BookReadOnlyDTO getBookById();
    BookReadOnlyDTO getBookByTitle();
    List<BookReadOnlyDTO> getBooksByCriteria(Map<String, Object> criteria);
    List<BookInsertDTO> getAllBooks();
}
