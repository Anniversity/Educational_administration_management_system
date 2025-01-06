package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.zjw.edubackend.common.enums.CourseTypeEnum;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.zjw.edubackend.pojo.po.EduCourse;
import cn.zjw.edubackend.security.LoginUser;
import cn.zjw.edubackend.service.CourseService;
import cn.zjw.edubackend.util.ConfigUtil;
import cn.zjw.edubackend.util.SecurityUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/course")
public class CourseController extends BaseController<EduCourse, CourseService> {

    @Autowired
    private CourseService courseService;

    @Override
    @PreAuthorize("hasAnyRole('admin', 'teacher')")
    public AjaxResult list(Integer page, Integer pageSize, EduCourse eduCourse, HttpServletRequest request) {
        if(eduCourse == null)
            eduCourse = new EduCourse();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser.getUserType().equals(UserTypeEnum.TEACHER.getCode()))
            eduCourse.setTeacherId(loginUser.getUser().getId());
        return super.list(page, pageSize, eduCourse, request);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'teacher')")
    public AjaxResult updateById(@PathVariable("id") Integer id, @Validated(ValidateGroup.update.class) @RequestBody EduCourse eduCourse, HttpServletRequest request) {
        return super.updateById(id, eduCourse, request);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'teacher')")
    public AjaxResult insert(@Validated(ValidateGroup.insert.class) @RequestBody EduCourse eduCourse, HttpServletRequest request) {
        return super.insert(eduCourse, request);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'teacher')")
    public AjaxResult selectById(@PathVariable("id") Integer id, HttpServletRequest request) {
        return super.selectById(id, request);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin')")
    public AjaxResult deleteById(@PathVariable("id") Integer id, HttpServletRequest request) {
        return super.deleteById(id, request);
    }

    @RequestMapping("/unArrangedCourseList")
    public AjaxResult unArrangedCourseList(
            @NotNull(message = "学年不能为空") Integer year,
            @NotNull(message = "学期不能为空") Integer semester) {
        return AjaxResult.success(courseService.unArrangedCourseList(year, semester));
    }

    @RequestMapping("/electiveCourseList")
    @PreAuthorize("hasAnyRole('student')")
    public AjaxResult getElectiveCourseList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            EduCourse eduCourse
    ) {
        if(eduCourse == null)
            eduCourse = new EduCourse();
        eduCourse.setType(CourseTypeEnum.ELECTIVE.getCode());
        eduCourse.setYear(ConfigUtil.getCurrentYear());
        eduCourse.setSemester(ConfigUtil.getCurrentSemester());
        PageHelper.startPage(page, pageSize);
        return AjaxResult.success(courseService.selectListFuzzy(eduCourse));
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.COURSE;
    }
}
