package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduConfig;

import java.util.Map;

public interface ConfigService extends BaseService<EduConfig> {
    boolean updateMoreByKey(Map<String, String> map);
    EduConfig selectByKey(String key);
}
