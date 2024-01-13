package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.younite.entity.RoomEntity;
import tw.com.younite.service.inter.IRoomService;
import tw.com.younite.util.JSONResult;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RoomController extends BaseController{
    @Autowired
    IRoomService iRoomService;

    @PostMapping("/create")
    public JSONResult<Integer> createRoom(HttpSession session, @RequestBody RoomEntity roomEntity){
        iRoomService.createRoom(roomEntity);
        JSONResult<Integer> rs =new JSONResult<>();
        rs.setData(roomEntity.getId());
        rs.setState(OK);
        return rs;
    }

    @GetMapping("/get/{id1}/{id2}")
    public JSONResult<Integer> getRoom(HttpSession session, @PathVariable("id1") Integer id1,@PathVariable("id2") Integer id2){
        JSONResult<Integer> jsonResult =new JSONResult<>();
       RoomEntity roomEntity=iRoomService.getRoom(id1,id2).get(0);
        Integer rs =roomEntity.getId();
        jsonResult.setData(rs);
        jsonResult.setState(OK);
        return jsonResult;

    }
}
