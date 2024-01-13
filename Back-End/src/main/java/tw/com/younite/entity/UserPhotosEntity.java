package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "使用者照片的Entity")
@Data
public class UserPhotosEntity implements Serializable {
    @ApiModelProperty(value = "照片ID")
    private int photoID;
    @ApiModelProperty(value = "個人資料ID")
    private int profileID;
    @ApiModelProperty(value = "第一張照片")
    private String firstPhotoPath;
    @ApiModelProperty(value = "第二章照片")
    private String secondPhotoPath;
    @ApiModelProperty(value = "第三章照片")
    private String thirdPhotoPath;
    @ApiModelProperty(value = "第四章照片")
    private String fourthPhotoPath;
    @ApiModelProperty(value = "第五章照片")
    private String fifthPhotoPath;
    @ApiModelProperty(value = "第六章照片")
    private String sixthPhotoPath;
}
