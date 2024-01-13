package tw.com.younite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.younite.entity.users;
import tw.com.younite.mapper.IUserDao;
import tw.com.younite.service.inter.IUserServiceMP;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImplMP extends ServiceImpl<IUserDao, users> implements IUserServiceMP {
    @Autowired
    private IUserDao iUserDao ;
    @Override
    @Transactional
    public users loginByGoogle(String token) {
        users user = new users();
        user.setUsername(parseJSON(token).get("name").asText());
        user.setEmail(parseJSON(token).get("email").asText());
        user.setThirdPartyLogin(true);
        System.out.println(user + "service測試");
        return user;
//        LambdaQueryWrapper<users> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(users::getEmail, user.getEmail()).eq(users::getThirdPartyLogin,true);
//        List<users> result = iUserDao.selectList(lqw);
//        if (result.isEmpty()) {
//            return user;
//        } else {
//            return null;
//        }
    }
    public static JsonNode parseJSON(String token){
        String[] parts = token.split("\\.", 0);


        byte[] bytes = Base64.getUrlDecoder().decode(parts[1]);
        String decodedString = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(decodedString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return jsonNode;
    }
}
