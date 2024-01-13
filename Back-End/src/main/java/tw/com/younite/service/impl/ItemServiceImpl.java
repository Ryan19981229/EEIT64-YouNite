package tw.com.younite.service.impl;

import tw.com.younite.entity.ItemEntity;
import tw.com.younite.mapper.ItemMapper;
import tw.com.younite.service.inter.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;


    @Override
    public List<ItemEntity> getItems() {

        return itemMapper.selectAll();

    }


}
