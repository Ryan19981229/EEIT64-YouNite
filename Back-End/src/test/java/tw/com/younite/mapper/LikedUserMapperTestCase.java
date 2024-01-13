package tw.com.younite.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.UserLikeEntity;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LikedUserMapperTestCase {
    @Autowired
    private UserLikeMapper userLikeMapper;

    @Test
    public void testInsertLike() {
        UserLikeEntity userLikeEntity = new UserLikeEntity();

        userLikeEntity.setUserID(126);
        userLikeEntity.setLikedUserID(128);
        userLikeEntity.setLikedAt(new Date());
        Integer result = userLikeMapper.insertLike(userLikeEntity);
        System.out.println("userLikeEntity = " + userLikeEntity);
        System.out.println("result = " + result);
    }

    @Test
    public void testGetLikedUsers() {
        List<UserLikeEntity> userEntity = userLikeMapper.getLikedUsers(126);
        System.out.println("userEntity = " + userEntity);
    }

    @Test
    public void testDeleteLikesUsers() {
        Integer rows = userLikeMapper.removeLikedUsers(162, 162);
    }

    @Test
    public void testLikesTracker() {
        List<UserLikeEntity> list = userLikeMapper.likesTracker(126);
        System.out.println("list = " + list);
    }
}
