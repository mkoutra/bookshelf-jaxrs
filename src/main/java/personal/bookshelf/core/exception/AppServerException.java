package personal.bookshelf.core.exception;

public class AppServerException extends GenericException {
    private static String DEFAULT_CODE = "AppServerError";

    public AppServerException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
