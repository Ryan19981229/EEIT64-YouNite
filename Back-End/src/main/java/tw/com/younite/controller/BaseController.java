package tw.com.younite.controller;

import tw.com.younite.service.uploadException.*;
import tw.com.younite.util.JSONResult;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tw.com.younite.service.exception.*;

import javax.servlet.http.HttpSession;

public class BaseController {
    //Response code for successful request.
    public static final int OK = 200;
    public static final int CREATE_OK = 201;
    public static final int NO_CONTENT_OK = 204;
    //Response code for failed request from front-end.
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED_ERROR = 401;
    public static final int NOT_FOUND_ERROR = 404;
    public static final int CONFLICT_ERROR = 409;
    public static final int PAYLOAD_MAXIMUM_SIZE_ERROR = 413;
    public static final int UNSUPPORTED_FILE_TYPE_ERROR = 415;


    //Response code for internal server.
    public static final int INTERNAL_SERVER_ERROR = 500;


    //網頁註冊頁面產生的異常，會被此控制器所攔截，方法的返回值直接傳遞給前端。
    @ExceptionHandler(ServiceException.class)
    public JSONResult<Void> handleException(Throwable e) {
        JSONResult<Void> result = new JSONResult<>();
        if (e instanceof DuplicatedUsernameException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("帳號已被註冊");
        } else if (e instanceof DuplicatedEmailException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("信箱已被註冊");
        } else if (e instanceof DuplicatedPhoneException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("手機已被註冊");
        } else if (e instanceof DuplicatedFullNameException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("暱稱已被註冊!");
        } else if (e instanceof DuplicatedUserProfileException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("個人資料已經存在!");
        } else if (e instanceof DuplicatedLikedUserException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("喜歡的用戶已存在!");
        } else if (e instanceof BlockedIDAlreadyExistsException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("此用戶已在黑名單內!");
        } else if (e instanceof UserNotFoundException) {
            result.setState(NOT_FOUND_ERROR);
            result.setMessage("帳號不存在!");
        } else if (e instanceof InterestsNotFoundException) {
            result.setState(NOT_FOUND_ERROR);
            result.setMessage("興趣不存在!");
        } else if (e instanceof LikedUserNotFoundException) {
            result.setState(NOT_FOUND_ERROR);
            result.setMessage("喜歡的用戶不存在或已被刪除!");
        } else if (e instanceof BlockedUserNotFoundException) {
            result.setState(NOT_FOUND_ERROR);
            result.setMessage("封鎖的用戶不存在或已被解除封鎖!");
        } else if (e instanceof NoMatchedException) {
            result.setState(NOT_FOUND_ERROR);
            result.setMessage("目前尚未有成功配對的用戶!");
        } else if (e instanceof InvitationNotFoundException) {
            result.setState(NOT_FOUND_ERROR);
            result.setMessage("找不到邀請狀態碼，請重新傳入");
        } else if (e instanceof ProfileNotFoundException) {
            result.setState(NOT_FOUND_ERROR);
            result.setMessage("找不到當前用戶資料，請先建立!");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(UNAUTHORIZED_ERROR);
            result.setMessage("密碼錯誤!");
        } else if (e instanceof LikedUserException) {
            result.setState(INTERNAL_SERVER_ERROR);
            result.setMessage("伺服器異常，無法添加喜歡用戶，請稍後再試!");
        } else if (e instanceof RegisterException) {
            result.setState(INTERNAL_SERVER_ERROR);
            result.setMessage("伺服器異常，無法註冊，請稍後再嘗試! ");
        } else if (e instanceof UpdateException) {
            result.setState(INTERNAL_SERVER_ERROR);
            result.setMessage("更新數據時產生未知的錯誤!");
        } else if (e instanceof InsertProfileException) {
            result.setState(INTERNAL_SERVER_ERROR);
            result.setMessage("伺服器異常，無法新增個人資料，請稍後再嘗試!");
        } else if (e instanceof FileTypeException) {
            result.setState(UNSUPPORTED_FILE_TYPE_ERROR);
            result.setMessage("文件格式錯誤，無法上傳");
        } else if (e instanceof InterestException) {
            result.setState(INTERNAL_SERVER_ERROR);
            result.setMessage("伺服器異常，無法新增個人興趣，請稍後再嘗試!");
        } else if (e instanceof FileEmptyException) {
            result.setState(BAD_REQUEST);
            result.setMessage("文件為空，無法上傳");
        } else if (e instanceof FileSizeException) {
            result.setState(PAYLOAD_MAXIMUM_SIZE_ERROR);
            result.setMessage("文件檔案過大，無法上傳");
        } else if (e instanceof FileStateException) {
            result.setState(CONFLICT_ERROR);
            result.setMessage("文件狀態異常");
        } else if (e instanceof FileUploadIOException) {
            result.setState(INTERNAL_SERVER_ERROR);
            result.setMessage("文件IO異常");
        } else if (e instanceof FileUploadException) {
            result.setState(INTERNAL_SERVER_ERROR);
            result.setMessage("文件上傳時發生異常!");
        }
        return result;
    }

    /**
     * 獲取session物件中的full name
     *
     * @param session session物件
     * @return 當前登陸用戶的full name的值
     */
    protected final String getUsernameFromSession(HttpSession session) {
        //TODO: getAttribute改成full name.
        return session.getAttribute("username").toString();
    }

    protected final Integer getIDFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("id").toString());
    }
}
