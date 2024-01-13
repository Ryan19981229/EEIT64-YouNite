package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.FriendMsgTempEntity;
import tw.com.younite.mapper.FriendMsgTempMapper;
import tw.com.younite.service.inter.IFriendTempService;

import java.util.List;

@Service
public class FriendTempService implements IFriendTempService {
    @Autowired
    FriendMsgTempMapper friendMsgTempMapper;
    @Override
    public void setLastMsg(FriendMsgTempEntity msgTempEntity) {
        Integer ud= msgTempEntity.getUserID();
        System.out.println(ud);
        Integer fd= msgTempEntity.getFriendID();
        String msg= msgTempEntity.getLastMessage();
        friendMsgTempMapper.setLastMsg(msg,ud,fd);
    }

    @Override
    public List<FriendMsgTempEntity> getRoom(Integer friendID1, Integer friendID2) {
        if(friendID1>friendID2){
            Integer temp =friendID1;
            friendID1=friendID2;
            friendID2=temp;
        }

        return friendMsgTempMapper.getRoom(friendID1,friendID2);
    }

    @Override
    public void createRoom(FriendMsgTempEntity msgTempEntity) {
       Integer ui= msgTempEntity.getUserID();
       Integer fi= msgTempEntity.getFriendID();
       String lm= msgTempEntity.getLastMessage();
        if(ui>fi){
           Integer temp =ui;
           ui=fi;
           fi=temp;
       }
        friendMsgTempMapper.createRoom(ui,fi,lm);
    }
}
