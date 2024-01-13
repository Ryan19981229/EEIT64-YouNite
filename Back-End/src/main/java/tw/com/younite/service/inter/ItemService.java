package tw.com.younite.service.inter;

import tw.com.younite.entity.ItemEntity;

import java.util.Date;
import java.util.List;


public interface ItemService {

    List<ItemEntity> getItems();

//    Date setItemDate(Integer itemId , Date vipDate);
}
