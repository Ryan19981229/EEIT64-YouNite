package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import tw.com.younite.entity.AmazonFileVO;
import tw.com.younite.service.impl.AmazonService;
import tw.com.younite.service.impl.AmazonUploadServiceImpl;
import tw.com.younite.service.impl.Amazons3Service;
import tw.com.younite.service.inter.AmazonUploadService;
import tw.com.younite.util.JSONResult;

@Api(tags = "亞馬遜雲端儲存服務")
@RestController
@RequestMapping("/storage")
public class AmazonS3Controller extends BaseController {

    @Autowired
    private AmazonService amazonService;

    @Autowired
    private AmazonUploadService amazonUploadService;

    @Autowired
    private Amazons3Service service;

    @ApiOperation("上傳")
    @PostMapping(value = "/upload")
    public JSONResult<Void> upload(@RequestParam("file") MultipartFile file) {
        AmazonFileVO amazonFileModel = null;
        try {
            amazonFileModel = amazonUploadService.upload(file, "avatar");
            System.out.println("amazonFileModel = " + amazonFileModel.getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONResult(OK);
    }

    @PostMapping(value = "/uploadChat")
    public JSONResult<String> uploadMessage(@RequestParam("file") MultipartFile file) {
        AmazonFileVO amazonFileModel = null;
        try {
            amazonFileModel = amazonUploadService.upload(file, "avatar");
            System.out.println("amazonFileModel = " + amazonFileModel.getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONResult<String> jsonResult = new JSONResult<>();
        jsonResult.setState(OK);
        jsonResult.setData(amazonFileModel.getUrl());
        return jsonResult;
    }
    //這會動喔

    @ApiOperation("上傳")
    @PostMapping("/uploadOK")
    public JSONResult<Void> uploadOK(@RequestParam("file") MultipartFile file) {
        service.uploadFile(file);
        return new JSONResult<>(OK);
    }

    //這會動喔
    @ApiOperation("下載檔案")
    @GetMapping("/download/{fileName}")
    public JSONResult<ByteArrayResource> download(@ApiParam(value = "拿取下載檔案", required = true)
                                                  @PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return new JSONResult<>(OK, resource);
    }

    @ApiOperation("上傳檔案")
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        System.out.println("file = " + file);
        return this.amazonService.uploadFile(file);
    }

    @ApiOperation("刪除檔案")
    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonService.deleteFileFromS3Bucket(fileUrl);
    }

    @ApiOperation("取得檔案內容")
    @GetMapping("/getFileList")
    public JSONResult<List<String>> getFileList() {
        System.out.println("this.amazonService.listFiles() = " + this.amazonService.listFiles());
        return new JSONResult(OK, this.amazonService.listFiles());
    }
}
