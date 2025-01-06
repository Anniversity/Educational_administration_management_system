package cn.zjw.edubackend.security;

import cn.zjw.edubackend.pojo.po.UserBase;
import cn.zjw.edubackend.service.LoginService;
import cn.zjw.edubackend.util.RedisCache;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.jwt.JWTUtil;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Data
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 1/通过userName 获取到userInfo信息
         * 2/通过User（UserDetails）返回details。
         */
        Integer userType = Integer.parseInt(username.substring(username.length() - 1));
        String realUsername = username.substring(0, username.length() - 1);

        LoginService loginService = SpringUtil.getBean(LoginService.class);

        UserBase user = loginService.selectUserByUsernameAndUserType(realUsername, userType);

        if(user == null)
            return null;

        LoginUser loginUser = new LoginUser(user);
        loginUser.setToken(JWTUtil.createToken(loginUser.getInfoMap(), "123123".getBytes()));

        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        redisCache.setCacheObject("token:" + loginUser.getUserType() + ":" + loginUser.getUser().getId(), loginUser, 30, TimeUnit.DAYS);

        return loginUser;
    }
}