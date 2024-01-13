package tw.com.younite.service;

import tw.com.younite.entity.UserEntity;
import tw.com.younite.service.exception.ServiceException;
import tw.com.younite.service.inter.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTestCase {

    @Autowired
    private IUserService iUserService;

    @Test
    public void testReg() {
        try {
            UserEntity user = new UserEntity();
            user.setUsername("Rita");
            user.setPassword("123456");
            user.setEmail("Rita123@gmail.com");
            iUserService.reg(user);
            System.out.println("Done");
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getClass().getName());
            System.out.println(serviceException.getMessage());
        }
    }


    @Test
    public void testReg01() {
        try {
            UserEntity user = new UserEntity();
            user.setUsername("Rex");
            user.setPassword("12345689");
            user.setEmail("Rex123@gmail.com");
            iUserService.reg(user);
            System.out.println("Done");
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getClass().getName());
            System.out.println(serviceException.getMessage());
        }
    }

    @Test
    public void testReg02() {
        try {
            UserEntity user = new UserEntity();
            user.setUsername("Rex1");
            user.setPassword("123456898");
            user.setEmail("Rex123444@gmail.com");
            iUserService.reg(user);
            System.out.println("Done");
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getClass().getName());
            System.out.println(serviceException.getMessage());
        }
    }

    @Test
    public void testReg03() {
        try {
            UserEntity user = new UserEntity();
            user.setUsername("Leo");
            user.setPassword("1234567");
            user.setEmail("Leo123@gmail.com");
            iUserService.reg(user);
            System.out.println("Done");
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getClass().getName());
            System.out.println(serviceException.getMessage());
        }
    }

    @Test
    public void testReg04() {
        try {
            UserEntity user = new UserEntity();
            user.setUsername("Lucas");
            user.setPassword("1234567");
            user.setEmail("Lucas123@gmail.com");
            iUserService.reg(user);
            System.out.println("Done");
        } catch (ServiceException serviceException) {
            System.out.println(serviceException.getClass().getName());
            System.out.println(serviceException.getMessage());
        }
    }

    @Test
    public void test05Login() {
        UserEntity user = new UserEntity();
        user.setUsername("David76666");
        user.setPassword("david888777");
        UserEntity newUser = iUserService.login(user);
        System.out.println("user = " + newUser);
    }

    @Test
    public void testChangePassword() {
        iUserService.resetPassword(6, "1234567", "7654321");
    }
}
