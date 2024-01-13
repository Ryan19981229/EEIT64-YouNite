package tw.com.younite.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ApiModel(description = "亞馬遜雲端儲存服務的Entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmazonFileVO {
    private long fileSize;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件URL
     */
    private String url;

    /**
     * 云存储中的路径
     */
    private String filePath;

    /**
     *  文件类型
     */
    private String fileType;
}
