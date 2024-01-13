package tw.com.younite.service.exception;

public class DuplicatedPhoneException extends ServiceException {
    public DuplicatedPhoneException() {
        super();
    }

    public DuplicatedPhoneException(String message) {
        super(message);
    }

    public DuplicatedPhoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedPhoneException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedPhoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
