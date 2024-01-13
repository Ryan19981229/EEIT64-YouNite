package tw.com.younite.service.inter;
import tw.com.younite.entity.FriendEntity;

import java.util.List;

public interface IFriendService {

    void insertFriend(FriendEntity friendEntity);
    void setInvitationSent(Integer userID, Integer friendID);
    void setInvitationAccept(Integer userID, Integer friendID);
    List<Integer> getFriendsList(Integer userID);
    void setInvitationStatus(Integer userID, Integer friendID, Integer status);
    Boolean getInvitationStatus(Integer userID, Integer friendID, Integer status);
    void setVideoChatFunction(Integer userID, Integer friendID);
    void setVoiceChatFunction(Integer userID, Integer friendID);
    void setImageSendFunction(Integer userID, Integer friendID);

    void VideoSent(Integer userID, Integer friendID);
    void VideoAccepted(Integer userID, Integer friendID);
    void VoiceSent(Integer userID, Integer friendID);
    void VoiceAccepted(Integer userID, Integer friendID);
    void ImageSent(Integer userID, Integer friendID);
    void ImageAccepted(Integer userID, Integer friendID);

}
