package tw.com.younite.entity;

import lombok.Data;

@Data
public class FriendMsgTempEntity {
    private Integer id;
    private Integer userID;
    private Integer friendID;
    private String lastMessage;
    private Integer unread;
}
