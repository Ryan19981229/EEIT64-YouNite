package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@ApiModel(description = "興趣的Entity")
@Data
public class InterestEntity  implements Serializable {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "使用者ID")
    private Integer userID;
    @ApiModelProperty(value = "興趣")
    private String interest;
}
