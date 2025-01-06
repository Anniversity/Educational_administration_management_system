package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduTeacher;
import cn.zjw.edubackend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController extends BaseController<EduTeacher, TeacherService> {

    @Autowired
    private TeacherService teacherService;

    @PutMapping("/resetPassword/{id}")
    public AjaxResult resetPassword(@PathVariable("id") Integer id) {
        if(!teacherService.resetPassword(id)) {
            return AjaxResult.error("重置失败");
        }
        return AjaxResult.success("密码已重置为工号后六位");
    }

    @Override
    public AjaxResult insert(@Validated(ValidateGroup.insert.class) @RequestBody EduTeacher teacher, HttpServletRequest request) {
        return super.insert(teacher, request);
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.TEACHER;
    }
}
