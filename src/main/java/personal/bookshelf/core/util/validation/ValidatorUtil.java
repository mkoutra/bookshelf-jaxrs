package personal.bookshelf.core.util.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.bookshelf.dao.IUserDAO;
import personal.bookshelf.dao.UserDAO;
import personal.bookshelf.dto.user.UserInsertDTO;
import personal.bookshelf.mapper.Mapper;
import personal.bookshelf.service.IUserService;
import personal.bookshelf.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Utility class that implements Bean Validation.
 */
public class ValidatorUtil {
    private ValidatorUtil() {}

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorUtil.class);
    private static final Validator validator;

    private static final IUserDAO userDAO = new UserDAO();
    private static final Mapper mapper = new Mapper();
    private static final IUserService userService = new UserService(userDAO, mapper);

    static {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        } catch (Exception e) {
            LOGGER.error("Unable to initialize validator");
            throw e;
        }
    }

    public static <T> List<String> validateDTO(T dto) {
        List<String> errors = new ArrayList<>();

        // Bean Validation
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                // Message correspond to messages given in Bean validation annotations in DTOs
                errors.add(violation.getMessage());
            }
        }

        return errors;
    }

    public static List<String> validateDTO(UserInsertDTO userInsertDTO) {
        List<String> errors = new ArrayList<>();

        // Bean Validation
        Set<ConstraintViolation<UserInsertDTO>> violations = validator.validate(userInsertDTO);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserInsertDTO> violation : violations) {
                errors.add(violation.getMessage());
            }
        }

        // Logical Validation
        if (userService.isUsernameExists(userInsertDTO.getUsername())) {
            errors.add("Username already exists.");
        }  else if (!userInsertDTO.getPassword().equals(userInsertDTO.getConfirmPassword())) {
            errors.add("Passwords do not match");
        }

        return errors;
    }
}
