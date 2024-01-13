package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.younite.entity.FriendMsgTempEntity;
import tw.com.younite.entity.FriendsProfileEntity;
import tw.com.younite.service.impl.TokenServiceImpl;
import tw.com.younite.service.inter.IFriendProfileService;
import tw.com.younite.service.inter.IFriendTempService;
import tw.com.younite.util.JSONResult;

import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags ="朋友聊天室")
@RestController
@RequestMapping("/getProfile")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FriendProfileController extends BaseController{
    @Autowired
    IFriendProfileService ifriendProfileService;
    @Autowired
    IFriendTempService iFriendTempService;
    @Autowired
    TokenServiceImpl token;

    @ApiOperation("好友列表拿取")
    @GetMapping("/friendList")
    public ResponseEntity<List<FriendsProfileEntity>> getProfiles(@ApiParam(value = "拿到好友列表", required = true)HttpSession session){
        String account = token.getAccount();
        Integer userID = token.getIdFromAccountString(account);
        List<FriendsProfileEntity> data= ifriendProfileService.getFriendProfile(userID);
        List<FriendsProfileEntity> data2= ifriendProfileService.getFriendProfile2(userID);
        data.addAll(data2);

        return ResponseEntity.ok(data);
    }
    @ApiOperation("最後訊息")
    @PutMapping("/setLast")
    public JSONResult<Void> setLast(@ApiParam(value = "朋友聊天室設置最後訊息", required = true)
                                        @RequestBody FriendMsgTempEntity msgTempEntity){
        iFriendTempService.setLastMsg(msgTempEntity);
        return new JSONResult<>(OK);
    }
    @ApiOperation("拿聊天室")
    @GetMapping("/getRoom/{id}/{id2}")
    public JSONResult<Integer> getRoom(@ApiParam(value = "拿回聊天室房間ID", required = true)HttpSession session,
                                       @PathVariable("id") Integer id, @PathVariable("id2") Integer id2){
         FriendMsgTempEntity friendMsgTempEntity=iFriendTempService.getRoom(id,id2).get(0);
         JSONResult<Integer> jsonResult=new JSONResult<>();
         jsonResult.setData(friendMsgTempEntity.getId());
         jsonResult.setState(OK);
         return jsonResult;
    }
    @ApiOperation("建立房間")
    @PostMapping("/createRoom")
    public JSONResult<Integer> createRoom(@ApiParam(value = "創建房間", required = true)
                                              @RequestBody FriendMsgTempEntity msgTempEntity){
        iFriendTempService.createRoom(msgTempEntity);
        JSONResult<Integer> jsonResult =new JSONResult<>();
        jsonResult.setState(OK);
        return jsonResult;
    }
}
