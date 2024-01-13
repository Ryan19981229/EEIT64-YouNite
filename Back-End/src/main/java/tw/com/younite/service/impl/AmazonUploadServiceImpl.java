package tw.com.younite.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tw.com.younite.entity.AmazonFileVO;
import tw.com.younite.service.inter.AmazonUploadService;
import java.io.IOException;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class AmazonUploadServiceImpl implements AmazonUploadService {
    AmazonS3 s3 = null;

    @Value("${s3.accessKeyId}")
    private String accessKeyId;

    @Value("${s3.accessKeySecret}")
    private String accessKeySecret;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.region}")
    private String regionName;

    @Value("${s3.endpoint}")
    private String endpoint;

    private String FolderName = "avatar";

    public AmazonFileVO upload(MultipartFile file, String folderName) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        int index = originalFileName.lastIndexOf(".");
        String suffix = originalFileName.substring(index);
        String fileName = UUID.randomUUID().toString().toUpperCase() + suffix;
        String contentType = file.getContentType();
        long fileSize = file.getSize();
        String filePath = folderName +"/"+ fileName;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(fileSize);
        try {
            PutObjectResult putObjectResult = s3.putObject(folderName, fileName, file.getInputStream(), objectMetadata);
            //文件权限,设置为公共读
            s3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        AmazonFileVO amazonFileModel = new AmazonFileVO();
        amazonFileModel.setFileName(originalFileName);
        amazonFileModel.setFileSize(fileSize);
        amazonFileModel.setFileType(contentType);
        amazonFileModel.setFilePath(filePath);
        amazonFileModel.setUrl(endpoint + "/" + filePath);
        return amazonFileModel;
    }


    @PostConstruct
    public void init() {

        ClientConfiguration config = new ClientConfiguration();
        AwsClientBuilder.EndpointConfiguration endpointConfig =
                new AwsClientBuilder.EndpointConfiguration(endpoint, regionName);
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

        s3 = AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(config)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
