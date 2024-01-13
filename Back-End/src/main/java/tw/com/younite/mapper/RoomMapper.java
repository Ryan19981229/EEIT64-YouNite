package tw.com.younite.mapper;

import tw.com.younite.entity.RoomEntity;

import java.util.List;

public interface RoomMapper {
    Integer createRoom(RoomEntity roomEntity);
    List<RoomEntity> getRoom(Integer friendID1, Integer friendID2);
}
