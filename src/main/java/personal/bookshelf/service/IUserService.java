package personal.bookshelf.service;

import personal.bookshelf.core.exception.AppServerException;
import personal.bookshelf.core.exception.EntityAlreadyExistsException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.dto.book.BookReadOnlyDTO;
import personal.bookshelf.dto.user.UserInsertDTO;
import personal.bookshelf.dto.user.UserReadOnlyDTO;
import personal.bookshelf.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserReadOnlyDTO insertUser(UserInsertDTO userInsertDTO) throws AppServerException;
    void deleteUser(Long id) throws EntityNotFoundException;
    UserReadOnlyDTO getUserById(Long id) throws EntityNotFoundException;
    List<UserReadOnlyDTO> getAllUsers();
    UserReadOnlyDTO findUserByUsername(String username) throws EntityNotFoundException;
    boolean isUserValid(String username, String plainPassword);
    boolean isUsernameExists(String username);
}
