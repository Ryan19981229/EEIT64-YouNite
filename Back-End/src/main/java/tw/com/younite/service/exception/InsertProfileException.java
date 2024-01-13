package tw.com.younite.service.exception;

public class InsertProfileException extends ServiceException {
    public InsertProfileException() {
        super();
    }

    public InsertProfileException(String message) {
        super(message);
    }

    public InsertProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertProfileException(Throwable cause) {
        super(cause);
    }

    protected InsertProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
