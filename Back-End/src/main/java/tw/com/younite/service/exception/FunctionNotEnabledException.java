package tw.com.younite.service.exception;

public class FunctionNotEnabledException extends ServiceException {
    public FunctionNotEnabledException() {
        super();
    }

    public FunctionNotEnabledException(String message) {
        super(message);
    }

    public FunctionNotEnabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionNotEnabledException(Throwable cause) {
        super(cause);
    }

    protected FunctionNotEnabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
