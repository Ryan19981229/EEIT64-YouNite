package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.younite.entity.MessageEntity;
import tw.com.younite.service.impl.MessageService;
import tw.com.younite.service.inter.IMessageService;
import tw.com.younite.util.JSONResult;

import javax.servlet.http.HttpSession;
import java.util.List;
@Api(tags ="聊天室對話")
@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MessageController extends BaseController {
    @Autowired
    IMessageService iMessageService;
    @ApiOperation("新增訊息")
    @PostMapping("/add")
    public JSONResult<Void> addmessage(@ApiParam(value = "新增聊天訊息", required = true)HttpSession session, @RequestBody MessageEntity messageEntity) {
        System.out.println("add msg");
        iMessageService.insertMessage(messageEntity);
        return new JSONResult<>(OK);
    }
    @ApiOperation("拿聊天室資料")
    @GetMapping("/find/{roomId}")
    public ResponseEntity<List<MessageEntity>> findMessage(@ApiParam(value = "拿到聊天室所有訊息", required = true)HttpSession session,@PathVariable("roomId") Integer roomId){
        List<MessageEntity> list= iMessageService.getMessages(roomId);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/find/unRead/{receive}/{send}")
    public JSONResult<Integer> getUnread(@PathVariable("receive") Integer r ,@PathVariable("send") Integer sd){
        MessageEntity ur =iMessageService.getUnreadNo(r,sd);
        System.out.println("no:"+ur);
        JSONResult<Integer> jsonResult =new JSONResult<>(OK);
        jsonResult.setData(ur.getCount());
        return jsonResult;
    }
}