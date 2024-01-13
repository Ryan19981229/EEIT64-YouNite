package tw.com.younite.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import tw.com.younite.entity.UserEntity;
import tw.com.younite.entity.UserProfileEntity;
import tw.com.younite.mapper.InterestMapper;
import tw.com.younite.mapper.UserMapper;
import tw.com.younite.mapper.UserProfileMapper;
import tw.com.younite.service.exception.*;
import tw.com.younite.service.inter.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.util.DataTransferUtil;

import java.util.*;

@Service
public class UserProfileServiceImpl implements IUserProfileService {
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InterestMapper interestMapper;

    @Autowired
    private DataTransferUtil tools;

    @Override
    public void insertProfile(UserProfileEntity userProfile) {
        //檢查暱稱是否重複
        String fullName = userProfile.getFullName();
        if (userProfileMapper.getByFullName(fullName) != null) {
            throw new DuplicatedFullNameException("暱稱重複!");
        }
        Integer rows = userProfileMapper.insertProfile(userProfile);
        if (rows != 1) {
            throw new InsertProfileException("伺服器/資料庫異常!");
        }
    }

    @Override
    public UserProfileEntity getUserProfile(Integer id) {
        if (userMapper.getUserByID(id) == null) {
            throw new UserNotFoundException("使用者帳號不存在!");
        }
        return userProfileMapper.getProfileByID(id);
    }

    @Override
    public void resetUserProfile(UserProfileEntity userprofile) {
        Integer row = userProfileMapper.updateUserProfileByID(userprofile);
        if (row != 1) {
            throw new UpdateException();
        }
    }

    @Override
    public void blockUser(Integer userID, Integer blockedUserID) {
        UserProfileEntity userProfileEntity = userProfileMapper.getProfileByID(userID);
        if (userMapper.getUserByID(blockedUserID) == null) {
            throw new UserNotFoundException("欲封鎖的用戶不存在");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("blocked", blockedUserID);
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        userProfileEntity.setBlockId(jsonString);
        Integer rows = userProfileMapper.blockUser(userProfileEntity);
        if (rows != 1) {
            throw new BlockUserException("伺服器異常，無法封鎖用戶，請稍後再試!");
        }
    }

    @Override
    public List<Integer> getBlockedID(Integer userID) {
        UserProfileEntity userProfile = userProfileMapper.getProfileByID(userID);
        List<Integer> blockedUsersList = new ArrayList<>();
        String jsonString = userProfile.getBlockId();
        if (jsonString == null) {
            return blockedUsersList;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonArray = objectMapper.readTree(jsonString);
            for (JsonNode element: jsonArray) {
                if (element.has("blocked")) {
                    blockedUsersList.add(element.get("blocked").asInt());
                }
            }
            System.out.println("blockedUsersList = " + blockedUsersList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blockedUsersList;
    }

    @Override
    public void unblockUser(Integer userID, Integer unblockedUserID) {
        UserProfileEntity userProfileEntity = userProfileMapper.getProfileByID(userID);
        List<Integer> blockedList = getBlockedID(userID);
        if (blockedList == null)
            return;
        int index = blockedList.indexOf(unblockedUserID);
        if (index == -1) {
            throw new BlockedUserNotFoundException("用戶不存在");
        }
        userProfileEntity.setIndex(index);
        userProfileMapper.unblockUser(userProfileEntity);
    }

    @Override
    public List<Map<String, Object>> getProfilesByProfession(Integer userID) {
        UserProfileEntity currentUserProfile = userProfileMapper.getProfileByID(userID);
        String profession = currentUserProfile.getProfessions();
        String preferredGender = currentUserProfile.getPreferredGender();
        List<UserProfileEntity> profileLists = userProfileMapper.getProfilesByProfession(userID, profession);
        List<Map<String, Object>> results = new ArrayList<>();
        if (profileLists != null) {
            for (UserProfileEntity profile: profileLists) {
                if (Objects.equals(profile.getGender(), preferredGender)
                        || preferredGender.equals("Other")) {
                    Map<String, Object> userProfile = new HashMap<>();
                    Integer ID = profile.getUserId();
                    userProfile.put("userID", ID);
                    userProfile.put("name", profile.getFullName());
                    userProfile.put("avatar", profile.getProfileAvatar());
                    userProfile.put("age", tools.calculateAge(profile.getBirthday()));
                    userProfile.put("interests", tools.parseInterestEntities(interestMapper.getInterests(ID)));
                    userProfile.put("distance", tools.parseDistance(currentUserProfile, profile));
                    results.add(userProfile);
                }
            }
        }
        return results;
    }

    @Override
    public String getPreferredGender(Integer userID) {
        return userProfileMapper.getPreferredGender(userID);
    }

    @Override
    public List<Integer> getProfilesByPreferredGender(Integer userId,String preferredGender) {
        List<UserEntity> userList = null;
        List<UserProfileEntity> userProfileList = null;
        if (preferredGender.equals("Other")) {
            userList = userMapper.getAllUsers();
        } else {
            userProfileList = userProfileMapper.getProfilesByPreferredGender(userId, preferredGender);
        }

        List<Integer> matchResult = new ArrayList<>();
        if (userList != null) {
            for (UserEntity users: userList) {
                matchResult.add(users.getId());
            }
        } else {
            for (UserProfileEntity profiles: userProfileList) {
                matchResult.add(profiles.getUserId());
            }
        }
        return matchResult;
    }
}
