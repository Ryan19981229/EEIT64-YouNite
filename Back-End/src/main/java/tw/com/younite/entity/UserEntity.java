package tw.com.younite.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
@ApiModel(description = "登入註冊的Entity")
@Data
public class UserEntity extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "使用者登入註冊id")
    private Integer id;
    @ApiModelProperty(value = "使用者登入註冊username")
    private String username;
    @ApiModelProperty(value = "使用者登入註冊salt")
    private String salt;
    @ApiModelProperty(value = "使用者登入註冊email")
    private String email;
    @ApiModelProperty(value = "使用者登入註冊password")
    private String password;
    @ApiModelProperty(value = "使用者登入註冊thirdPartyLogin")
    private Boolean thirdPartyLogin;
    @ApiModelProperty(value = "使用者登入註冊googleUser")
    private String googleUser;
    @ApiModelProperty(value = "使用者登入註冊facebookUser")
    private String facebookUser;
    @ApiModelProperty(value = "使用者vip時間")
    private Date vipExpiry;
    private Boolean unlocked;
    private Timestamp logTime;
    @ApiModelProperty(value = "重置密碼驗證碼")
    private String resetToken;

}
