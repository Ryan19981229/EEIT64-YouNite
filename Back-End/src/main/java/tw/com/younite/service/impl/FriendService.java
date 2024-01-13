package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.FriendEntity;
import tw.com.younite.mapper.FriendMapper;
import tw.com.younite.service.exception.FunctionNotEnabledException;
import tw.com.younite.service.exception.NoMatchedException;
import tw.com.younite.service.inter.IFriendService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService implements IFriendService {
    private static final int VIDEO_CHAT_INVITATION = 1;
    private static final int VIDEO_CHAT_INVITATION_ACCEPT = 2;
    private static final int VOICE_CHAT_INVITATION = 4;
    private static final int VOICE_CHAT_INVITATION_ACCEPT = 8;
    private static final int IMAGE_INVITATION = 16;
    private static final int IMAGE_INVITATION_ACCEPT = 32;

    @Autowired
    FriendMapper friendMapper;

    @Override
    public void insertFriend(FriendEntity friendEntity) {
        friendMapper.insertFriend(friendEntity);
    }

    @Override
    public List<Integer> getFriendsList(Integer userID) {
        List<FriendEntity> friendsList = friendMapper.getFriend(userID);
        List<Integer> result = new ArrayList<>();
        if (friendsList == null) {
            throw new NoMatchedException("");
        }
        for (FriendEntity friend : friendsList) {
            result.add(friend.getSecondUserID());
        }
        return result;
    }

    @Override
    public void setInvitationSent(Integer userID, Integer friendUserID) {
        FriendEntity friend = friendMapper.getSpecificFriend(userID, friendUserID);
        friend.setIsInvitationSent(!friend.getIsInvitationSent());
        friendMapper.setInvitationSent(friend);
    }

    @Override
    public void setInvitationAccept(Integer userID, Integer friendID) {
        for (int i = 0; i < 2; i++) {
            FriendEntity friend = friendMapper.getSpecificFriend(
                    i == 0 ? userID : friendID,
                    i == 0 ? friendID : userID);
            friend.setIsInvitationAccepted(true);
            friendMapper.setInvitationAccept(friend);
        }
    }

    @Override
    public void setInvitationStatus(Integer userID, Integer friendID, Integer status) {
        boolean isMatched = checkMatched(userID, friendID);
        while (isMatched) {
            if (status == VIDEO_CHAT_INVITATION_ACCEPT ||
                    status == VOICE_CHAT_INVITATION_ACCEPT ||
                    status == IMAGE_INVITATION_ACCEPT) {
                for (int i = 0; i < 2; i++) {
                    Integer currentUserID = (i == 0) ? userID : friendID;
                    Integer currentFriendID = (i == 0) ? friendID : userID;
                    FriendEntity friend = friendMapper.getSpecificFriend(currentUserID, currentFriendID);
                    friend.setInvitationStatus(status);
                    friendMapper.setInvitationStatus(friend);
                }
            } else {
                FriendEntity friend = friendMapper.getSpecificFriend(userID, friendID);
                friend.setInvitationStatus(status);
                friendMapper.setInvitationStatus(friend);
            }
            isMatched = false;
        }
    }

    @Override
    public Boolean getInvitationStatus(Integer userID,
                                       Integer friendID,
                                       Integer status) {
        boolean isMatched = checkMatched(userID, friendID);
        while (isMatched) {
            if (status == VIDEO_CHAT_INVITATION ||
                    status == VOICE_CHAT_INVITATION ||
                    status == IMAGE_INVITATION) {
                FriendEntity friend = friendMapper.getInvitationStatus(userID, friendID, status);
                return !(friend == null);
            } else {
                for (int i = 0; i < 2; i++) {
                    Integer currentUserID = (i == 0) ? userID : friendID;
                    Integer currentFriendID = (i == 0) ? friendID : userID;
                    FriendEntity friend = friendMapper.getInvitationStatus(currentUserID, currentFriendID, status);
                    return !(friend == null);
                }
            }
            isMatched = false;
        }
        return true;
    }

    @Override
    public void setVideoChatFunction(Integer userID, Integer friendID) {
        for (int i = 0; i < 2; i++) {
            Integer currentUserID = (i == 0) ? userID : friendID;
            Integer currentFriendID = (i == 0) ? friendID : userID;
            FriendEntity friend = friendMapper.getSpecificFriend(currentUserID, currentFriendID);
            friend.setIsVideoChatEnabled(true);
            friendMapper.setVideoChatStatus(friend);
        }
    }

    @Override
    public void setVoiceChatFunction(Integer userID, Integer friendID) {
        for (int i = 0; i < 2; i++) {
            Integer currentUserID = (i == 0) ? userID : friendID;
            Integer currentFriendID = (i == 0) ? friendID : userID;
            FriendEntity friend = friendMapper.getSpecificFriend(currentUserID, currentFriendID);
            friend.setIsVoiceChatEnabled(true);
            friendMapper.setVoiceChatStatus(friend);
        }
    }

    @Override
    public void setImageSendFunction(Integer userID, Integer friendID) {
        for (int i = 0; i < 2; i++) {
            Integer currentUserID = (i == 0) ? userID : friendID;
            Integer currentFriendID = (i == 0) ? friendID : userID;
            FriendEntity friend = friendMapper.getSpecificFriend(currentUserID, currentFriendID);
            friend.setIsImageEnabled(true);
            friendMapper.setImageStatus(friend);
        }
    }


    public Boolean checkMatched(Integer userID, Integer friendID) {
        FriendEntity friend = friendMapper.getSpecificFriend(userID, friendID);
        if (friend == null) {
            throw new NoMatchedException("");
        }
        return true;
    }


    @Override
    public void VideoSent(Integer userID, Integer friendID) {
        friendMapper.updateVideoSent(userID, friendID);

    }

    @Override
    public void VideoAccepted(Integer userID, Integer friendID) {
        for (int i = 0; i < 2; i++) {
            Integer currentUserID = (i == 0) ? userID : friendID;
            Integer currentFriendID = (i == 0) ? friendID : userID;
            FriendEntity friend = friendMapper.getSpecificFriend(currentUserID, currentFriendID);
            friendMapper.updateVideoAccepted(friend);
        }

    }

    @Override
    public void VoiceSent(Integer userID, Integer friendID) {
        friendMapper.updateVoiceSent(userID, friendID);

    }

    @Override
    public void VoiceAccepted(Integer userID, Integer friendID) {
        for (int i = 0; i < 2; i++) {
            Integer currentUserID = (i == 0) ? userID : friendID;
            Integer currentFriendID = (i == 0) ? friendID : userID;
            FriendEntity friend = friendMapper.getSpecificFriend(currentUserID, currentFriendID);
            friendMapper.updateVoiceAccepted(friend);
        }

    }

    @Override
    public void ImageSent(Integer userID, Integer friendID) {
        friendMapper.updateImageSent(userID, friendID);

    }

    @Override
    public void ImageAccepted(Integer userID, Integer friendID) {
        for (int i = 0; i < 2; i++) {
            Integer currentUserID = (i == 0) ? userID : friendID;
            Integer currentFriendID = (i == 0) ? friendID : userID;
            FriendEntity friend = friendMapper.getSpecificFriend(currentUserID, currentFriendID);
            friendMapper.updateImageAccepted(friend);
        }

    }
}
