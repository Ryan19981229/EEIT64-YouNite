package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "使用者資料的Entity")
@Data
public class UserProfileEntity extends UserEntity implements Serializable {
    @ApiModelProperty(value = "使用者資料ID")
    private Integer profileId;
    @ApiModelProperty(value = "使用者ID")
    private Integer userId;
    @ApiModelProperty(value = "使用者全名")
    private String fullName;
    @ApiModelProperty(value = "使用者性別")
    private String gender;
    @ApiModelProperty(value = "使用者性取向")
    private String sexualOrientation;
    @ApiModelProperty(value = "使用者性別偏好")
    private String preferredGender;
    @ApiModelProperty(value = "使用者約會目標")
    private String datingGoal;
    @ApiModelProperty(value = "使用者自我介紹")
    private String selfIntro;
    @ApiModelProperty(value = "使用者地點位置")
    private String location;
    @ApiModelProperty(value = "使用者所在城市")
    private String city;
    @ApiModelProperty(value = "使用者個人頭像")
    private String profileAvatar;
    @ApiModelProperty(value = "使用者語音介紹")
    private String voiceIntro;
    @ApiModelProperty(value = "使用者職業")
    private String professions;
    @ApiModelProperty(value = "使用者電話號碼")
    private String phone;
    @ApiModelProperty(value = "封鎖使用者ID")
    private String blockId;
    @ApiModelProperty(value = "照片順序")
    private Integer index;
    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
}
