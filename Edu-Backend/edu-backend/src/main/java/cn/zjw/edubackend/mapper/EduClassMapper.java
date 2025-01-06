package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduClass;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduClassMapper extends BaseMapper<EduClass> {
    EduClass selectByName(String name);
}