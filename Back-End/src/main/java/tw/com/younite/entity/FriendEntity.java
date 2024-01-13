package tw.com.younite.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@ApiModel(description = "好友功能的Entity")
@Data
public class FriendEntity {
    @ApiModelProperty(value = "使用者ID")
    private Integer id;
    @ApiModelProperty(value = "第一個使用者ID")
    private Integer firstUserID;
    @ApiModelProperty(value = "第二個使用者ID")
    private Integer secondUserID;
    @ApiModelProperty(value = "是否配對成功")
    private Boolean isMatched;
    @ApiModelProperty(value = "是否可以視訊")
    private Boolean isVideoChatEnabled;
    @ApiModelProperty(value = "是否可以語音")
    private Boolean isVoiceChatEnabled;
    @ApiModelProperty(value = "使否可以傳圖片")
    private Boolean isImageEnabled;
    @ApiModelProperty(value = "是否可以發送邀請")
    private Boolean isInvitationSent;
    @ApiModelProperty(value = "是否有無接受好友邀請")
    private Boolean isInvitationAccepted;
    @ApiModelProperty(value = "是否發送視訊邀請")
    private Boolean isVideoSent;
    @ApiModelProperty(value = "是否接受視訊邀請")
    private Boolean isVideoAccepted;
    @ApiModelProperty(value = "是否發送語音邀請")
    private Boolean isVoiceSent;
    @ApiModelProperty(value = "是否接受語音邀請")
    private Boolean isVoiceAccepted;
    @ApiModelProperty(value = "是否發送圖片邀請")
    private Boolean isImageSent;
    @ApiModelProperty(value = "是否接受圖片邀請")
    private Boolean isImageAccepted;
    @ApiModelProperty(value = "邀請狀態")
    private Integer invitationStatus;
    @ApiModelProperty(value = "發生時間")
    private Date createAt;

}
