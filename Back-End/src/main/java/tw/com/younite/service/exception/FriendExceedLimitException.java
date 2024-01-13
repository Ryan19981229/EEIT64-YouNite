package tw.com.younite.service.exception;

public class FriendExceedLimitException extends ServiceException {
    public FriendExceedLimitException() {
        super();
    }

    public FriendExceedLimitException(String message) {
        super(message);
    }

    public FriendExceedLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendExceedLimitException(Throwable cause) {
        super(cause);
    }

    protected FriendExceedLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
