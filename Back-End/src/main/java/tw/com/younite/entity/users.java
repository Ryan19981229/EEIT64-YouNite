package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Google登入的Entity")
public class users {

    @ApiModelProperty(value = "使用者ID")
    private Integer id;
    @ApiModelProperty(value = "GOOLE登入傳入Email")
    private String email;
    @ApiModelProperty(value = "GOOLE登入傳入username")
    private String username;
    @ApiModelProperty(value = "GOOLE登入傳入ThirdPartyLogin")
    private boolean ThirdPartyLogin;
}
