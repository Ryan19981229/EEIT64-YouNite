package tw.com.younite.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.FriendEntity;
import tw.com.younite.service.inter.IFriendService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FriendServiceTestCase {
    @Autowired
    IFriendService iFriendService;

    @Test
    public void testSetInvitationSent() {
        iFriendService.setInvitationSent(164, 165);
    }

    @Test
    public void testSetInvitationAccept() {
        iFriendService.setInvitationAccept(164, 165);
    }

    @Test
    public void testSetInvitationStatus() {
        iFriendService.setInvitationStatus(164, 165, 8);
    }

    @Test
    public void testGetInvitationStatus() {
        for (int i = 1; i <= 32;) {
            System.out.println("Status: " + i + iFriendService.getInvitationStatus(164, 165, i));
            System.out.println("Status: " + i + iFriendService.getInvitationStatus(165, 164, i));
            i *= 2;
        }
    }

    @Test
    public void testFunctionSetter() {
        iFriendService.setVideoChatFunction(164, 165);
        iFriendService.setImageSendFunction(164, 165);
        iFriendService.setVoiceChatFunction(164, 165);
    }
}
