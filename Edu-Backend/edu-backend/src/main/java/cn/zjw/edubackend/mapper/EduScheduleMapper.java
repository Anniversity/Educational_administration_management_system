package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduSchedule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduScheduleMapper extends BaseMapper<EduSchedule> {
    int deleteByCourseId(Integer courseId);
}