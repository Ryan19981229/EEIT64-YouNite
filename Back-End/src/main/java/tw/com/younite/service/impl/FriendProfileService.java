package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.FriendsProfileEntity;
import tw.com.younite.mapper.FriendProfileMapper;
import tw.com.younite.service.inter.IFriendProfileService;

import java.util.List;
@Service
public class FriendProfileService implements IFriendProfileService {
    @Autowired
    FriendProfileMapper friendProfileMapper;
    @Override
    public List<FriendsProfileEntity> getFriendProfile(Integer userid) {
        return  friendProfileMapper.getFriendProfile(userid);
    }

    @Override
    public List<FriendsProfileEntity> getFriendProfile2(Integer friendID) {
        return friendProfileMapper.getFriendProfile2(friendID);
    }


}
