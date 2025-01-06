package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduDept;
import cn.zjw.edubackend.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dept")
public class DeptController extends BaseController<EduDept, DeptService> {

    @Autowired
    DeptService deptService;

    @RequestMapping("/simpleList")
    public AjaxResult simpleList(
            EduDept Dept
    ) {
        return AjaxResult.success(deptService.selectList(Dept));
    }

    @GetMapping("/majorTreeList")
    public AjaxResult majorTreeList() {
        return AjaxResult.success(deptService.selectMajorTreeList());
    }
    @GetMapping("/teacherTreeList")
    public AjaxResult teacherTreeList() {
        return AjaxResult.success(deptService.selectTeacherTreeList());
    }
    @GetMapping("/classTreeList")
    public AjaxResult classTreeList() {
        return AjaxResult.success(deptService.selectClassTreeList());
    }
    @GetMapping("/courseTreeList")
    public AjaxResult courseTreeList() {
        return AjaxResult.success(deptService.selectCourseTreeList());
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.DEPT;
    }
}
