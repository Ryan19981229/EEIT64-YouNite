package tw.com.younite.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.service.inter.IUserLikeService;
import java.util.List;
@SpringBootTest
@RunWith(SpringRunner.class)
public class LikedUserServiceTestCase {
    @Autowired
    IUserLikeService userLikeService;
    @Test
    public void testGetLikedUserList() {
        List<Integer> results= userLikeService.getLikedUserList(126);
        System.out.println("results = " + results);
    }

    @Test
    public void testLikesTracker() {
        List<Integer> results= userLikeService.likesTracker(126);
        System.out.println("results = " + results);
    }

    @Test
    public void testDeleteLikedUsersException() {
        userLikeService.deleteLikedUser(126, 128);
    }

}
