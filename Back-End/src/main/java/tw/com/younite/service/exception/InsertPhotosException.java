package tw.com.younite.service.exception;

public class InsertPhotosException extends ServiceException{
    public InsertPhotosException() {
        super();
    }

    public InsertPhotosException(String message) {
        super(message);
    }

    public InsertPhotosException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertPhotosException(Throwable cause) {
        super(cause);
    }

    protected InsertPhotosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
