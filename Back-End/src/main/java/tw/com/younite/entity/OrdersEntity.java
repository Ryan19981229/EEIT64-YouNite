package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ApiModel(description = "訂單的Entity")
@Data
//Data 涵蓋 getter setter toString
public class OrdersEntity {
    @ApiModelProperty(value = "訂單的id")
    private Integer id;
    @ApiModelProperty(value = "使用者id")
    private Integer userId;
    @ApiModelProperty(value = "物品id")
    private Integer itemId;
    @ApiModelProperty(value = "價錢總和")
    private Integer amount;
    @ApiModelProperty(value = "解鎖VIP")
    private Boolean unlocked;
    @ApiModelProperty(value = "購買日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchased;
    @ApiModelProperty(value = "綠界交易編號")
    private String mTradeNo;



}

