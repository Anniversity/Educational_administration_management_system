package cn.zjw.edubackend.common.config;

import cn.zjw.edubackend.common.CacheConstants;
import cn.zjw.edubackend.mapper.EduConfigMapper;
import cn.zjw.edubackend.pojo.po.EduConfig;
import cn.zjw.edubackend.util.RedisCache;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "config")
@Data
public class ProjectConfig {

    @Autowired
    private RedisCache redisCache;
    @Resource
    private EduConfigMapper configMapper;

    /** 上传路径 */
    private static String profile;

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    public static String getProfile()
    {
        return profile;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    @PostConstruct
    public void setConfigInRedis() {
        List<EduConfig> eduConfigs = configMapper.selectList(null);
        eduConfigs.forEach(item -> {
            redisCache.setCacheObject(CacheConstants.CONFIG_KEY + item.getKey(), item.getValue());
        });
    }
}
