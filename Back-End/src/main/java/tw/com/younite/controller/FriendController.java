package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.younite.entity.FriendEntity;
import tw.com.younite.service.exception.InvitationNotFoundException;
import tw.com.younite.service.impl.TokenServiceImpl;
import tw.com.younite.service.inter.IFriendService;
import tw.com.younite.util.JSONResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
@Api(tags ="處理好友功能")
@RestController
@CrossOrigin(origins = "*")
public class FriendController extends BaseController {
    private static final int VIDEO_CHAT_INVITATION = 1;
    private static final int VIDEO_CHAT_INVITATION_ACCEPT = 2;
    private static final int VOICE_CHAT_INVITATION = 4;
    private static final int VOICE_CHAT_INVITATION_ACCEPT = 8;
    private static final int IMAGE_INVITATION = 16;
    private static final int IMAGE_INVITATION_ACCEPT = 32;

    @Autowired
    IFriendService iFriendService;

    @Autowired
    TokenServiceImpl token;


    @ApiOperation("設置好友邀請狀態")
    @PutMapping("/users/invitation/{friendID}/{status}")
    public JSONResult<Void> setInvitationStatus(@ApiParam(value = "傳入好友邀請狀態;視訊、語音、圖片", required = true)HttpSession session,
                                                @PathVariable Integer status,
                                                @PathVariable Integer friendID) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        switch (status) {
            case VIDEO_CHAT_INVITATION, VOICE_CHAT_INVITATION, IMAGE_INVITATION -> iFriendService.setInvitationStatus(userID, friendID, status);
            case VIDEO_CHAT_INVITATION_ACCEPT -> iFriendService.setVideoChatFunction(userID, friendID);
            case VOICE_CHAT_INVITATION_ACCEPT -> iFriendService.setVoiceChatFunction(userID, friendID);
            case IMAGE_INVITATION_ACCEPT -> iFriendService.setImageSendFunction(userID, friendID);
            default -> throw new InvitationNotFoundException("");
        }
        return new JSONResult<>(OK);
    }
    @ApiOperation("好友清單")
    @GetMapping("/users/friends")
    public JSONResult<List<Integer>> getFriendList(@ApiParam(value = "傳出好友清單", required = true)HttpSession session) {
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        List<Integer> data = iFriendService.getFriendsList(userID);
        return new JSONResult<>(OK, data);
    }
    @ApiOperation("指定好友清單")
    @GetMapping("/users/friends/{userID}")
    public JSONResult<List<Integer>> getUsersFriendList(@ApiParam(value = "傳出指定好友清單", required = true)
                                                            @PathVariable Integer userID) {
        List<Integer> data = iFriendService.getFriendsList(userID);
        return new JSONResult<>(OK, data);
    }


}
