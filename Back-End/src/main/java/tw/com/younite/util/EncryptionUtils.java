package tw.com.younite.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class EncryptionUtils {
    @Value("${encryption.secretKeyF}")
    public String SECRETKEYF;

    @Value("${encryption.secretKeyB}")
    private String SECRETKEYB;

    public String encrypt(String salt) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRETKEYF.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(salt.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String salt) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRETKEYF.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(salt));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(EncryptionUtils.class);
        EncryptionUtils encryptionUtils = context.getBean(EncryptionUtils.class);
        try {
            String salt = "EEIT64BIGCHRYSANTHEMUM";
            String encryptPassword = encryptionUtils.encrypt(salt);
            System.out.println("encryptPassword = " + encryptPassword);
            System.out.println("SECRETKEYF = " + encryptionUtils.SECRETKEYF);

            String decryptedBytes = encryptionUtils.decrypt(encryptPassword);
            System.out.println("decryptedBytes = " + decryptedBytes);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
