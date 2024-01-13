package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.MessageEntity;
import tw.com.younite.mapper.MessageMapper;
import tw.com.younite.service.inter.IMessageService;

import java.util.List;
@Service
public class MessageService implements IMessageService {
    @Autowired
    MessageMapper messageMapper;
    @Override
    public void insertMessage(MessageEntity messageEntity) {
        messageMapper.insertMessage(messageEntity);
    }

    @Override
    public List<MessageEntity> getMessages(Integer roomId) {
        return messageMapper.getMessages(roomId);
    }

    @Override
    public MessageEntity getUnreadNo(Integer receiverId, Integer senderId) {
        System.out.println("ser:"+messageMapper.getUnreadNo(receiverId,senderId));
        MessageEntity ms= messageMapper.getUnreadNo(receiverId,senderId);
        return ms;
    }
}
