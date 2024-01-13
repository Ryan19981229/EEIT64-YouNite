package tw.com.younite.mapper;

import tw.com.younite.entity.OrdersEntity;
import tw.com.younite.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTestCase {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Test
    public void testRegister() {
        UserEntity user = new UserEntity();
        user.setId(200);
        user.setEmail("gfdhgdhgfudihidugohigh@gmail.com");
        user.setUsername("fuigdfhigflhidiglhd");
        user.setPassword("aaa520520");
        Integer result = userMapper.register(user);
        System.out.println("result = " + result);
    }

    @Test
    public void testRegisterByPAuth() {
        UserEntity user = new UserEntity();
        user.setId(200);
        user.setEmail("qoqo850206123@gmail.com");
        user.setUsername("David123");
        user.setGoogleUser("1");
        user.setThirdPartyLogin(true);
        Integer result = userMapper.registerByOAuth(user);
        System.out.println("OAuth result = " + result);
    }

    @Test
    public void testGetByUsername() {
        UserEntity user = userMapper.getByUsername("David");
        System.out.println("user = " + user);
    }

    @Test
    public void testUpdatePasswordByID() {
        Integer result  = userMapper.updatePasswordByID(43, "DDD", new Date());
        System.out.println("result = " + result);
    }

    @Test
    public void testGetByID() {
        UserEntity user = userMapper.getUserByID(44);
        System.out.println("user = " + user);
    }

    @Test
    public void testUpdateVipById() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiry = dateFormat.parse("2023-09-05 14:49:54");
        userMapper.updateVipById(210, expiry , true);
    }

    @Test
    public void testGetByTradeNo () {
        OrdersEntity order = ordersMapper.getByTradeNo("Y1688538055138");
        System.out.println("order = " + order);
    }

    @Test
    public void trial() {
        List<Map<String, Object>> result = userMapper.trial();
        System.out.println("result = " + result);
    }
}
