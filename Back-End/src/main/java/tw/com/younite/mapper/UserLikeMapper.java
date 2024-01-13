package tw.com.younite.mapper;

import tw.com.younite.entity.UserLikeEntity;

import java.util.List;

public interface UserLikeMapper {
    Integer insertLike(UserLikeEntity userLikeEntity);
    List<UserLikeEntity> getLikedUsers(Integer userId);
    Integer removeLikedUsers(Integer userID, Integer likedUserID);
    List<UserLikeEntity> likesTracker(Integer likedUserID);
}
