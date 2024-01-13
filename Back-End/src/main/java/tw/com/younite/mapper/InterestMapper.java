package tw.com.younite.mapper;

import tw.com.younite.entity.InterestEntity;
import tw.com.younite.entity.UserProfileEntity;

import java.util.List;
import java.util.Map;

public interface InterestMapper {
    Integer addInterest(InterestEntity interest);
    List<InterestEntity> getInterests(Integer userID);
    List<InterestEntity> findUsersByInterests(String interest, Integer userID, String gender);
    List<InterestEntity> findAllUsersByInterests(String interest, Integer userID);
    Integer removeInterests(Integer userID);
    Integer updateInterests(Integer userID);


}
