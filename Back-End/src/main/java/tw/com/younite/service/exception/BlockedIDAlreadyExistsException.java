package tw.com.younite.service.exception;

public class BlockedIDAlreadyExistsException extends ServiceException {
    public BlockedIDAlreadyExistsException() {
        super();
    }

    public BlockedIDAlreadyExistsException(String message) {
        super(message);
    }

    public BlockedIDAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockedIDAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected BlockedIDAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
