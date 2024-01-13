package tw.com.younite.service.exception;

public class LikedUserNotFoundException extends ServiceException {
    public LikedUserNotFoundException() {
        super();
    }

    public LikedUserNotFoundException(String message) {
        super(message);
    }

    public LikedUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikedUserNotFoundException(Throwable cause) {
        super(cause);
    }

    protected LikedUserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
