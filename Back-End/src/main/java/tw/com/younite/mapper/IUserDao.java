package tw.com.younite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tw.com.younite.entity.users;

@Mapper
public interface IUserDao extends BaseMapper<users> {


}
