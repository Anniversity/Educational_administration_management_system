package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduSelection;

public interface SelectionService extends BaseService<EduSelection> {
    void selectCourse(Integer courseId, Integer studentId);
    boolean cancelSelectCourse(Integer id, Integer userId);
}
