package tw.com.younite.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.com.younite.entity.ItemEntity;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemMapperTestCase {
    @Autowired
    private ItemMapper itemMapper;
    @Test
    public void testGetByUsername() {
        List<ItemEntity> item = itemMapper.selectAll();
        System.out.println("item = " + item);
    }
}
