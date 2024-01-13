package tw.com.younite.service.inter;

import tw.com.younite.entity.UserEntity;

public interface MailService {
    void sendResetPasswordEmail(String email, String verificationCode);
    void sendEmail(String email);

    boolean verifyVerificationCode(String email, String verificationCode);
}
