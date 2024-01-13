package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "朋友聊天室資料")
@Data
public class FriendsProfileEntity {
    @ApiModelProperty(value = "朋友ID")
    private Integer userid;
    @ApiModelProperty(value = "朋友名稱")
    private String fullName;
    @ApiModelProperty(value = "朋友聊天室最後訊息")
    private String lastMessage;
    @ApiModelProperty(value = "朋友頭貼")
    private String profileAvatar;
    @ApiModelProperty(value = "朋友未讀訊息")
    private Integer unread;
}
