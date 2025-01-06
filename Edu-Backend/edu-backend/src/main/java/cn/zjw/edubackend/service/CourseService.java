package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduCourse;

import java.util.List;

public interface CourseService extends BaseService<EduCourse> {
    List<EduCourse> unArrangedCourseList(Integer year, Integer semester);
}
