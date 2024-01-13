package tw.com.younite.service.impl;

import org.springframework.web.bind.annotation.CrossOrigin;
import tw.com.younite.entity.OrdersEntity;
import tw.com.younite.mapper.ItemMapper;
import tw.com.younite.mapper.OrdersMapper;
import tw.com.younite.service.inter.PayService;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ItemMapper itemMapper;




    @Override
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public String ecpayCheckout(Integer id) {


        AllInOne all = new AllInOne("");

        OrdersEntity order = ordersMapper.getById(id);
        String itemName = itemMapper.getItemNameByItemId(order.getItemId());

        AioCheckOutOneTime obj = new AioCheckOutOneTime();
        String mTradeNo = "Y" + System.currentTimeMillis();
        obj.setMerchantTradeNo(mTradeNo);
        obj.setMerchantTradeDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date()));
        obj.setTotalAmount(String.valueOf(order.getAmount()));
        obj.setTradeDesc("Thank you");
        obj.setItemName(itemName);
        obj.setReturnURL("https://9b1e-114-46-180-52.ngrok-free.app/callback");
        // OrderResultURL   : 選填 消費者完成付費後。重新導向的位置

        obj.setOrderResultURL("http://localhost:8080/redirectPost");
        obj.setNeedExtraPaidInfo("N");
        String form = all.aioCheckOut(obj, null);
        System.out.println(form);

        // 注入mTradeNo到订单表
        ordersMapper.addTradeNo(id, mTradeNo);
        // 回傳form訂單 並自動將使用者導到 綠界
        return form;


    }

}
