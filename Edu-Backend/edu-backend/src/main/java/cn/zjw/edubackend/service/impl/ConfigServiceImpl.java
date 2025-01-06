package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.mapper.EduConfigMapper;
import cn.zjw.edubackend.pojo.po.EduConfig;
import cn.zjw.edubackend.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ConfigServiceImpl extends BaseServiceImpl<EduConfig, EduConfigMapper> implements ConfigService {

    @Resource
    private EduConfigMapper configMapper;

    @Override
    public boolean updateMoreByKey(Map<String, String> map) {
        ArrayList<EduConfig> eduConfigs = new ArrayList<>();
        map.forEach((k, v) -> {
            EduConfig eduConfig = new EduConfig();
            eduConfig.setKey(k);
            eduConfig.setValue(v);
            eduConfigs.add(eduConfig);
        });
        return configMapper.updateMoreByKey(eduConfigs) == eduConfigs.size();
    }

    @Override
    public EduConfig selectByKey(String key) {
        return configMapper.selectByKey(key);
    }
}
