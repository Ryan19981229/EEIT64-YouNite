package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "商品內容的Entity")
@Data
//Data 涵蓋 getter setter toString
public class ItemEntity {
    @ApiModelProperty(value = "商品id")
    private Integer id;
    @ApiModelProperty(value = "商品名稱")
    private String itemName;
    @ApiModelProperty(value = "商品價格")
    private Integer price;

}

//    @Override
//    public String toString() {
//        return "Item{" +
//                "id=" + id +
//                ", itemName='" + itemName + '\'' +
//                ", price=" + price +
//                ", valid=" + valid +
//                '}';
//    }
//}
