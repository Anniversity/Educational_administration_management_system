package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduDeptMapper extends BaseMapper<EduDept> {
    List<EduDept> selectMajorTreeList();

    List<EduDept> selectTeacherTreeList();

    List<EduDept> selectClassTreeList();

    List<EduDept> selectCourseTreeList();

    EduDept selectByName(String name);
}