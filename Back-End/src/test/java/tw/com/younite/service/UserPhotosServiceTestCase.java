package tw.com.younite.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.UserPhotosEntity;
import tw.com.younite.service.inter.IUserPhotosService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPhotosServiceTestCase {

    @Autowired
    IUserPhotosService iUserPhotosService;
    @Test
    public void testUserPhotosService() {
        UserPhotosEntity userPhotos = new UserPhotosEntity();
        userPhotos.setProfileID(39);
        userPhotos.setSixthPhotoPath("Hello");
        iUserPhotosService.insertPhotos(userPhotos);
    }
}
