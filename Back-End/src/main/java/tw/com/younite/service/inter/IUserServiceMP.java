package tw.com.younite.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.com.younite.entity.users;

public interface IUserServiceMP extends IService<users> {
    users loginByGoogle(String token);
}
