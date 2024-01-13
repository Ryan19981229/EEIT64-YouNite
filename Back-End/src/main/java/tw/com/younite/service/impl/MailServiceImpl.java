package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.UserEntity;
import tw.com.younite.mapper.UserMapper;
import tw.com.younite.service.inter.MailService;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserMapper userMapper;

    // 其他依賴注入...

    @Override
    public void sendResetPasswordEmail(String email, String verificationCode) {
        // 驗證輸入的電子郵件地址是否有效且存在於使用者資料庫中
        UserEntity user = userMapper.getByUserEmail(email);
        if (user == null) {
            // 郵件地址無效，可以在這裡處理錯誤情況
            return;
        }

        // 建立郵件內容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // 設定收件人郵件地址
        message.setSubject("younite重置密碼驗證信"); // 設定郵件主題
        message.setText("您的重置密碼驗證碼為：" + verificationCode); // 設定郵件內容
        // 使用 JavaMailSender 發送郵件
        javaMailSender.send(message);
    }
    public void sendEmail(String email) {
        // 生成验证码
        String verificationCode = generateVerificationCode();

        // 获取用户对象
        UserEntity user = userMapper.getByUserEmail(email);
        if (user != null) {
            // 设置重置令牌
            user.setResetToken(verificationCode);

            // 调用邮件服务发送邮件
            sendResetPasswordEmail(email, verificationCode);

            // 更新数据库中的重置令牌
            userMapper.updateResetTokenByEmail(email, verificationCode);
        } else {
            // 处理数据库中不存在该邮箱对应用户的情况
            // 可以返回错误信息或执行其他操作
        }
    }

    private String generateVerificationCode() {
        int codeLength = 6;
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder verificationCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            verificationCode.append(randomChar);
        }

        return verificationCode.toString();
    }

    @Override
    public boolean verifyVerificationCode(String email, String verificationCode) {
        // 根据邮箱查询用户信息
        UserEntity user = userMapper.getByUserEmail(email);
        if (user == null) {
            return false;
        }

        // 获取存储的验证码
        String storedVerificationCode = generateVerificationCode();

        // 比较验证码是否匹配
        return verificationCode.equals(storedVerificationCode);
    }

}
