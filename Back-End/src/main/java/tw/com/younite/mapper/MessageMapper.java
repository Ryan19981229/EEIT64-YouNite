package tw.com.younite.mapper;

import tw.com.younite.entity.FriendEntity;
import tw.com.younite.entity.MessageEntity;

import java.util.List;

public interface MessageMapper {
    Integer insertMessage(MessageEntity messageEntity);
    List<MessageEntity> getMessages(Integer roomId);
    MessageEntity getUnreadNo(Integer receiverId,Integer senderId);
}
