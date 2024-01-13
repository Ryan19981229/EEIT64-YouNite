package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "個人資料設置的Entity")
@Data
public class BaseEntity implements Serializable {
    @ApiModelProperty(value = "創建資料時間點")
    private Date createdAt;
    @ApiModelProperty(value = "修改資料時間點")
    private Date modifiedAt;
}
