package tw.com.younite.service.exception;

public class DuplicatedUsernameException extends ServiceException {
    public DuplicatedUsernameException() {
        super();
    }

    public DuplicatedUsernameException(String message) {
        super(message);
    }

    public DuplicatedUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedUsernameException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
