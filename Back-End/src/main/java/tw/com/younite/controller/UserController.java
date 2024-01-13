// UserController.java
package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tw.com.younite.entity.LoginResponse;
import tw.com.younite.entity.UserEntity;
import tw.com.younite.service.impl.TokenServiceImpl;
import tw.com.younite.service.inter.IUserService;
import tw.com.younite.util.EncryptionUtils;
import tw.com.younite.util.JSONResult;
import tw.com.younite.util.JwtUtil;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.younite.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;


@Api(tags = "登入與註冊頁面")
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController extends BaseController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private EncryptionUtils encryptionUtils;

    @Autowired
    private TokenServiceImpl token;

    @ApiOperation("用ID獲取相應使用者資訊")
    @GetMapping("/users/getUser/{userID}")
    public JSONResult<UserEntity> getUser(@ApiParam(value = "傳出以ID尋找的使用者資訊", required = true)
                                          @PathVariable Integer userID, HttpSession session) throws Exception {
        UserEntity user = iUserService.getUserByID(userID);
        return new JSONResult<UserEntity>(OK, user);
    }

    @ApiOperation("獲取當前用戶資訊(需先登入)")
    @GetMapping("/users/getUser")
    public JSONResult<UserEntity> getCurrentUser(@ApiParam(value = "傳出使用者資訊", required = true)HttpSession session) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = token.getAccount();
        Integer userId = token.getIdFromAccountString(username);
        UserEntity user = iUserService.getUserByID(userId);
        return new JSONResult<UserEntity>(OK, user);
    }

    @ApiOperation("註冊新用戶")
    @PostMapping("/users/register")
    public JSONResult<Void> reg(@ApiParam(value = "傳入以註冊用戶資訊", required = true) @RequestBody UserEntity user,
                                HttpSession session) {
        iUserService.reg(user);
        session.setAttribute("id", user.getId());
        return new JSONResult<>(CREATE_OK, "註冊成功");
    }

    @ApiOperation("透過OAuth註冊新用戶")
    @PostMapping("/users/registerByOAuth")
    public JSONResult<Void> register(@ApiParam(value = "傳入以第三方註冊資訊", required = true) @RequestBody UserEntity user,
                                     HttpSession session) {
        user.setGoogleUser("1");
        user.setThirdPartyLogin(true);
        iUserService.regByOAuth(user);
        session.setAttribute("id", user.getId());
        return new JSONResult<>(CREATE_OK, "註冊成功");
    }

    @ApiOperation("用戶登出")
    @PostMapping("/users/logout")
    public JSONResult<Void> Logout(@ApiParam(value = "傳入用戶登出", required = true) HttpSession session) {
        session.removeAttribute("id");
        session.removeAttribute("username");
        return new JSONResult<>(OK, "成功登出");
    }

    @ApiOperation("用戶登入")
    @PostMapping("/users/login")
    public JSONResult<LoginResponse> login(HttpServletRequest request, @RequestBody UserEntity user) {
        UserEntity data = iUserService.login(user);
        HttpSession session = request.getSession();
        session.setAttribute("id", data.getId());
        session.setAttribute("username", data.getUsername());

        // Generate JWT token
        String token = JwtUtil.generateToken(data);

        // Create the LoginResponse object with user and token
        LoginResponse loginResponse = new LoginResponse(data, token);

        return new JSONResult<>(OK, "登入成功", loginResponse);
    }


    @ApiOperation("修改用戶密碼")
    @PutMapping("/users/resetPassword")
    public JSONResult<Void> changePassword(@ApiParam(value = "傳入修改後的密碼資料", required = true) String oldPassword,
                                           String newPassword, HttpSession session) {
        Integer id = getIDFromSession(session);
        iUserService.resetPassword(id, oldPassword, newPassword);
        return new JSONResult<>(NO_CONTENT_OK, "密碼修改成功");
    }

    @ApiOperation("修改用戶密碼（透過用戶ID）")
    @PutMapping("/users/resetPassword/{userID}")
    public JSONResult<Void> changePasswordTwo(@ApiParam(value = "傳入修改用戶密碼（透過用戶ID）", required = true)
                                              @PathVariable("userID") Integer userID, String oldPassword,
                                              String newPassword, HttpSession session) {
        iUserService.resetPassword(userID, oldPassword, newPassword);
        return new JSONResult<>(NO_CONTENT_OK, "密碼修改成功");
    }
    @PutMapping("/lastlog/{userID}")
    public JSONResult<Void> lastLog(@PathVariable("userID") Integer id){
        iUserService.updateLogTime(id);
        return new JSONResult<>(NO_CONTENT_OK, "時間修改成功");

    }

}