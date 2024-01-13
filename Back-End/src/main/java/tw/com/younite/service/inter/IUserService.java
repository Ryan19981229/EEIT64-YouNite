package tw.com.younite.service.inter;

import tw.com.younite.entity.UserEntity;

import java.util.List;

public interface IUserService {
    void reg(UserEntity user);
    void regByOAuth(UserEntity user);
    void resetPassword(Integer id, String oldPassword, String newPassword);
    UserEntity login(UserEntity user);
    UserEntity getUserByID(Integer id);
    List<Integer> getAllUsers();

    UserEntity getUserByUsername(String username);

    void updateLogTime(Integer id);

    boolean checkEmailExists(String email);
}
