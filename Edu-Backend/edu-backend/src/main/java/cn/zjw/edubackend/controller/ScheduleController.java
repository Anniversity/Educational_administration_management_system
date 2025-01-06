package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.zjw.edubackend.pojo.po.EduSchedule;
import cn.zjw.edubackend.pojo.po.arrangecourse.ArrangeCourse;
import cn.zjw.edubackend.security.LoginUser;
import cn.zjw.edubackend.service.ScheduleService;
import cn.zjw.edubackend.util.ConfigUtil;
import cn.zjw.edubackend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController extends BaseController<EduSchedule, ScheduleService> {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/arrange/{year}/{semester}")
    public AjaxResult arrangeCourse(
            @PathVariable Integer year,
            @PathVariable Integer semester,
            @RequestBody List<ArrangeCourse> arrangeCourse) {
        new Thread(() -> scheduleService.arrangeCourse(year, semester, arrangeCourse)).start();
        return AjaxResult.success("排课已提交，请稍后查看排课结果");
    }

    @RequestMapping("/allScheduleList")
    public AjaxResult allScheduleList() {
        EduSchedule eduSchedule = new EduSchedule();
        eduSchedule.setYear(ConfigUtil.getCurrentYear());
        eduSchedule.setSemester(ConfigUtil.getCurrentSemester());
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser.getUserType().equals(UserTypeEnum.TEACHER.getCode()))
            eduSchedule.setTeacherId(loginUser.getUser().getId());
        return AjaxResult.success(scheduleService.selectListFuzzy(eduSchedule));
    }

    @Override
    public AjaxResult list(Integer page, Integer pageSize, EduSchedule eduSchedule, HttpServletRequest request) {
        if(eduSchedule == null)
            eduSchedule = new EduSchedule();
        // 判断当前的用户 设置id
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser.getUserType().equals(UserTypeEnum.STUDENT.getCode())) {
            eduSchedule.setStudentId(loginUser.getUser().getId());
        }
        if(loginUser.getUserType().equals(UserTypeEnum.TEACHER.getCode())) {
            eduSchedule.setTeacherId(loginUser.getUser().getId());
        }
        return AjaxResult.success(scheduleService.selectListFuzzy(eduSchedule));
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.SCHEDULE;
    }
}
