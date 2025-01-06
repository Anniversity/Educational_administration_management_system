package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduSchedule;
import cn.zjw.edubackend.pojo.po.arrangecourse.ArrangeCourse;

import java.util.List;

public interface ScheduleService extends BaseService<EduSchedule> {
    void arrangeCourse(Integer year, Integer semester, List<ArrangeCourse> arrangeCourse);
}
