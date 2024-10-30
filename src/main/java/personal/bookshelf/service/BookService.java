package personal.bookshelf.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.bookshelf.core.exception.EntityAlreadyExistsException;
import personal.bookshelf.core.exception.EntityInvalidArgumentsException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.core.util.jpa.JPAHelperUtil;
import personal.bookshelf.dao.IBookDAO;
import personal.bookshelf.dto.book.BookInsertDTO;
import personal.bookshelf.dto.book.BookReadOnlyDTO;
import personal.bookshelf.dto.book.BookUpdateDTO;
import personal.bookshelf.mapper.Mapper;
import personal.bookshelf.model.Book;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BookService implements IBookService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final IBookDAO bookDAO;
    private final Mapper mapper;

    @Override
    public BookReadOnlyDTO insertBook(BookInsertDTO bookInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentsException {
        try {
            JPAHelperUtil.beginTransaction();
            Book book = mapper.mapToBookEntity(bookInsertDTO);

            if (bookDAO.findBookByTitle(book.getTitle()).isPresent()) {
                throw new EntityAlreadyExistsException("Book", "Book with title " + book.getTitle() + "already exists");
            }

            BookReadOnlyDTO bookReadOnlyDTO = bookDAO.insert(book)
                                                     .map(mapper::mapToBookReadOnlyDTO)
                                                     .orElseThrow(() -> new EntityInvalidArgumentsException("Book",
                                                             "Book with title " + book.getTitle() + " not inserted."));
            JPAHelperUtil.commitTransaction();

            LOGGER.info("Book with id {} and title {} inserted.", book.getId(), book.getTitle());

            return bookReadOnlyDTO;
        } catch (Exception e) {
            LOGGER.error("Book with title {}, author: {}, release year: {} was not inserted",
                    bookInsertDTO.getTitle(), bookInsertDTO.getAuthor(), bookInsertDTO.getReleaseYear());
            JPAHelperUtil.rollbackTransaction();
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public BookReadOnlyDTO updateBook(BookUpdateDTO bookUpdateDTO)
            throws EntityNotFoundException, EntityInvalidArgumentsException {
        try {
            JPAHelperUtil.beginTransaction();
            Book book = mapper.mapToBookEntity(bookUpdateDTO);

            bookDAO.getById(bookUpdateDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Book", "Book with id " + bookUpdateDTO.getId() + " was not found."));

            BookReadOnlyDTO bookReadOnlyDTO = bookDAO.update(book)
                                                     .map(mapper::mapToBookReadOnlyDTO)
                                                     .orElseThrow(() -> new EntityInvalidArgumentsException("Book",
                                                             "Book with id " + book.getId() + " not updated."));
            JPAHelperUtil.commitTransaction();

            LOGGER.info("Book with id {} was update.", book.getId());

            return bookReadOnlyDTO;
        } catch (Exception e) {
            LOGGER.error("Book with id {} was not updated", bookUpdateDTO.getId());
            JPAHelperUtil.rollbackTransaction();
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public void deleteBook(Long id) throws EntityNotFoundException {
        try {
            JPAHelperUtil.beginTransaction();
            bookDAO.getById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book", "Book with id " + id + " was not found."));
            bookDAO.delete(id);
            JPAHelperUtil.commitTransaction();

            LOGGER.info("Book with id: {} was deleted.", id);
        } catch (Exception e) {
            LOGGER.error("Book with id {} was not deleted.", id);
            JPAHelperUtil.rollbackTransaction();
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public BookReadOnlyDTO getBookById(Long id) throws EntityNotFoundException {
        try {
            JPAHelperUtil.beginTransaction();

            BookReadOnlyDTO bookReadOnlyDTO = bookDAO.getById(id)
                                                     .map(mapper::mapToBookReadOnlyDTO)
                                                     .orElseThrow(() -> new EntityNotFoundException("Book",
                                                         "Book with id " + id + " was not found."));
            JPAHelperUtil.commitTransaction();

            LOGGER.info("Book with id {} was found.", id);

            return bookReadOnlyDTO;
        } catch (Exception e) {
            LOGGER.error("Book with id {} was not found.", id);
            JPAHelperUtil.rollbackTransaction();
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public BookReadOnlyDTO getBookByTitle(String title) throws EntityNotFoundException {
        try {
            JPAHelperUtil.beginTransaction();

            BookReadOnlyDTO bookReadOnlyDTO = bookDAO.getById(title)
                    .map(mapper::mapToBookReadOnlyDTO)
                    .orElseThrow(() -> new EntityNotFoundException("Book",
                            "Book with title " + title + " was not found."));
            JPAHelperUtil.commitTransaction();

            LOGGER.info("Book with title {} was found.", title);

            return bookReadOnlyDTO;
        } catch (Exception e) {
            LOGGER.error("Book with title {} was not found.", title);
            JPAHelperUtil.rollbackTransaction();
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public List<BookReadOnlyDTO> getBooksByAuthor(String author) {
        JPAHelperUtil.beginTransaction();
        List<Book> books =  bookDAO.findBooksByAuthor(author);
        JPAHelperUtil.commitTransaction();
        return books.stream().map(mapper::mapToBookReadOnlyDTO).toList();
    }

    @Override
    public List<BookReadOnlyDTO> getBooksByReleaseYear(String releaseYear) {
        JPAHelperUtil.beginTransaction();
        List<Book> books =  bookDAO.findBooksByReleaseYear(releaseYear);
        JPAHelperUtil.commitTransaction();
        return books.stream().map(mapper::mapToBookReadOnlyDTO).toList();
    }

    @Override
    public List<BookReadOnlyDTO> getBooksByCriteria(Map<String, Object> criteria) {
        JPAHelperUtil.beginTransaction();
        List<Book> books = bookDAO.getByCriteria(criteria);
        JPAHelperUtil.commitTransaction();
        return books.stream().map(mapper::mapToBookReadOnlyDTO).toList();
    }

    @Override
    public List<BookReadOnlyDTO> getAllBooks() {
        JPAHelperUtil.beginTransaction();
        List<Book> books = bookDAO.getAll();
        JPAHelperUtil.commitTransaction();
        return books.stream().map(mapper::mapToBookReadOnlyDTO).toList();
    }
}
