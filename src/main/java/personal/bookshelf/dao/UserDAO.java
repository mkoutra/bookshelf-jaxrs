package personal.bookshelf.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import personal.bookshelf.core.util.jpa.JPAHelperUtil;
import personal.bookshelf.core.util.security.PasswordUtil;
import personal.bookshelf.model.User;

import java.util.Optional;

@ApplicationScoped
public class UserDAO extends GenericCRUDImpl<User> implements IUserDAO {

    public UserDAO() {
        this.setPersistenceClass(User.class);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {
            EntityManager em = JPAHelperUtil.getEntityManager();
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isUserValid(String username, String plainPassword) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {
            EntityManager em = JPAHelperUtil.getEntityManager();
            User user = em.createQuery(jpql, User.class)
                          .setParameter("username", username)
                          .getSingleResult();
            return PasswordUtil.checkPassword(plainPassword, user.getPassword());
        } catch(NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username";
        try {
            EntityManager em = JPAHelperUtil.getEntityManager();
            return em.createQuery(jpql, Boolean.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
    }
}
