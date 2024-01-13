package tw.com.younite.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.ItemEntity;

import tw.com.younite.mapper.OrdersMapper;
import tw.com.younite.mapper.UserMapper;
import tw.com.younite.service.inter.OrdersService;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemServiceTestCase {

    @Autowired
    private ItemService itemService;




    @Test
    public void test() {

        List<ItemEntity> a = itemService.getItems();

        System.out.println("item = " + a);

    }



}
