package tw.com.younite.service.exception;

public class DuplicatedLikedUserException extends ServiceException {
    public DuplicatedLikedUserException() {
        super();
    }

    public DuplicatedLikedUserException(String message) {
        super(message);
    }

    public DuplicatedLikedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedLikedUserException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedLikedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
