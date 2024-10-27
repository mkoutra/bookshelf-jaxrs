package personal.bookshelf.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import personal.bookshelf.core.util.JPAHelper;
import personal.bookshelf.model.Book;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookDAO extends GenericCRUDImpl<Book> implements IBookDAO {

    public BookDAO() {
        this.setPersistenceClass(Book.class);   // Necessary for GenericCruDImpl
    }

    // The following methods could have been implemented using the getByCriteria() method
    // of GenericCRUDImpl. I just wanted to practice a bit with JPQL queries.

    /**
     * Returns a book with the specified title. If no books exist returns an empty {@link Optional}.
     */
    @Override
    public Optional<Book> findBookByTitle(String title) {
        EntityManager em = getEntityManager();

        try {
            String jpql = "SELECT b FROM Book b WHERE b.title = :title";
            TypedQuery<Book> query = em.createQuery(jpql, Book.class);
            query.setParameter("title", title + "%");

            Book bookFound = query.getSingleResult();

            return Optional.of(bookFound);
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns a list with books written by the given author. If no books exist returns an empty list.
     */
    @Override
    public List<Book> findBooksByAuthor(String author) {
        EntityManager em = getEntityManager();

        String jpql = "SELECT b FROM Book b WHERE b.author = :author";
        TypedQuery<Book> query = em.createQuery(jpql, Book.class);
        query.setParameter("author", author + "%");

        return query.getResultList();
    }

    /**
     * Returns a list with books written in the specified year. If no books exist returns an empty list.
     */
    @Override
    public List<Book> findBooksByReleaseYear(String releaseYear) {
        EntityManager em = getEntityManager();
        String jpql = "SELECT b FROM Book b WHERE b.releaseYear = :year";
        TypedQuery<Book> query = em.createQuery(jpql, Book.class);
        query.setParameter("year", releaseYear);
        return query.getResultList();
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }
}
