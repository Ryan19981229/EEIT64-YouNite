package tw.com.younite.service.exception;

public class InterestsNotFoundException extends ServiceException {
    public InterestsNotFoundException() {
        super();
    }

    public InterestsNotFoundException(String message) {
        super(message);
    }

    public InterestsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterestsNotFoundException(Throwable cause) {
        super(cause);
    }

    protected InterestsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
