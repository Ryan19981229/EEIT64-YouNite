package tw.com.younite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import tw.com.younite.entity.ItemEntity;
import tw.com.younite.service.inter.ItemService;
import tw.com.younite.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static tw.com.younite.controller.BaseController.OK;
@Api(tags ="顯示商品")
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ItemController {

        @Autowired
        private ItemService itemService;

        //取得所有商品列表
    @ApiOperation("回傳商品列表")
    @GetMapping("/items")
    public JSONResult<List<ItemEntity>> getAllItems(){
        List<ItemEntity> itemList = itemService.getItems(); //list 會回傳商品列表回來

        return new JSONResult<List<ItemEntity>>(OK, itemList);
    }



}


//        @GetMapping("/items")
//        public ResponseEntity<List<Item>> getItems(){
//            List<Item> itemList = itemService.getItems(); //list 會回傳商品列表回來
//
//            return ResponseEntity.status(HttpStatus.OK).body(itemList);
//        }
