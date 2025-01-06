package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    List<EduCourse> unArrangedCourseList(@Param("year") Integer year, @Param("semester") Integer semester);
}