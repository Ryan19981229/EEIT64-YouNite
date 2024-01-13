package tw.com.younite.service.inter;

import com.fasterxml.jackson.core.JsonProcessingException;
import tw.com.younite.entity.UserProfileEntity;
import java.util.List;
import java.util.Map;

public interface IUserProfileService {
    void insertProfile(UserProfileEntity userProfile);
    void resetUserProfile(UserProfileEntity userprofile);
    UserProfileEntity getUserProfile(Integer id);
    List<Integer> getBlockedID(Integer userID);
    void blockUser(Integer userID, Integer blockedUserID);
    void unblockUser(Integer userID, Integer blockedUserID);
    List<Map<String, Object>> getProfilesByProfession(Integer userID);
    String getPreferredGender(Integer userID);
    List<Integer> getProfilesByPreferredGender(Integer userId, String preferredGender);
}
