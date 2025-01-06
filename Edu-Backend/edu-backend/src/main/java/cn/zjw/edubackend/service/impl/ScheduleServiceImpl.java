package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.enums.ArrangeStatusEnum;
import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.*;
import cn.zjw.edubackend.pojo.po.*;
import cn.zjw.edubackend.pojo.po.arrangecourse.ArrangeCourse;
import cn.zjw.edubackend.pojo.po.arrangecourse.MyGa;
import cn.zjw.edubackend.pojo.po.arrangecourse.GaSchedule;
import cn.zjw.edubackend.service.ScheduleService;
import cn.zjw.edubackend.util.ConfigUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl extends BaseServiceImpl<EduSchedule, EduScheduleMapper> implements ScheduleService {

    @Resource
    private EduCourseMapper courseMapper;
    @Resource
    private EduClassroomMapper classroomMapper;
    @Resource
    private EduScheduleMapper scheduleMapper;
    @Resource
    private EduSectionMapper sectionMapper;
    @Resource
    private EduArrangeRecordMapper arrangeRecordMapper;

    @Override
    public void arrangeCourse(Integer year, Integer semester, List<ArrangeCourse> arrangeCourse) {
        // 添加排课记录
        EduArrangeRecord eduArrangeRecord = new EduArrangeRecord();
        eduArrangeRecord.setYear(year);
        eduArrangeRecord.setSemester(semester);
        eduArrangeRecord.setData(JSONUtil.toJsonStr(arrangeCourse));
        eduArrangeRecord.setStatus(ArrangeStatusEnum.ARRANGING.getCode());

        arrangeRecordMapper.insertSelective(eduArrangeRecord);

        // 查询当前学期的所有已排课课程
        EduSchedule eduScheduleSelectCurrSemester = new EduSchedule();
        eduScheduleSelectCurrSemester.setYear(ConfigUtil.getCurrentYear());
        eduScheduleSelectCurrSemester.setSemester(ConfigUtil.getCurrentSemester());
        List<EduSchedule> currSemesterSchedules = scheduleMapper.selectListFuzzy(eduScheduleSelectCurrSemester);

        /*// 获取当前学期的所有课程
        List<EduCourse> currSemesterCourses = courseMapper.selectList(eduCourse);
        // 获取当前的所有教室
        List<EduClassroom> eduClassrooms = classroomMapper.selectList(null);

        // 构建排课的需要数据源
        List<GaSchedule> gaSchedules = currSemesterCourses.stream().map(item -> {
            return new GaSchedule(item.getId() + "|" + item.getTeacherId() + "|" + item.getClassId(), item.getType());
        }).collect(Collectors.toList());
        MyGa myGa = new MyGa();*/

        List<GaSchedule> gaSchedules = new ArrayList<>();
        arrangeCourse.forEach(item -> {
            item.getCourses().forEach(courseId -> {
                EduCourse course = courseMapper.selectByPrimaryKey(courseId);
                Integer classId = course.getClassId();
                Integer teacherId = course.getTeacherId();

                GaSchedule gaSchedule = new GaSchedule(String.format("%d|%d|%d", courseId, teacherId, classId));
                gaSchedule.setClassrooms(item.getClassrooms().stream().map(classroomId -> {
                    return classroomMapper.selectByPrimaryKey(classroomId);
                }).collect(Collectors.toList()));
                gaSchedule.setSlots(item.getSections());

                gaSchedules.add(gaSchedule);
            });
        });

        MyGa myGa = new MyGa();
        ArrayList<GaSchedule> initGaSchedules = new ArrayList<>();
        currSemesterSchedules.forEach(item -> {
            GaSchedule gaSchedule = new GaSchedule(String.format("%d|%d|%d", item.getCourseId(), item.getCourse().getTeacherId(), item.getCourse().getClassId()));
            gaSchedule.setSlot(item.getSectionId());
            gaSchedule.setClassroom(item.getClassroom());
            gaSchedule.setCanEdit(false);
            initGaSchedules.add(gaSchedule);
        });
        myGa.setInitPopulation(initGaSchedules);

        // 结果
        try {
            myGa.evolution(gaSchedules/*, eduClassrooms*/);
            gaSchedules.forEach(item -> {
                EduSchedule eduSchedule = new EduSchedule();
                String[] split = item.getTeachtaskid().split("\\|");

                eduSchedule.setCourseId(Integer.parseInt(split[0]));
                eduSchedule.setClassroomId(item.getClassroom().getId());

//            EduSection eduSection = new EduSection();
//            eduSection.setDay(item.getWeekday());
//            eduSection.setStartNum(item.getSlot() * 2 - 1);

                eduSchedule.setSectionId(item.getSlot());
                scheduleMapper.insert(eduSchedule);
            });
            // 更新当前排课记录状态为已完成
            eduArrangeRecord.setStatus(ArrangeStatusEnum.SUCCESS.getCode());
        }catch (ServiceException e) {
            eduArrangeRecord.setStatus(ArrangeStatusEnum.FAIL.getCode());
            eduArrangeRecord.setRemark("课程安排存在冲突，无法排课");
        }
        arrangeRecordMapper.updateByPrimaryKeySelective(eduArrangeRecord);
    }
}
