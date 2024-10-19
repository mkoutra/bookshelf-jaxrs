package personal.bookshelf.service;

import personal.bookshelf.core.exception.EntityAlreadyExistsException;
import personal.bookshelf.core.exception.EntityInvalidArgumentsException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.dto.BookInsertDTO;
import personal.bookshelf.dto.BookReadOnlyDTO;
import personal.bookshelf.dto.BookUpdateDTO;

import java.util.List;
import java.util.Map;

public class BookService implements IBookService {


    @Override
    public BookReadOnlyDTO insertBook(BookInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentsException {
        return null;
    }

    @Override
    public BookReadOnlyDTO updateBook(BookUpdateDTO updateDTO) throws EntityNotFoundException, EntityInvalidArgumentsException {
        return null;
    }

    @Override
    public void deleteBook(Long id) throws EntityNotFoundException {

    }

    @Override
    public BookReadOnlyDTO getBookById() {
        return null;
    }

    @Override
    public BookReadOnlyDTO getBookByTitle() {
        return null;
    }

    @Override
    public List<BookReadOnlyDTO> getBooksByCriteria(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public List<BookInsertDTO> getAllBooks() {
        return List.of();
    }
}
