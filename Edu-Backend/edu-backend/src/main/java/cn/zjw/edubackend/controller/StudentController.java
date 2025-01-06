package cn.zjw.edubackend.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.zjw.edubackend.pojo.bbs.Result;
import cn.zjw.edubackend.pojo.po.EduStudent;
import cn.zjw.edubackend.security.LoginUser;
import cn.zjw.edubackend.service.StudentService;
import cn.zjw.edubackend.service.impl.StudentServiceImpl;
import cn.zjw.edubackend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentController extends BaseController<EduStudent, StudentService> {

    @Autowired
    private StudentService studentService;

//    @Resource
//    private StudentServiceImpl studentServiceImpl;

    @Override
    public AjaxResult list(Integer page, Integer pageSize, EduStudent eduStudent, HttpServletRequest request) {
        if(eduStudent == null)
            eduStudent = new EduStudent();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser.getUserType().equals(UserTypeEnum.TEACHER.getCode()))
            eduStudent.setTeacherId(loginUser.getUser().getId());
        return super.list(page, pageSize, eduStudent, request);
    }

    @Override
    public AjaxResult updateById(@PathVariable("id") Integer id, @Validated(ValidateGroup.update.class) @RequestBody EduStudent eduStudent, HttpServletRequest request) {
        return super.updateById(id, eduStudent, request);
    }

    @Override
    public AjaxResult insert(@Validated(ValidateGroup.insert.class) @RequestBody EduStudent eduStudent, HttpServletRequest request) {
        return super.insert(eduStudent, request);
    }

    @Override
    public AjaxResult selectById(@PathVariable("id") Integer id, HttpServletRequest request) {
        return super.selectById(id, request);
    }

    @Override
    public AjaxResult deleteById(@PathVariable("id") Integer id, HttpServletRequest request) {
        return super.deleteById(id, request);
    }

    @PutMapping("/resetPassword/{id}")
    public AjaxResult resetPassword(@PathVariable("id") Integer id) {
        if(!studentService.resetPassword(id)) {
            return AjaxResult.error("重置失败");
        }
        return AjaxResult.success("密码已重置为学号后六位");
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.STUDENT;
    }

//    @GetMapping("/studentNumberPrefixStats")
//    public ResponseEntity<List<Map<String, Object>>> getStudentNumberPrefixStats() {
//        try {
//            // 获取学生数据统计
//            List<Map<String, Object>> stats = studentService.countStudentsByStudentNumberPrefix();
//            return ResponseEntity.ok(stats);  // 返回统计数据
//        } catch (Exception e) {
//            e.printStackTrace(); // 打印异常日志
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // 返回 500 错误
//        }
//    }

}
