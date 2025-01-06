package cn.zjw.edubackend.util;

import cn.zjw.edubackend.common.CacheConstants;
import cn.hutool.extra.spring.SpringUtil;

public class ConfigUtil {
    private static RedisCache redisCache;

    static {
        redisCache = SpringUtil.getBean(RedisCache.class);
    }

    public static Integer getCurrentYear() {
        return Integer.parseInt(redisCache.getCacheObject(CacheConstants.CONFIG_KEY + "currentYear"));
    }

    public static Integer getCurrentSemester() {
        return Integer.parseInt(redisCache.getCacheObject(CacheConstants.CONFIG_KEY + "currentSemester"));
    }
}
