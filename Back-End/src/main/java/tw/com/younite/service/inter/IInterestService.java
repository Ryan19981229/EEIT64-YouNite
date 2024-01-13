package tw.com.younite.service.inter;

import tw.com.younite.entity.UserProfileEntity;

import java.util.List;
import java.util.Map;

public interface IInterestService {
    void setInterests(Integer userID, List<String> interest);
    List<String> getInterests(Integer userID);
    void removeInterests(Integer userID);
    List<Map<String, Object>> findUserProfilesByInterests(Integer userID);
}
