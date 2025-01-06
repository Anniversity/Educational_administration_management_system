package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduTeacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {
    EduTeacher selectByJobNumber(String jobNumber);

    EduTeacher selectByIdcard(String idcard);
}