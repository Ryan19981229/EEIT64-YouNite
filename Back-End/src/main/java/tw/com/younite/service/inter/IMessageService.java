package tw.com.younite.service.inter;

import tw.com.younite.entity.MessageEntity;

import java.util.List;

public interface IMessageService {
    void insertMessage(MessageEntity messageEntity);
    List<MessageEntity> getMessages(Integer roomId);
    MessageEntity getUnreadNo(Integer receiverId,Integer senderId);
}
