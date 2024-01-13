package tw.com.younite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.younite.entity.UserPhotosEntity;
import tw.com.younite.mapper.UserPhotosMapper;
import tw.com.younite.service.exception.InsertPhotosException;
import tw.com.younite.service.inter.IUserPhotosService;

@Service
public class UserPhotosServiceImpl implements IUserPhotosService {
    @Autowired
    UserPhotosMapper userPhotosMapper;
    @Override
    public void insertPhotos(UserPhotosEntity userPhotos) {
        Integer rows = userPhotosMapper.addPhotos(userPhotos);
        if (rows != 1) {
            throw new InsertPhotosException("新增圖片時資料庫發生異常!");
        }
    }
}
