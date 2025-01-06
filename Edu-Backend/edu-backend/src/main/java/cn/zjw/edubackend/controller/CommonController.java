package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.enums.*;
import cn.zjw.edubackend.util.EnumUtil;
import cn.zjw.edubackend.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getConfig")
    public AjaxResult getConfig() {
        Map<String, Object> map = new HashMap<>();

        Map<String, List<EnumUtil.Dict>> stringDictHashMap = new HashMap<>();
        stringDictHashMap.put("classroom_status", EnumUtil.parseEnumToDictList(ClassroomStatusEnum.values()));
        stringDictHashMap.put("course_type", EnumUtil.parseEnumToDictList(CourseTypeEnum.values()));
        stringDictHashMap.put("course_status", EnumUtil.parseEnumToDictList(CourseStatusEnum.values()));
        stringDictHashMap.put("apply_status", EnumUtil.parseEnumToDictList(ApplyStatusEnum.values()));
        stringDictHashMap.put("user_type", EnumUtil.parseEnumToDictList(UserTypeEnum.values()));
        stringDictHashMap.put("arrange_status", EnumUtil.parseEnumToDictList(ArrangeStatusEnum.values()));

        map.put("enums", stringDictHashMap);
        HashMap<String, String> configMap = new HashMap<>();

        String configPrefix = "config:";
        for (String key : redisCache.keys(configPrefix + "*")) {
            configMap.put(key.replace(configPrefix, ""), redisCache.getCacheObject(key));
        }

        map.put("config", configMap);
        return AjaxResult.success(map);
    }

}