package tw.com.younite.mapper;

import tw.com.younite.entity.OrdersEntity;
import tw.com.younite.entity.UserEntity;
import tw.com.younite.entity.UserProfileEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    /**
     * @param user
     * @return 受影響的數據(增 、 刪 、 改都有 ， 可以根據返回值判斷)
     */
    Integer register(UserEntity user);

    Integer registerByOAuth(UserEntity user);

    UserEntity getByUsername(String username);

    UserEntity getByUserEmail(String email);

    UserEntity getByUserPhone(String phone);

    /**
     * 根據users table / user id 來修改密碼
     *
     * @param id         用戶的id
     * @param password   用戶輸入的新密碼
     * @param modifiedAt 用戶修改時間
     * @return 受影響的行數
     */
    Integer updatePasswordByID(Integer id, String password, Date modifiedAt);

    /**
     * 根據users table / user id 來查詢用戶
     *
     * @param id 用戶的id
     * @return User物件
     */
    UserEntity getUserByID(Integer id);

    /**
     * 根據users table / user id 來查詢用戶
     *
     * @param id 用戶的id
     * @return Profile物件
     */

    void updateVipById(Integer id, Date vipExpiry, Boolean unlocked);

    void lockedVipById(Integer id, Boolean unlocked);

    List<UserEntity> getAllUsers();

    List<Map<String, Object>> trial();

    void updateLogTime(Timestamp logTime,Integer id);

    //忘記密碼
    void updatePasswordByEmail(String email, String password);

    void updateResetTokenByEmail(String email, String resetToken);

    String tokenByEmail (String email);
}
