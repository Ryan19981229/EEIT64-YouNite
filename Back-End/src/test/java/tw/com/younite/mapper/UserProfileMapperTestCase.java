package tw.com.younite.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import tw.com.younite.entity.UserProfileEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserProfileMapperTestCase {
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Test
    public void testInsertProfile() {
        UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setProfileId(1);
        userProfile.setId(2);
        userProfile.setBirthday(new Date());
        userProfile.setGender("Female");
        userProfile.setFullName("Rita");
        Integer result = userProfileMapper.insertProfile(userProfile);
        System.out.println("result = " + result);
        System.out.println("userProfile = " + userProfile);
    }

    @Test
    public void testGetProfileByID() {
        UserProfileEntity userProfile = userProfileMapper.getProfileByID(98);
        System.out.println("userProfile = " + userProfile);
    }

    @Test
    public void testUpdateProfileByID() throws ParseException {
        UserProfileEntity userProfile = userProfileMapper.getProfileByID(98);
        Integer id = userProfile.getUserId();
        userProfile.setFullName("Hello你好嗎");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse("1996-02-06");
        userProfile.setBirthday(parsedDate);
        userProfile.setDatingGoal("Meet husband!");
        userProfile.setGender("Female");
        userProfile.setSexualOrientation("Straight");
        userProfile.setLocation("Taichung");
        userProfile.setSelfIntro("");
        userProfile.setProfileAvatar("/avatar/cat.jpg");
        userProfile.setUserId(id);
        userProfileMapper.updateUserProfileByID(userProfile);
        System.out.println("userProfile = " + userProfile);
    }

    @Test
    public void testInsertProfileWrong() {
        UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setProfileId(2);
        userProfile.setId(3);
        userProfile.setBirthday(new Date());
        userProfile.setGender("Male");
        userProfile.setFullName("Rex");
        Integer result = userProfileMapper.insertProfile(userProfile);
        System.out.println("result = " + result);
        System.out.println("userProfile = " + userProfile);
    }

    @Test
    public void testBlockUsers() throws JsonProcessingException {
        UserProfileEntity userProfile = userProfileMapper.getProfileByID(160);
        ObjectMapper objectMapper= new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("name1", 126);
        json.put("name2", 127);
        String jsonString = objectMapper.writeValueAsString(json);
        userProfile.setBlockId(jsonString);
        System.out.println(jsonString);
        Integer result = userProfileMapper.blockUser(userProfile);
        System.out.println("result = " + result);
        System.out.println("userProfile = " + userProfile);
    }

    @Test
    public void testGetBlockedID() {
        UserProfileEntity result = userProfileMapper.getBlockedID(166);
        String str = result.getBlockId();
        System.out.println("result = " + result);
    }

    @Test
    public void testUnblockID() {
        UserProfileEntity userProfile = userProfileMapper.getProfileByID(165);
        userProfile.setIndex(0);
        Integer result = userProfileMapper.unblockUser(userProfile);
        System.out.println("result = " + result);
    }

    @Test
    public void testGetProfilesByProfessions() {
        System.out.println("Results = " + userProfileMapper.getProfilesByProfession(287, "工程師"));
    }

}
