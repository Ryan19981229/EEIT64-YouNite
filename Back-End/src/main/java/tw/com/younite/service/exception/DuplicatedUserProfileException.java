package tw.com.younite.service.exception;

public class DuplicatedUserProfileException extends ServiceException {
    public DuplicatedUserProfileException() {
        super();
    }

    public DuplicatedUserProfileException(String message) {
        super(message);
    }

    public DuplicatedUserProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedUserProfileException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedUserProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
