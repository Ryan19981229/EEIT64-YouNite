package tw.com.younite.mapper;

import org.apache.ibatis.annotations.Mapper;
import tw.com.younite.entity.UserPhotosEntity;



public interface UserPhotosMapper {
    Integer addPhotos(UserPhotosEntity userPhotos);
}
