package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduSelection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduSelectionMapper extends BaseMapper<EduSelection> {
    Integer countByCourseId(Integer courseId);
    int deleteByCourseId(Integer courseId);
}