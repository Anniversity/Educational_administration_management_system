package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduConfigMapper extends BaseMapper<EduConfig> {
    int updateMoreByKey(List<EduConfig> list);
    EduConfig selectByKey(String key);
}