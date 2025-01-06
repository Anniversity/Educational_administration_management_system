package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduSection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduSectionMapper extends BaseMapper<EduSection> {
    List<EduSection> selectSectionList();
}