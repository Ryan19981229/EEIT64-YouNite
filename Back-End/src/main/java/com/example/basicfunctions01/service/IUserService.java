package com.example.basicfunctions01.service;


import tw.com.younite.entity.UserEntity;
import tw.com.younite.entity.UserProfileEntity;


public interface IUserService {
    /** register interface*/
    void reg(UserEntity user);
    void insertProfile(UserProfileEntity userProfile);
    UserEntity login(UserEntity user);
    void changePassword(Integer id, String username, String oldPassword, String newPassword);


}
