package tw.com.younite.service.exception;

public class LikedUserException extends ServiceException {
    public LikedUserException() {
        super();
    }

    public LikedUserException(String message) {
        super(message);
    }

    public LikedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikedUserException(Throwable cause) {
        super(cause);
    }

    protected LikedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
