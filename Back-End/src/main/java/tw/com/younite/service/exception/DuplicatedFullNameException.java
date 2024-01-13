package tw.com.younite.service.exception;

public class DuplicatedFullNameException extends ServiceException {
    public DuplicatedFullNameException() {
        super();
    }

    public DuplicatedFullNameException(String message) {
        super(message);
    }

    public DuplicatedFullNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedFullNameException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedFullNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
