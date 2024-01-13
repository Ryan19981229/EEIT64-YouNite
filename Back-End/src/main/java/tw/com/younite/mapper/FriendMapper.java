package tw.com.younite.mapper;
import tw.com.younite.entity.FriendEntity;


import java.util.List;

public interface FriendMapper {
    Integer insertFriend(FriendEntity friendEntity);
    Integer setInvitationSent(FriendEntity friendEntity);
    Integer setInvitationAccept(FriendEntity friendEntity);
    Integer setInvitationStatus(FriendEntity friendEntity);
    Integer setVideoChatStatus(FriendEntity friendEntity);
    Integer setVoiceChatStatus(FriendEntity friendEntity);
    Integer setImageStatus(FriendEntity friendEntity);
    List<FriendEntity> getFriend(Integer id);
    FriendEntity getSpecificFriend(Integer firstUserID, Integer secondUserID);
    FriendEntity getInvitationStatus(Integer firstUserID,
                                     Integer secondUserID,
                                     Integer invitationStatus);
    void updateVideoSent(Integer firstUserID, Integer secondUserID);
    void updateVideoAccepted(FriendEntity friendEntity);
    void updateVoiceSent(Integer firstUserID, Integer secondUserID);
    void updateVoiceAccepted(FriendEntity friendEntity);
    void updateImageSent(Integer firstUserID, Integer secondUserID);
    void updateImageAccepted(FriendEntity friendEntity);

}
