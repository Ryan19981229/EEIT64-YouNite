package tw.com.younite.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.FriendEntity;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FriendMapperTestCase {
    @Autowired
    FriendMapper friendMapper;

    @Test
    public void testInsertFriend() {
        FriendEntity friend = new FriendEntity();
        friend.setFirstUserID(127);
        friend.setSecondUserID(126);
        friend.setCreateAt(new Date());
        friend.setIsMatched(true);
        friendMapper.insertFriend(friend);
        System.out.println("friend = " + friend);
    }

    @Test
    public void testGetFriend() {
        List<FriendEntity> friend = friendMapper.getFriend(162);
        System.out.println("friend = " + friend);
    }

    @Test
    public void testSetInvitationSent() {
        FriendEntity friend = friendMapper.getSpecificFriend(164, 165);
        friend.setIsInvitationSent(true);
        friendMapper.setInvitationSent(friend);
    }
    @Test
    public void testSetInvitationAccept() {
        FriendEntity friend = friendMapper.getSpecificFriend(164, 165);
        friend.setIsInvitationAccepted(true);
        friendMapper.setInvitationAccept(friend);
    }

    @Test
    public void testSetVoiceInvitationStatus() {
        FriendEntity friend = friendMapper.getSpecificFriend(164, 165);
        //發送視訊聊天邀請
        friend.setInvitationStatus(1);
        friendMapper.setInvitationStatus(friend);
    }

    @Test
    public void testSetVoiceInvitationAcceptStatus() {
        FriendEntity friend = friendMapper.getSpecificFriend(164, 165);
        //對方接受視訊聊天邀請
        friend.setInvitationStatus(2);
        friendMapper.setInvitationStatus(friend);
    }

    @Test
    public void testGetVoiceInvitationStatus() {
        FriendEntity friend = friendMapper.getInvitationStatus(164, 165, 1);
        FriendEntity friend2 = friendMapper.getInvitationStatus(164, 165, 2);
        //Expect null
        FriendEntity friend3 = friendMapper.getInvitationStatus(164, 165, 4);
        //查看視訊聊天邀請是否被成功設置
        System.out.println("friend = " + friend);
        System.out.println("friend2 = " + friend2);
        System.out.println("friend3 = " + friend3);
    }

    @Test
    public void testSetVoiceEnabledStatus() {
        FriendEntity friend = friendMapper.getInvitationStatus(164, 165, 2);
        FriendEntity nullObject = friendMapper.getInvitationStatus(164, 165, 8);
        if (friend != null) {
            friend.setIsVideoChatEnabled(true);
            friendMapper.setVideoChatStatus(friend);
        }

        if (nullObject == null) {
            System.out.println("語音聊天功能尚未啟動");
        }
        //查看視訊聊天功能是否成功啟動
        System.out.println("friend = " + friend);
    }

}
