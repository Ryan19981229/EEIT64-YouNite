package tw.com.younite.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.InterestEntity;
import tw.com.younite.entity.UserProfileEntity;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InterestMapperTestCase {
    @Autowired
    InterestMapper interestMapper;

    @Autowired
    UserProfileMapper userProfileMapper;

    @Test
    public void testAddInterest() throws JsonProcessingException {
        InterestEntity interest = new InterestEntity();
        interest.setUserID(165);
        String interestsString = "游泳,跑步,爬山";
        interest.setInterest(interestsString);
        interestMapper.addInterest(interest);
    }

    @Test
    public void testGetInterests() {
        List<InterestEntity> interests = interestMapper.getInterests(165);
        System.out.println("interests = " + interests);
    }

    @Test
    public void testFindUsersProfileByInterests() {
        Map<UserProfileEntity, List<String>> mutualInterestMap = new HashMap<>();
        List<InterestEntity> interestEntityList = interestMapper.getInterests(286);

        for (InterestEntity interestEntity : interestEntityList) {
            List<InterestEntity> resultList = interestMapper.findUsersByInterests(interestEntity.getInterest(), 286, "male");
            System.out.println("resultList = " + resultList);
            if (!resultList.isEmpty()) {
                // 将结果添加到已有的结果中
                for (InterestEntity result : resultList) {
                    UserProfileEntity userProfile = userProfileMapper.getProfileByID(result.getUserID());
                    String interest = result.getInterest();
                    mutualInterestMap.computeIfAbsent(userProfile, k -> new ArrayList<>()).add(interest);
                }
            }
        }
        System.out.println("mutualInterestMap = " + mutualInterestMap);
        System.out.println("mutualInterestMap.size() = " + mutualInterestMap.size());
        System.out.println("mutualInterestMap.get(userProfileMapper.getProfileByID(300)) = " + mutualInterestMap.get(userProfileMapper.getProfileByID(300)));
    }
}
