package tw.com.younite.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.UserProfileEntity;
import tw.com.younite.service.inter.IInterestService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InterestServiceTestCase {
    @Autowired
    IInterestService iInterestService;
    @Test
    public void testSetInterestService() {
        List<String> list = new ArrayList<>();
        list.add("游泳");
        list.add("跑步");
        list.add("登山");
        list.add("睡覺");
        list.add("玩耍");
        list.add("打球");
        list.add("打牌");
        iInterestService.setInterests(300, list);
    }
    @Test
    public void testGetInterestService() {
        System.out.println("Result = " + iInterestService.getInterests(300));
    }

    @Test
    public void testRemovedInterests() {
        iInterestService.removeInterests(312);
    }

    @Test
    public void testGetMutualInterests() {
        System.out.println("iInterestService.findUserProfilesByInterests(286) = " + iInterestService.findUserProfilesByInterests(4));
    }
}
