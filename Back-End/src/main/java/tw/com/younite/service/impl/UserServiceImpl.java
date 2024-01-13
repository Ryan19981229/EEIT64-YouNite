package tw.com.younite.service.impl;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tw.com.younite.entity.UserEntity;
import tw.com.younite.mapper.UserMapper;
import tw.com.younite.service.exception.*;
import tw.com.younite.service.inter.IUserService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 將UserService 交給spring管理，可自動創建component，沒加會報錯
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    //加密方式
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void reg(UserEntity user) {
        String username = user.getUsername();
        String email = user.getEmail();
        UserEntity result = userMapper.getByUsername(username);

        //驗證帳號是否重複
        if (result != null) {
            throw new DuplicatedUsernameException("帳號重複!");
        }

        //驗證信箱是否重複
        if (userMapper.getByUserEmail(email) != null) {
            throw new DuplicatedEmailException("電子信箱重複!");
        }


        /**TODO: 若使用者用Google/Facebook註冊，將對應的欄位設定為1 */


        /** 密碼加密處理, 加密方法: md5
         form: salt value + password + salt value */

        String saltValue = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(saltValue);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        /** 加入資料庫前，補全部分資料: */
        Date date = new Date();
        user.setVipExpiry(date);
        user.setCreatedAt(date);
        user.setModifiedAt(date);
        /** 註冊成功: rows == 1.*/
        Integer rows = userMapper.register(user);
        if (rows != 1) {
            throw new RegisterException("伺服器/資料庫異常!");
        }
    }

    @Override
    public void regByOAuth(UserEntity user) {
        Integer rows = userMapper.registerByOAuth(user);
        if (rows != 1) {
            throw new RegisterException("第三方註冊失敗");
        }
    }

    @Override
    public UserEntity login(UserEntity user) {
        String username = user.getUsername();
        String password = user.getPassword();
        UserEntity result = userMapper.getByUsername(username);


        if (result == null) {
            throw new UserNotFoundException("帳號不存在");
        }

        if (!passwordEncoder.matches(password, result.getPassword())) {
            throw new PasswordNotMatchException("密碼錯誤!");
        }

        UserEntity newUser = new UserEntity();
        newUser.setId(result.getId());
        newUser.setUsername(result.getUsername());
        //檢查vip是否到期
        Date vipDate = result.getVipExpiry();
        Date currentDate = new Date();
        if (vipDate.before(currentDate)) {
            userMapper.lockedVipById(result.getId(), false);
        }
        return newUser;
    }


    @Override
    public void resetPassword(Integer id, String oldPassword, String newPassword) {
        UserEntity result = userMapper.getUserByID(id);
        if (result == null) {
            throw new UserNotFoundException();
        }

        if (!passwordEncoder.matches(oldPassword, result.getPassword())) {
            throw new PasswordNotMatchException("密碼錯誤");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        Integer row = userMapper.updatePasswordByID(id, encodedPassword, new Date());
        if (row != 1) {
            throw new UpdateException();
        }
    }
    @Override
    public UserEntity getUserByID(Integer id) {
        UserEntity result = userMapper.getUserByID(id);
        if (result == null) {
            throw new UserNotFoundException("使用者不存在");
        }
        return result;
    }

    @Override
    public List<Integer> getAllUsers() {
        List<Integer> userIDList = new ArrayList<>();
        List<UserEntity> entities = userMapper.getAllUsers();
        for (UserEntity entity: entities) {
            userIDList.add(entity.getId());
        }
        return userIDList;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        UserEntity result = userMapper.getByUsername(username);
        if (result == null) {
            throw new UserNotFoundException("使用者不存在");
        }
        return result;
    }

    @Override
    public void updateLogTime(Integer id) {
        userMapper.updateLogTime(new Timestamp(System.currentTimeMillis()),id);
    }


    //忘記密碼
    @Override
    public boolean checkEmailExists(String email) {
        // 驗證輸入的電子郵件地址是否存在於使用者資料庫中
        UserEntity user = userMapper.getByUserEmail(email);
        return user != null;
    }
}


