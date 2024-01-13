package tw.com.younite.mapper;

import tw.com.younite.entity.OrdersEntity;

import java.util.Date;

public interface OrdersMapper {

    //從ordersmapper返回方法名

    void insertOrder(OrdersEntity orders);

    void updateUnlocked(String mTradeNo, Boolean unlocked, Date purchased);

    Integer getAmountById (Integer id);

    OrdersEntity getById(Integer id);
    OrdersEntity getByUserId(Integer userId);


    OrdersEntity getByTradeNo (String mTradeNo);

    void addTradeNo(Integer id, String mTradeNo);

    Integer getItemByUser(Integer userId, Integer itemId);

    void updateLocked (Integer userId, Boolean unlocked);

}
