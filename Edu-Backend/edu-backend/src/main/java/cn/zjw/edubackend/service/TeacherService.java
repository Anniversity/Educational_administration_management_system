package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduTeacher;

public interface TeacherService extends BaseService<EduTeacher> {
    boolean resetPassword(Integer id);
    String getUniqueJobNumber(EduTeacher teacher);
}
