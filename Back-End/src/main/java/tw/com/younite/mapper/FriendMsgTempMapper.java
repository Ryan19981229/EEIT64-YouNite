package tw.com.younite.mapper;

import tw.com.younite.entity.FriendMsgTempEntity;
import tw.com.younite.entity.FriendsProfileEntity;
import tw.com.younite.entity.RoomEntity;

import java.util.List;

public interface FriendMsgTempMapper {
    void setLastMsg(String lastMessage,Integer userID,Integer friendID);
    List<FriendMsgTempEntity> getRoom(Integer userID, Integer friendID);
    void createRoom(Integer userID,Integer friendID,String lastMessage);

}
