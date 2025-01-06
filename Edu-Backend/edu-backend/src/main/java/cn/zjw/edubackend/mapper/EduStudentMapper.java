package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EduStudentMapper extends BaseMapper<EduStudent> {
    EduStudent selectByStudentNumber(String studentNumber);
    EduStudent selectByIdcard(String idcard);

//    @Select("SELECT * FROM edu_student")
//    List<EduStudent> selectAllStudents();
}