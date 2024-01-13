package tw.com.younite.mapper;

import tw.com.younite.entity.ItemEntity;

import java.util.List;

public interface ItemMapper {



    //從itemmapper返回方法名
    //查詢所有
    List<ItemEntity> selectAll();
    ItemEntity getItemPriceByItemId(Integer itemId);
    String getItemNameByItemId(Integer itemId);



}
