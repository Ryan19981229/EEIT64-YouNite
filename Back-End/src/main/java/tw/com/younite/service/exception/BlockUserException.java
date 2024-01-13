package tw.com.younite.service.exception;

public class BlockUserException extends ServiceException {
    public BlockUserException() {
        super();
    }

    public BlockUserException(String message) {
        super(message);
    }

    public BlockUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockUserException(Throwable cause) {
        super(cause);
    }

    protected BlockUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
