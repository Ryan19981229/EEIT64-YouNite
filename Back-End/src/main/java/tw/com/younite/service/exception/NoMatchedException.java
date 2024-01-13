package tw.com.younite.service.exception;

public class NoMatchedException extends ServiceException {
    public NoMatchedException() {
        super();
    }

    public NoMatchedException(String message) {
        super(message);
    }

    public NoMatchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMatchedException(Throwable cause) {
        super(cause);
    }

    protected NoMatchedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
