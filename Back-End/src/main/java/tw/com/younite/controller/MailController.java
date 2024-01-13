package tw.com.younite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tw.com.younite.entity.UserEntity;
import tw.com.younite.mapper.UserMapper;
import tw.com.younite.service.inter.MailService;
import java.util.Map;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        // 驗證輸入的電子郵件地址是否有效且存在於使用者資料庫中
        UserEntity user = userMapper.getByUserEmail(email);
        if (user != null) {

            // 調用郵件服務發送郵件
            mailService.sendEmail(email);

            return ResponseEntity.ok("Email is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email");
        }
    }

    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String resetToken = request.get("resetToken");
        String newPassword = request.get("newPassword");

        System.out.println("Reset Token: " + resetToken);

        String token = userMapper.tokenByEmail(email);

        System.out.println("Token By Email: " + token);


        // 验证验证码
        if (resetToken == null || token == null || !resetToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
        }

        // 根据邮箱查询用户信息
        UserEntity user = userMapper.getByUserEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email");
        }


        // 使用加密算法对密码进行加密
        String encodedPassword = passwordEncoder.encode(newPassword);


        // 更新用户密码
        userMapper.updatePasswordByEmail(email,encodedPassword);

        return ResponseEntity.ok("Password reset successfully");
    }

}



