package tw.com.younite.service.exception;

public class InterestException extends ServiceException {
    public InterestException() {
        super();
    }

    public InterestException(String message) {
        super(message);
    }

    public InterestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterestException(Throwable cause) {
        super(cause);
    }

    protected InterestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
