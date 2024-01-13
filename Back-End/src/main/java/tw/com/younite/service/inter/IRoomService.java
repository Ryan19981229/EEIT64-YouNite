package tw.com.younite.service.inter;

import tw.com.younite.entity.RoomEntity;

import java.util.List;

public interface IRoomService {
    void createRoom(RoomEntity roomEntity);
    List<RoomEntity> getRoom(Integer friendID1, Integer friendID2);
}
