package tw.com.younite.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.UserPhotosEntity;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPhotosTestCase {

    @Autowired
    UserPhotosMapper userPhotosMapper;
    @Test
    public void testAddPhotos() {
        UserPhotosEntity userPhotos = new UserPhotosEntity();
        userPhotos.setProfileID(43);
        userPhotos.setFirstPhotoPath("Hello.jpg");
        userPhotos.setThirdPhotoPath("HelloWorld.jpg");
        userPhotos.setFifthPhotoPath("Hello你好嗎.jpg");
        userPhotosMapper.addPhotos(userPhotos);
    }
}
