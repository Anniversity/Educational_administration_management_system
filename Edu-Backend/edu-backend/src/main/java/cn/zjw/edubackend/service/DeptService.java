package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduDept;

import java.util.List;

public interface DeptService extends BaseService<EduDept> {
    List<EduDept> selectMajorTreeList();
    List<EduDept> selectTeacherTreeList();
    List<EduDept> selectClassTreeList();
    List<EduDept> selectCourseTreeList();
}
