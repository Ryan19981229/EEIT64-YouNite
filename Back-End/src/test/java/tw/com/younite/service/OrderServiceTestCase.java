package tw.com.younite.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.UserEntity;
import tw.com.younite.mapper.OrdersMapper;
import tw.com.younite.mapper.UserMapper;
import tw.com.younite.service.inter.OrdersService;

import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)

public class OrderServiceTestCase {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Test
    public void testLocked() {

        // 获取测试所需的用户信息
        UserEntity user = userMapper.getUserByID(260);
        Date vipDate = user.getVipExpiry();
        Date currentDate = new Date();
        Boolean unlocked = true;

        // 模拟 VIP 过期的情况
        if (vipDate.before(currentDate)) {
            // 在这里可以添加相关的测试逻辑，比如验证是否调用了 ordersMapper.updateLocked() 方法等
            userMapper.lockedVipById(user.getId(),  unlocked);

            System.out.println("ok");
        } else {
            System.out.println("no");

        }


        // 调用被测试的方法
//        ordersService.lockedVip(210, unlocked);

        // 在这里可以添加额外的断言来验证方法的行为和结果
        // 比如验证相关的更新操作是否执行成功
    }

}
