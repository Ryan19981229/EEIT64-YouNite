package tw.com.younite.service.exception;

public class BlockedUserNotFoundException extends ServiceException {
    public BlockedUserNotFoundException() {
        super();
    }

    public BlockedUserNotFoundException(String message) {
        super(message);
    }

    public BlockedUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockedUserNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BlockedUserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
