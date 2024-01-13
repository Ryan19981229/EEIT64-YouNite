package tw.com.younite.service.exception;

/** 使用者數據注入資料庫、連接伺服器時產生的異常*/
public class RegisterException extends ServiceException {
    public RegisterException() {
        super();
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }

    protected RegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
