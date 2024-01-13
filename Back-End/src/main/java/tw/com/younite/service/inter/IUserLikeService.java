package tw.com.younite.service.inter;

import org.apache.catalina.User;
import tw.com.younite.entity.UserLikeEntity;
import java.util.List;

public interface IUserLikeService {
    void insertLikedUser(UserLikeEntity userLikeEntity);
    List<Integer> getLikedUserList(Integer userID);
    List<Integer> likesTracker(Integer likedUserID);
    void deleteLikedUser(Integer userID, Integer likedUserID);
}
