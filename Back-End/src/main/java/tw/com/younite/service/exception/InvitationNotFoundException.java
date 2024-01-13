package tw.com.younite.service.exception;

public class InvitationNotFoundException extends ServiceException {
    public InvitationNotFoundException() {
        super();
    }

    public InvitationNotFoundException(String message) {
        super(message);
    }

    public InvitationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvitationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected InvitationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
