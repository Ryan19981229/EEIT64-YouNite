package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import tw.com.younite.entity.AmazonFileVO;
import tw.com.younite.entity.UserPhotosEntity;
import tw.com.younite.entity.UserProfileEntity;
import tw.com.younite.service.exception.*;
import tw.com.younite.service.impl.TokenServiceImpl;
import tw.com.younite.service.inter.AmazonUploadService;
import tw.com.younite.service.inter.IInterestService;
import tw.com.younite.service.inter.IUserPhotosService;
import tw.com.younite.service.inter.IUserProfileService;
import tw.com.younite.util.DateUtil;
import tw.com.younite.util.FileUploadUtil;
import tw.com.younite.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.com.younite.service.uploadException.FileEmptyException;
import tw.com.younite.service.uploadException.FileSizeException;
import tw.com.younite.service.uploadException.FileTypeException;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "個人資料建立")
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserProfileController extends BaseController {
    @Autowired
    private IUserProfileService iUserProfileService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private IUserPhotosService iUserPhotosService;

    @Autowired
    private AmazonUploadService amazonUploadService;

    @Autowired
    private IInterestService interestService;

    @Autowired
    private TokenServiceImpl token;

    /**
     *
     * @param userProfile
     * @param session
     * @param avatar MultipartFile是Spring MVC提供的一個接口，可以接受各文件類型的數據
     * @return
     */

    /**
     * 限制上傳大頭貼最大值
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    public static final int VOICE_INTRO_MAX_SIZE = 5 * 1024 * 1024;

    /**
     * 限制上傳的文件類型
     */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    public static final List<String> VOICE_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/svg");
        AVATAR_TYPE.add("image/webp");
    }

    static {
        VOICE_TYPE.add("audio/mpeg");
        VOICE_TYPE.add("audio/wav");
        VOICE_TYPE.add("audio/aac");
    }

    private static final String PREFIX = "https://younite-avatar-bucket.s3.ap-northeast-1.amazonaws.com/";

    @ApiOperation("接受使用者的個人檔案相關資訊")
    @PostMapping(value = "/users/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JSONResult<Void> insertProfile(@ApiParam(value = "使用者個人資料新增", required = true) @ModelAttribute UserProfileEntity userProfile,
                                          HttpSession session,
                                          String birthday,
                                          @RequestParam("hobbies") List<String> hobbies,
                                          @RequestParam(required = false) MultipartFile voice,
                                          MultipartFile avatar,
                                          @RequestParam(required = false) MultipartFile[] photos) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        if (iUserProfileService.getUserProfile(userID) == null) {
            userProfile.setUserId(userID);
        } else {
            throw new DuplicatedUserProfileException("個人檔案重複");
        }

        if (voice != null) {
            validateVoice(voice);
            userProfile.setVoiceIntro(fileUploadUtil.uploadFile(voice));
        }

        validateAvatar(avatar);
        uploadFile(avatar, userProfile, "avatar");

        //格式化生日
        Date date = dateUtil.parseDate(birthday);
        userProfile.setBirthday(date);

        //設置興趣
        interestService.setInterests(userID, hobbies);

        iUserProfileService.insertProfile(userProfile);
        handleUserPhotos(userID, photos);

        //返回使用者的大頭貼路徑給前端，未來可以展示用
        return new JSONResult<>(CREATE_OK);

    }

    @ApiOperation("封鎖使用者黑名單")
    @PostMapping("/users/blockUser")
    public JSONResult<Void> blockUser(@ApiParam(value = "要被封鎖的使用者ID匯入", required = true) HttpSession session, Integer blockedUserID) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        if (iUserProfileService.getBlockedID(userID).contains(blockedUserID)) {
            throw new BlockedIDAlreadyExistsException("此用戶已在黑名單內");
        }
        iUserProfileService.blockUser(userID, blockedUserID);
        return new JSONResult<>(CREATE_OK, "已成功添加黑名單");
    }

    @ApiOperation("取消封鎖使用者黑名單")
    @PostMapping("/users/unblockUser")
    public JSONResult<Void> unblockUser(@ApiParam(value = "要取消封鎖在黑名單內的使用者ID匯入", required = true) HttpSession session, Integer unblockedUserID) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        if (!iUserProfileService.getBlockedID(userID).contains(unblockedUserID)) {
            throw new BlockedUserNotFoundException();
        }
        iUserProfileService.unblockUser(userID, unblockedUserID);
        return new JSONResult<>(CREATE_OK, "已將用戶自黑名單中移除!");
    }

    @ApiOperation("查詢黑名單")
    @GetMapping("/users/getBlokedID")
    public JSONResult<List<Integer>> getBlockedList(@ApiParam(value = "查詢黑名單的資料回傳", required = true) HttpSession session) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        List<Integer> data = iUserProfileService.getBlockedID(userID);
        return new JSONResult<>(OK, data);
    }

    @ApiOperation("查詢個人資料")
    @GetMapping("/users/profile")
    public JSONResult<UserProfileEntity> getUserProfile(@ApiParam(value = "查詢個人資料回傳", required = true) HttpSession session) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        UserProfileEntity data = iUserProfileService.getUserProfile(userID);
        return new JSONResult<>(OK,  data);
    }

    @ApiOperation("查詢指定的個人資料")
    @GetMapping("/users/profile/{userID}")
    public JSONResult<UserProfileEntity> getUserProfile(@ApiParam(value = "查詢指定個人資料回傳", required = true)
                                                        @PathVariable Integer userID) {
        UserProfileEntity data = iUserProfileService.getUserProfile(userID);
        return new JSONResult<>(OK, data);
    }

    @ApiOperation("獲得相同職業的使用者名單")
    @GetMapping("/users/profiles/profession")
    public JSONResult<List<Map<String, Object>>> getProfilesByProfession(@ApiParam(value = "接收ID才取得相同興趣的使用者", required = true)
                                                                         HttpSession session) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        List<Map<String, Object>> data = iUserProfileService.getProfilesByProfession(userID);
        return new JSONResult<>(OK,"透過職業取得其他用戶資料成功", data);
    }

    @ApiOperation("修改個人資料與更新")
    @PutMapping("/users/profile")
    public JSONResult<Void> resetProfiles(@ApiParam(value = "接收個人資料修改與更新資料", required = true)
                                          @ModelAttribute UserProfileEntity userProfile,
                                          HttpSession session,
                                          String birthday,
                                          @RequestParam(value = "hobbies", required = false) List<String> hobbies,
                                          @RequestParam(value = "voice", required = false) MultipartFile voice,
                                          @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                          @RequestParam(value = "photos", required = false) MultipartFile[] photos) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        UserProfileEntity originalProfile = iUserProfileService.getUserProfile(userID);
        if (originalProfile == null) {
            throw new ProfileNotFoundException("用戶資料不存在，請先創建!");
        }
        String originalAvatar = originalProfile.getProfileAvatar();
        String originalVoice = originalProfile.getVoiceIntro();
        //新用戶
        userProfile.setUserId(userID);
        Date date = dateUtil.parseDate(birthday);
        userProfile.setBirthday(date);
        if (avatar != null) {
            uploadFile(avatar, userProfile, "avatar");
        } else {
            userProfile.setProfileAvatar(originalAvatar);
        }

        if (voice != null) {
            uploadFile(voice, userProfile, "voice");
        } else {
            userProfile.setVoiceIntro(originalVoice);
        }


        if (photos != null) {
            handleUserPhotos(userID, photos);
        }

        iUserProfileService.resetUserProfile(userProfile);
        if (hobbies != null) {
            interestService.removeInterests(userID);
            interestService.setInterests(userID, hobbies);
        }


        return new JSONResult<Void>(NO_CONTENT_OK,"個人資料更新成功");
    }



    private void validateAvatar(MultipartFile avatar) {
        if (avatar.isEmpty()) {
            throw new FileEmptyException("大頭照不能為空");
        }

        if (avatar.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("大頭貼大小必須小於等於10Mb!");
        }

        if (!AVATAR_TYPE.contains(avatar.getContentType())) {
            throw new FileTypeException("大頭照上傳類型錯誤，必須為jpeg/png/gif/bpm/svg/webp");
        }
    }

    private void validateVoice(MultipartFile voice) {
        if (voice.getSize() > VOICE_INTRO_MAX_SIZE) {
            throw new FileSizeException("音檔大小必須小於5Mb");
        }

        if (!VOICE_TYPE.contains(voice.getContentType())) {
            throw new FileTypeException("音檔上傳類型錯誤，必須為mp3/wav/aac");
        }
    }

    private void handleUserPhotos(Integer userID, MultipartFile[] photos) {
        Integer profileID = iUserProfileService.getUserProfile(userID)
                .getProfileId();
        UserPhotosEntity userPhotos = new UserPhotosEntity();
        userPhotos.setProfileID(profileID);
        if (photos != null) {
            for (int i = 0; i < photos.length; i++) {
                MultipartFile photo = photos[i];
                String photoPath = fileUploadUtil.uploadFile(photo);
                setUserPhotoPath(userPhotos, i, photoPath);
            }
        }
        iUserPhotosService.insertPhotos(userPhotos);
    }

    private void setUserPhotoPath(UserPhotosEntity userPhotos, int index,
                                  String photoPath) {
        switch (index) {
            case 0 -> userPhotos.setFirstPhotoPath(photoPath);
            case 1 -> userPhotos.setSecondPhotoPath(photoPath);
            case 2 -> userPhotos.setThirdPhotoPath(photoPath);
            case 3 -> userPhotos.setFourthPhotoPath(photoPath);
            case 4 -> userPhotos.setFifthPhotoPath(photoPath);
            case 5 -> userPhotos.setSixthPhotoPath(photoPath);
            default -> {
            }
        }
    }


    private void uploadFile(MultipartFile file, UserProfileEntity userProfile, String folderName) {
        AmazonFileVO amazonFileModel = null;
        String filePath = "";

        try {
            amazonFileModel = amazonUploadService.upload(file, folderName);
            filePath = PREFIX + amazonFileModel.getFilePath();
            if (AVATAR_TYPE.contains(file.getContentType())) {
                userProfile.setProfileAvatar(filePath);
            } else if (VOICE_TYPE.contains(file.getContentType())) {
                userProfile.setVoiceIntro(filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}