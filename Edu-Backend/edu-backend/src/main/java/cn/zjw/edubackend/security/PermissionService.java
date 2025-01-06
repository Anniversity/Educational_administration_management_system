package cn.zjw.edubackend.security;

import cn.zjw.edubackend.common.enums.OperateEnum;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

public class PermissionService {

    private static final Map<String, String> permissionMap = new HashMap<>();

    static {
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            for (ServiceEnum serviceEnum : ServiceEnum.values()) {
                String userType = userTypeEnum.name().toLowerCase();
                String service = serviceEnum.name().toLowerCase();

                String key = "permission." + userType + "." + service;
                String value = SpringUtil.getBean(Environment.class).getProperty(key);

                permissionMap.put(key, value);
            }
        }
    }

    public boolean hasPermission(ServiceEnum service, OperateEnum operate) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        UserTypeEnum userType = UserTypeEnum.getByCode(loginUser.getUserType());
        String permission = permissionMap.get("permission." + userType.name().toLowerCase() + "." + service.name().toLowerCase());

        if(StrUtil.isEmptyOrUndefined(permission)) {
            return false;
        }
        return true;
    }

}
