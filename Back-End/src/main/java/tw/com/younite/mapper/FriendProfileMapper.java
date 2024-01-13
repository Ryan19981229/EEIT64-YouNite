package tw.com.younite.mapper;

import tw.com.younite.entity.FriendsProfileEntity;

import java.util.List;

public interface FriendProfileMapper {
    List<FriendsProfileEntity> getFriendProfile(Integer userid);
    List<FriendsProfileEntity> getFriendProfile2(Integer friendID);
}
