package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.CacheConstants;
import cn.zjw.edubackend.common.enums.CourseStatusEnum;
import cn.zjw.edubackend.common.enums.CourseTypeEnum;
import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduConfigMapper;
import cn.zjw.edubackend.mapper.EduCourseMapper;
import cn.zjw.edubackend.mapper.EduScheduleMapper;
import cn.zjw.edubackend.mapper.EduSelectionMapper;
import cn.zjw.edubackend.pojo.po.EduCourse;
import cn.zjw.edubackend.pojo.po.EduSchedule;
import cn.zjw.edubackend.pojo.po.EduSelection;
import cn.zjw.edubackend.service.SelectionService;
import cn.zjw.edubackend.util.RedisCache;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SelectionServiceImpl extends BaseServiceImpl<EduSelection, EduSelectionMapper> implements SelectionService {

    @Resource
    EduCourseMapper courseMapper;
    @Resource
    EduScheduleMapper scheduleMapper;
    @Resource
    EduSelectionMapper selectionMapper;
    @Resource
    EduConfigMapper configMapper;
    @Autowired
    RedisCache redisCache;

    private void preValidate() {
        // 查询当前是否在可选课时间段内
        String selectionStartTime = redisCache.getCacheObject(CacheConstants.CONFIG_KEY + "selectionStartTime");
        String selectionEndTime = redisCache.getCacheObject(CacheConstants.CONFIG_KEY + "selectionEndTime");
        DateTime startTime = DateUtil.parse(selectionStartTime);
        DateTime endTime = DateUtil.parse(selectionEndTime);
        DateTime date = DateUtil.date();
        if(date.isBefore(startTime)) {
            throw new ServiceException("选课还未开始");
        }
        if(date.isAfter(endTime)) {
            throw new ServiceException("选课已结束");
        }
    }

    @Override
    public void selectCourse(Integer courseId, Integer studentId) {
        preValidate();
        // 查询当前课程状态是否为可选
        EduCourse eduCourse = courseMapper.selectByPrimaryKey(courseId);
        if(eduCourse == null)
            throw new ServiceException("课程不存在");
        if(eduCourse.getStatus().equals(CourseStatusEnum.DISABLED.getCode()))
            throw new ServiceException("无法选修当前课程");
        // 查询当前课程选修人数
        if(selectionMapper.countByCourseId(courseId) >= eduCourse.getSumNumber()) {
            throw new ServiceException("当前课程选修人数已满，无法选修");
        }
        // 查询当前排课记录
        EduSchedule eduSchedule = new EduSchedule();
        eduSchedule.setCourseId(courseId);
        List<EduSchedule> courseSchedules = scheduleMapper.selectList(eduSchedule);
        if(courseSchedules.size() == 0)
            throw new ServiceException("当前课程还未排课，无法选修");
        EduSchedule courseSchedule = courseSchedules.get(0);
        // 查询当前学生的其他选修记录 查看是否冲突
        EduSelection studentSelectionBean = new EduSelection();
        studentSelectionBean.setStudentId(studentId);
        List<EduSelection> studentSelectList = selectionMapper.selectList(studentSelectionBean);
        studentSelectList.forEach(item -> {
            if(item.getCourseId().equals(courseId)) {
                throw new ServiceException("您已选修当前课程，请勿重复选修");
            }

            EduSchedule eduScheduleSelectCourse = new EduSchedule();
            eduScheduleSelectCourse.setCourseId(item.getCourseId());
            scheduleMapper.selectList(eduScheduleSelectCourse).forEach(currCourseSchedule -> {
                if(currCourseSchedule.getSectionId().equals(courseSchedule.getSectionId())) {
                    throw new ServiceException("当前课程与您已选修的课程冲突，无法选修");
                }
            });
        });
        EduSelection eduSelection = new EduSelection();
        eduSelection.setStudentId(studentId);
        eduSelection.setCourseId(courseId);
        selectionMapper.insert(eduSelection);
    }

    @Override
    public boolean cancelSelectCourse(Integer id, Integer studentId) {
        preValidate();
        EduSelection selection = selectionMapper.selectByPrimaryKey(id);
        if(selection == null || !selection.getStudentId().equals(studentId)) {
            throw new ServiceException("选课记录不存在");
        }
        Integer courseId = selection.getCourseId();
        // 查询当前课程状态是否为可选
        EduCourse eduCourse = courseMapper.selectByPrimaryKey(courseId);
        if(eduCourse == null)
            throw new ServiceException("课程不存在");
        if(!eduCourse.getType().equals(CourseTypeEnum.ELECTIVE.getCode())) {
            throw new ServiceException("当前课程不是选修课，无法退选");
        }
        return selectionMapper.deleteByPrimaryKey(selection.getId()) > 0;
    }
}
