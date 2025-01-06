package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.enums.CourseTypeEnum;
import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduCourseMapper;
import cn.zjw.edubackend.mapper.EduScheduleMapper;
import cn.zjw.edubackend.mapper.EduSelectionMapper;
import cn.zjw.edubackend.mapper.EduStudentMapper;
import cn.zjw.edubackend.pojo.po.EduCourse;
import cn.zjw.edubackend.pojo.po.EduSelection;
import cn.zjw.edubackend.pojo.po.EduStudent;
import cn.zjw.edubackend.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl extends BaseServiceImpl<EduCourse, EduCourseMapper> implements CourseService {

    @Resource
    private EduStudentMapper studentMapper;
    @Resource
    private EduSelectionMapper selectionMapper;
    @Resource
    private EduCourseMapper courseMapper;
    @Resource
    private EduScheduleMapper scheduleMapper;

    @Override
    public boolean insertSelective(EduCourse eduCourse) {
        Integer classId = eduCourse.getClassId();
        EduStudent eduStudent = new EduStudent();
        eduStudent.setClassId(classId);
        boolean insert = super.insertSelective(eduCourse);
        // 给学生添加选课记录
        if (eduCourse.getType().equals(CourseTypeEnum.REQUIRED.getCode())){

            List<EduStudent> eduStudents = studentMapper.selectList(eduStudent);
            eduStudents.forEach(item -> {
                EduSelection selection = new EduSelection();
                selection.setStudentId(item.getId());
                selection.setCourseId(eduCourse.getId());
                selectionMapper.insert(selection);
            });
        }
        return insert;
    }

    @Override
    public boolean updateByIdSelective(EduCourse eduCourse) {
        EduCourse eduCourseSelect = courseMapper.selectByPrimaryKey(eduCourse.getId());
        if(!eduCourseSelect.getType().equals(eduCourse.getType())) {
            throw new ServiceException("不能修改课程类型，请删除后重新添加课程。");
        }
        return super.updateByIdSelective(eduCourse);
    }

    @Override
    public boolean deleteById(Integer id) {
        scheduleMapper.deleteByCourseId(id);
        selectionMapper.deleteByCourseId(id);
        return super.deleteById(id);
    }

    @Override
    public List<EduCourse> unArrangedCourseList(Integer year, Integer semester) {
        return courseMapper.unArrangedCourseList(year, semester);
    }
}
