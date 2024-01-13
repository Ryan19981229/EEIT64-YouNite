package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl {

    @Autowired
    private HttpServletRequest request;


    public String getAccount(){
        return JwtUtil.validateToken(getToken());
    }

    public String getToken(){
        return request.getHeader("Authorization");
    }

    public int getIdFromAccountString(String accountString) {
        String[] parts = accountString.split(", ");
        for (String part : parts) {
            if (part.startsWith("id=")) {
                String idString = part.substring(3);
                return Integer.parseInt(idString);
            }
        }
        return 0; // 如果無法解析 id，可以返回預設值或適當的錯誤處理
    }
}