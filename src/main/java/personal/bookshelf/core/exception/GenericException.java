package personal.bookshelf.core.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GenericException extends Exception {
    private String code;

    public GenericException(String code, String message) {
        super(message);
        this.code = code;
    }
}
