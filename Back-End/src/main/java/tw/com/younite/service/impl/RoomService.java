package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.RoomEntity;
import tw.com.younite.mapper.RoomMapper;
import tw.com.younite.service.inter.IRoomService;

import java.util.List;

@Service
public class RoomService implements IRoomService {
@Autowired
    RoomMapper roomMapper;
    @Override
    public void createRoom(RoomEntity roomEntity) {
       Integer id1= roomEntity.getFriendID1();
       Integer id2= roomEntity.getFriendID2();
        if(id1>id2){
            roomEntity.setFriendID1(id2);
            roomEntity.setFriendID2(id1);
        }
        roomMapper.createRoom(roomEntity);
    }

    @Override
    public List<RoomEntity> getRoom(Integer friendID1, Integer friendID2) {
        if(friendID1>friendID2){
            Integer temp =friendID1;
            friendID1=friendID2;
            friendID2=temp;
        }
        return   roomMapper.getRoom(friendID1,friendID2);
    }
}
