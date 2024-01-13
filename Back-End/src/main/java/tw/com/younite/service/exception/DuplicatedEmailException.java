package tw.com.younite.service.exception;

public class DuplicatedEmailException extends ServiceException {
    public DuplicatedEmailException() {
        super();
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }

    public DuplicatedEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEmailException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
