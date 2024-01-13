package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.UserLikeEntity;
import tw.com.younite.mapper.UserLikeMapper;
import tw.com.younite.service.exception.LikedUserNotFoundException;
import tw.com.younite.service.inter.IUserLikeService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLikeServiceImpl implements IUserLikeService {

    @Autowired
    UserLikeMapper userLikeMapper;

    @Override
    public void insertLikedUser(UserLikeEntity userLikeEntity) {
        Integer rows = userLikeMapper.insertLike(userLikeEntity);
        if (rows != 1) {
            throw new LikedUserNotFoundException("");
        }
    }

    @Override
    public List<Integer> getLikedUserList(Integer userID) {
        List<UserLikeEntity> likedUserList = userLikeMapper.getLikedUsers(userID);
        List<Integer> dataList = new ArrayList<>();
        for (UserLikeEntity userLikeEntity : likedUserList) {
            dataList.add(userLikeEntity.getLikedUserID());
        }
        return dataList;
    }

    @Override
    public List<Integer> likesTracker(Integer likedUserID) {
        List<UserLikeEntity> likedUserList = userLikeMapper.likesTracker(likedUserID);
        List<Integer> dataList = new ArrayList<>();
        for (UserLikeEntity userLikeEntity : likedUserList) {
            dataList.add(userLikeEntity.getUserID());
        }
        return dataList;
    }

    @Override
    public void deleteLikedUser(Integer userID, Integer likedUserID) {
        List<UserLikeEntity> list = userLikeMapper.getLikedUsers(userID);
        boolean isFound = false;
        for (UserLikeEntity userLikeEntity : list) {
            if (userLikeEntity.getLikedUserID().equals(likedUserID)) {
                userLikeMapper.removeLikedUsers(userID, likedUserID);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            throw new LikedUserNotFoundException("喜歡的用戶不存在，無法刪除");
        }
    }

}
