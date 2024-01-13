package tw.com.younite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.com.younite.entity.UserProfileEntity;
import tw.com.younite.service.inter.IInterestService;
import tw.com.younite.service.inter.IUserLikeService;
import tw.com.younite.service.inter.IUserProfileService;
import tw.com.younite.service.inter.IUserService;

import java.util.*;

@Component
public class RecommendationUtil {
    public static final int THRESHOLD = 10;

    @Autowired
    IUserLikeService likeService;
    @Autowired
    IInterestService interestService;
    @Autowired
    IUserService userService;
    @Autowired
    IUserProfileService userProfileService;
    @Autowired
    DataTransferUtil tools;


    public List<Map<String, Object>> recommendation(Integer currentUserID) {
        //Phase 1: 冷啟動階段(新註冊用戶，喜歡人數<=10時，採用普通興趣篩選)
        UserProfileEntity currentProfile = userProfileService.getUserProfile(currentUserID);
        List<Integer> likedList = likeService.getLikedUserList(currentUserID);
        int likedSize = likedList.size();
        String preferredGender = currentProfile.getPreferredGender();

        if (likedSize <= THRESHOLD) {
            List<Map<String, Object>> recommendList = interestService.findUserProfilesByInterests(currentUserID);
            recommendList.removeIf(recommendUsers -> likedList.contains(recommendUsers.get("userID")));
            Collections.shuffle(recommendList);
            return recommendList;
        }
        //Phase 2: 喜歡用戶 > 10後，採用協同過濾演算法
        //Phase 2.1: 找出喜歡用戶重疊度高的使用者，並計算sim
        List<Map<String, Integer>> similarityList = new ArrayList<>();
        List<Integer> allUsers = userProfileService.getProfilesByPreferredGender(currentUserID, preferredGender);
        System.out.println("allUsers = " + allUsers);
        HashSet<Integer> internalSet = new HashSet<>(likedList);
        //Phase 2.1.1: 拿出所有用戶的likedUsers並逐一比對，並計算相似度
        for (Integer user : allUsers) {
                Map<String, Integer> userMap = new HashMap<>();
                HashSet<Integer> intersectionSet = new HashSet<>(internalSet);
                HashSet<Integer> externalSet = new HashSet<>(likeService.getLikedUserList(user));

                //計算聯集
                HashSet<Integer> unionSet = new HashSet<>(intersectionSet);
                unionSet.addAll(externalSet);
                int unions = unionSet.size();
                //計算交集
                intersectionSet.retainAll(externalSet);
                int intersections = intersectionSet.size();
                //計算相似度分數
                int similarity = unions == 0 ? 0 : (intersections / unions);
                userMap.put("userID", user);
                userMap.put("similarity", similarity);
                similarityList.add(userMap);
        }
        //依據相似度分數做排序
        similarityList.sort(Comparator.comparing(m -> m.get("similarity")));
        Boolean isAllZeros = true;
        for (Map<String, Integer> sim: similarityList) {
            if (sim.get("similarity") != 0) {
                isAllZeros = false;
                break;
            } else {
                List<Map<String, Object>> recommendList = interestService.findUserProfilesByInterests(currentUserID);
                recommendList.removeIf(recommendUsers -> likedList.contains(recommendUsers.get("userID")));
                Collections.shuffle(recommendList);
                return recommendList;
            }
        }

        List<Map<String, Integer>> scoresList = new ArrayList<>();
        List<Integer> comparingList;

        if (preferredGender.equals("Other")) {
            comparingList = userService.getAllUsers();
        } else {
            comparingList = userProfileService.getProfilesByPreferredGender(currentUserID, preferredGender);
        }


        //Phase 2.1.2: 提取相似度前10名使用者的喜歡用戶名單(10個使用者)，並排除已存在現在使用者喜歡名單的用戶
        for (int i = 0; i < 10; i++) {
            // 拿取前十名類似使用者的喜歡清單
            List<Integer> similarUserLikedList = likeService.getLikedUserList(similarityList.get(i).get("userID"));
            // 遍歷相遇度高前十名的喜歡清單
            for (Integer target : similarUserLikedList) {
                if (likedList.contains(target)) continue;
                Map<String, Integer> scoring = new HashMap<>();
                // 將喜歡清單中的用戶逐一放入map
                scoring.put("id", target);
                //Phase 2.1.3: 將相似度分數 * 所有使用者(喜歡的話1分、沒有喜歡0分)
                // 如果所有使用者中包含相似度最高的10個使用喜歡清單
                if (comparingList.contains(target)) {
                    int existingScore = scoring.getOrDefault("score", 0);
                    //將分數更新，公式為相似度 * 1
                    int score = similarityList.get(i).get("similarity");
                    int newScore = existingScore + score;
                    scoring.put("score", newScore);
                }
                //將map放回ScoreList中，以方便排序
                scoresList.add(scoring);
            }
        }
        scoresList.sort(Comparator.comparing(s -> s.get("score")));

        //Phase 3.0: 回傳推薦用戶名單
        List<Map<String, Object>> recommendationsList = new ArrayList<>();
        for (Map<String, Integer> userRanking : scoresList) {
            Map<String, Object> recommendations = new HashMap<>();
            Integer id = userRanking.get("id");
            UserProfileEntity profile = userProfileService.getUserProfile(id);
            recommendations.put("name", profile.getFullName());
            recommendations.put("userID", profile.getUserId());
            recommendations.put("profileAvatar", profile.getProfileAvatar());
            recommendations.put("age", tools.calculateAge(profile.getBirthday()));
            recommendations.put("interests", interestService.getInterests(id));
            recommendations.put("city", profile.getCity());
            recommendations.put("dating", profile.getDatingGoal());
            recommendations.put("voice", profile.getVoiceIntro());
            recommendations.put("selfIntro", profile.getSelfIntro());
            recommendations.put("distance", tools.parseDistance(currentProfile, profile));
            recommendationsList.add(recommendations);
        }
        return recommendationsList;
    }
}
