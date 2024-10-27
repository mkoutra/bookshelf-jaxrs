package personal.bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessageDTO {
    private String code;
    private String message;

    public ErrorMessageDTO(String code) {
        this.code = code;
        message = "";
    }
}
