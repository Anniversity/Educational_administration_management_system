package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.pojo.ChangePasswordDTO;
import cn.zjw.edubackend.pojo.UserLoginDTO;
import cn.zjw.edubackend.security.LoginUser;
import cn.zjw.edubackend.service.LoginService;
import cn.zjw.edubackend.util.SecurityUtils;
import cn.hutool.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        String token = loginService.login(userLoginDTO);
        AjaxResult success = AjaxResult.success("登录成功");
        success.put(AjaxResult.DATA_TAG, token);
        return success;
    }

    @PostMapping("/logout")
    public AjaxResult logout() {
        loginService.logout();
        return AjaxResult.success("注销成功");
    }

    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser == null)
            throw new ServiceException("登录态失效", HttpStatus.HTTP_UNAUTHORIZED);
        return AjaxResult.success(loginUser.getInfoMap());
    }

    @PostMapping("/changePassword")
    public AjaxResult changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        if(!loginService.changePassword(changePasswordDTO)) {
            return AjaxResult.error("密码修改失败");
        }
        return AjaxResult.success("密码修改成功");
    }

}
