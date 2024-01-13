package tw.com.younite.service.inter;

import tw.com.younite.entity.FriendMsgTempEntity;
import tw.com.younite.entity.RoomEntity;

import java.util.List;

public interface IFriendTempService {
    void setLastMsg(FriendMsgTempEntity msgTempEntity);
    List<FriendMsgTempEntity> getRoom(Integer friendID1, Integer friendID2);
    void createRoom(FriendMsgTempEntity msgTempEntity);

}
