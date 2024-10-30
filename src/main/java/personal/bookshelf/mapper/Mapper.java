package personal.bookshelf.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import personal.bookshelf.core.enums.RoleType;
import personal.bookshelf.security.PasswordUtil;
import personal.bookshelf.dto.book.BookFiltersDTO;
import personal.bookshelf.dto.book.BookInsertDTO;
import personal.bookshelf.dto.book.BookReadOnlyDTO;
import personal.bookshelf.dto.book.BookUpdateDTO;
import personal.bookshelf.dto.user.UserInsertDTO;
import personal.bookshelf.dto.user.UserReadOnlyDTO;
import personal.bookshelf.model.Book;
import personal.bookshelf.model.User;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@ApplicationScoped
public class Mapper {

    public Book mapToBookEntity(BookInsertDTO bookInsertDTO) {
        return new Book(null, bookInsertDTO.getTitle(), bookInsertDTO.getAuthor(), bookInsertDTO.getReleaseYear());
    }

    public Book mapToBookEntity(BookUpdateDTO bookUpdateDTO) {
        return new Book(bookUpdateDTO.getId(), bookUpdateDTO.getTitle(), bookUpdateDTO.getAuthor(), bookUpdateDTO.getReleaseYear());
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        return new BookReadOnlyDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getReleaseYear());
    }

    public Map<String, Object> mapToCriteria(BookFiltersDTO bookFiltersDTO) {
        Map<String, Object> filters = new HashMap<>();

        if (!(bookFiltersDTO.getTitle() == null) && !(bookFiltersDTO.getTitle().isEmpty())) {
            filters.put("title", bookFiltersDTO.getTitle());
        }

        if (!(bookFiltersDTO.getAuthor() == null) && !(bookFiltersDTO.getAuthor().isEmpty())) {
            filters.put("author", bookFiltersDTO.getAuthor());
        }

        if (!(bookFiltersDTO.getReleaseYear() == null) && !(bookFiltersDTO.getReleaseYear().isEmpty())) {
            filters.put("releaseYear", bookFiltersDTO.getReleaseYear());
        }

        return filters;
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUsername(), user.getRole().name());
    }

    public User mapToUser(UserInsertDTO userInsertDTO) {
        return new User(
                null,
                userInsertDTO.getUsername(),
                PasswordUtil.encryptPassword(userInsertDTO.getPassword()),
                RoleType.valueOf(userInsertDTO.getRole()));
    }
}
