package tw.com.younite.service.inter;

import org.springframework.web.multipart.MultipartFile;
import tw.com.younite.entity.AmazonFileVO;

public interface AmazonUploadService {
    AmazonFileVO upload(MultipartFile file, String folderName);
}
