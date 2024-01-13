package tw.com.younite.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "按愛心喜歡的Entity")
@Data
public class UserLikeEntity implements Serializable {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "喜歡對方使用者ID")
    private Integer likedUserID;
    @ApiModelProperty(value = "使用者ID")
    private Integer userID;
    @ApiModelProperty(value = "喜歡或點讚的時間")
    private Date likedAt;
}
