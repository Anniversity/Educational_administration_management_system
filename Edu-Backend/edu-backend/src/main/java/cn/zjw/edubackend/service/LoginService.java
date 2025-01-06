package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.ChangePasswordDTO;
import cn.zjw.edubackend.pojo.UserLoginDTO;
import cn.zjw.edubackend.pojo.po.UserBase;

public interface LoginService {
    String login(UserLoginDTO userLoginDTO);
    UserBase selectUserByUsernameAndUserType(String username, Integer userType);
    void logout();
    boolean changePassword(ChangePasswordDTO changePasswordDTO);
}
