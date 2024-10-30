package personal.bookshelf.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.bookshelf.core.exception.AppServerException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.core.util.jpa.JPAHelperUtil;
import personal.bookshelf.dao.IUserDAO;
import personal.bookshelf.dto.user.UserInsertDTO;
import personal.bookshelf.dto.user.UserReadOnlyDTO;
import personal.bookshelf.mapper.Mapper;
import personal.bookshelf.model.User;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserService implements IUserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final IUserDAO userDAO;
    private final Mapper mapper;

    @Override
    public UserReadOnlyDTO insertUser(UserInsertDTO userInsertDTO) throws AppServerException {
        try {
            JPAHelperUtil.beginTransaction();
            User user = mapper.mapToUser(userInsertDTO);

            UserReadOnlyDTO userReadOnlyDTO = userDAO.insert(user)
                    .map(mapper::mapToUserReadOnlyDTO)
                    .orElseThrow(() -> new AppServerException("User", "User with username: " + userInsertDTO.getUsername() + "not inserted."));
            JPAHelperUtil.commitTransaction();
            LOGGER.info("User with username {} inserted.", userInsertDTO.getUsername());
            return userReadOnlyDTO;
        } catch (AppServerException e) {
            JPAHelperUtil.rollbackTransaction();
            LOGGER.error("User with username: {} not inserted.", userInsertDTO.getUsername());
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public UserReadOnlyDTO findUserByUsername(String username) throws EntityNotFoundException {
        try {
            JPAHelperUtil.beginTransaction();
            UserReadOnlyDTO userReadOnlyDTO = userDAO.findUserByUsername(username)
                    .map(mapper::mapToUserReadOnlyDTO)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with username " + username + " not found."));
            JPAHelperUtil.commitTransaction();
            return userReadOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("User with username: {} not found.", username);
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public boolean isUserValid(String username, String plainPassword) {
        try {
            JPAHelperUtil.beginTransaction();
            boolean isValid = userDAO.isUserValid(username, plainPassword);
            JPAHelperUtil.commitTransaction();
            return isValid;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        try {
            JPAHelperUtil.beginTransaction();
            boolean usernameExists = userDAO.isUsernameExists(username);
            JPAHelperUtil.commitTransaction();
            return usernameExists;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        try {
            JPAHelperUtil.beginTransaction();
            userDAO.getById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with id " + id + " was not found."));
            userDAO.delete(id);
            JPAHelperUtil.commitTransaction();

            LOGGER.info("User with id: {} was deleted.", id);
        } catch (Exception e) {
            LOGGER.error("User with id {} was not deleted.", id);
            JPAHelperUtil.rollbackTransaction();
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public UserReadOnlyDTO getUserById(Long id) throws EntityNotFoundException {
        try {
            JPAHelperUtil.beginTransaction();

            UserReadOnlyDTO userReadOnlyDTO = userDAO.getById(id)
                    .map(mapper::mapToUserReadOnlyDTO)
                    .orElseThrow(() -> new EntityNotFoundException("User",
                            "User with id " + id + " was not found."));
            JPAHelperUtil.commitTransaction();

            LOGGER.info("User with id {} was found.", id);

            return userReadOnlyDTO;
        } catch (Exception e) {
            LOGGER.error("User with id {} was not found.", id);
            JPAHelperUtil.rollbackTransaction();
            throw e;
        } finally {
            JPAHelperUtil.closeEntityManager();
        }
    }

    @Override
    public List<UserReadOnlyDTO> getAllUsers() {
        JPAHelperUtil.beginTransaction();
        List<User> users = userDAO.getAll();
        JPAHelperUtil.commitTransaction();
        return users.stream().map(mapper::mapToUserReadOnlyDTO).toList();
    }
}
