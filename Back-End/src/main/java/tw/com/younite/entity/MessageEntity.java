package tw.com.younite.entity;

import lombok.Data;

import java.util.Date;
@Data
public class MessageEntity {
    private Integer messageId;
    private Integer senderId;
    private Integer receiverId;
    private String messageType;
    private Integer roomId;
    private Date timestamp;
    private String messageContent;
    private Integer count;
}
