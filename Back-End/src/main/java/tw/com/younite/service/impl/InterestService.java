package tw.com.younite.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.InterestEntity;
import tw.com.younite.entity.UserProfileEntity;
import tw.com.younite.mapper.InterestMapper;
import tw.com.younite.mapper.UserLikeMapper;
import tw.com.younite.mapper.UserProfileMapper;
import tw.com.younite.service.exception.InterestException;
import tw.com.younite.service.exception.InterestsNotFoundException;
import tw.com.younite.service.inter.IInterestService;
import tw.com.younite.util.DataTransferUtil;

import java.util.*;

@Service
public class InterestService implements IInterestService {

    @Autowired
    InterestMapper interestMapper;

    @Autowired
    UserProfileMapper userProfileMapper;

    @Autowired
    UserLikeMapper userLikeMapper;

    @Autowired
    DataTransferUtil tools;

    @Override
    public void setInterests(Integer userID, List<String> interests) {
        int listSize = interests.size();
        if (listSize != 0) {
            for (String interest : interests) {
                InterestEntity interestEntity = new InterestEntity();
                interestEntity.setUserID(userID);
                interestEntity.setInterest(interest);
                interestMapper.addInterest(interestEntity);
            }
        } else {
            throw new InterestException("");
        }
    }

    @Override
    public List<String> getInterests(Integer userID) {
        List<InterestEntity> interestEntitiesList = interestMapper.getInterests(userID);
        if (interestEntitiesList == null) {
            throw new InterestsNotFoundException("");
        }
        List<String> interestsList = new ArrayList<>();
        for (InterestEntity interestEntity : interestEntitiesList) {
            interestsList.add(interestEntity.getInterest());
        }
        return interestsList;
    }

    @Override
    public void removeInterests(Integer userID) {
        Integer rows = interestMapper.removeInterests(userID);
    }

    @Override
    public List<Map<String, Object>> findUserProfilesByInterests(Integer userID) {
        List<Map<String, Object>> userProfiles = new ArrayList<>();
        UserProfileEntity currentProfile = userProfileMapper.getProfileByID(userID);
        // 取得現在使用者想看的性别
        String gender = currentProfile.getPreferredGender();
        // 取得現在使用者的興趣
        List<InterestEntity> interestEntityList = interestMapper.getInterests(userID);

        Set<Integer> addedUserIDs = new HashSet<>(); // 紀錄已添加過的用戶

        for (InterestEntity interestEntity : interestEntityList) {
            List<InterestEntity> resultList;
            if (!gender.equals("Other")) {
                resultList = interestMapper.findUsersByInterests(interestEntity.getInterest(), userID, gender);
            } else {
                resultList = interestMapper.findAllUsersByInterests(interestEntity.getInterest(), userID);
            }


            if (!resultList.isEmpty()) {
                // 把查詢結果加入List
                for (InterestEntity result : resultList) {
                    UserProfileEntity userProfile = userProfileMapper.getProfileByID(result.getUserID());
                    Integer userProfileID = userProfile.getUserId();
                    if (userLikeMapper.getLikedUsers(userID).contains(userProfileID)) continue;
                    if (!addedUserIDs.contains(userProfile.getUserId())) {
                        Map<String, Object> userProfileMap = new HashMap<>();
                        userProfileMap.put("name", userProfile.getFullName());
                        userProfileMap.put("userID", userProfile.getUserId());
                        userProfileMap.put("profileAvatar", userProfile.getProfileAvatar());
                        userProfileMap.put("age", tools.calculateAge(userProfile.getBirthday()));
                        userProfileMap.put("interests", tools.parseInterests(result.getInterest()));
                        userProfileMap.put("city", userProfile.getCity());
                        userProfileMap.put("dating", userProfile.getDatingGoal());
                        userProfileMap.put("voice", userProfile.getVoiceIntro());
                        userProfileMap.put("selfIntro", userProfile.getSelfIntro());
                        userProfileMap.put("distance", tools.parseDistance(currentProfile, userProfile));
                        userProfiles.add(userProfileMap);
                        addedUserIDs.add(userProfile.getUserId());
                    } else {
                        for (Map<String, Object> userProfileMap : userProfiles) {
                            Integer existingUserID = (Integer) userProfileMap.get("userID");
                            if (existingUserID.equals(userProfileID)) {
                                List<String> existingInterests = new ArrayList<>((List<String>) userProfileMap.get("interests"));
                                List<String> parsedInterests = new ArrayList<>(tools.parseInterests(result.getInterest()));
                                existingInterests.addAll(parsedInterests);
                                userProfileMap.put("interests", existingInterests);
                            }
                        }

                    }
                }
            }
        }
        Collections.shuffle(userProfiles);
        return userProfiles;
    }

}
