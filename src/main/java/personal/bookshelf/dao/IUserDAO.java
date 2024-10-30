package personal.bookshelf.dao;

import personal.bookshelf.model.User;

import java.util.Optional;

public interface IUserDAO extends IGenericCRUD<User> {
    Optional<User> findUserByUsername(String username);
    boolean isUserValid(String username, String plainPassword);
    boolean isUsernameExists(String username);
}
