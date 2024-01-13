package tw.com.younite.service.impl;

import tw.com.younite.entity.ItemEntity;
import tw.com.younite.entity.OrdersEntity;
import tw.com.younite.entity.UserEntity;
import tw.com.younite.mapper.ItemMapper;
import tw.com.younite.mapper.OrdersMapper;
import tw.com.younite.mapper.UserMapper;
import tw.com.younite.service.inter.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer createOrder(Integer userId, Integer itemId) {
        // 使用 Item Mapper 的方法獲取項目價格
        ItemEntity item = itemMapper.getItemPriceByItemId(itemId);
        Integer amount = item.getPrice().intValue();
        // 創建訂單
        OrdersEntity order = new OrdersEntity();
        order.setUserId(userId);
        order.setItemId(itemId);
        order.setAmount(amount);
        order.setUnlocked(false);
        order.setPurchased(new Date());

        ordersMapper.insertOrder(order);
        return order.getId();

    }


    @Override
    public void updateUnlocked(String mTradeNo, Boolean unlocked, Date purchased){
        ordersMapper.updateUnlocked( mTradeNo, unlocked, purchased);
    }




    @Override
    public Date setVipDate(String mTradeNo, Integer itemId, Date vipDate) {
        OrdersEntity order = ordersMapper.getByTradeNo(mTradeNo);
        Integer item = order.getItemId();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(vipDate);

        if (item == 1) {
            calendar.add(Calendar.MONTH, 1);
        } else if (item == 2) {
            calendar.add(Calendar.MONTH, 3);
        } else if (item == 3) {
            calendar.add(Calendar.YEAR, 1);
        }

        return calendar.getTime();
    }



}
