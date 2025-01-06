package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.zjw.edubackend.pojo.po.EduClass;
import cn.zjw.edubackend.security.LoginUser;
import cn.zjw.edubackend.service.ClassService;
import cn.zjw.edubackend.util.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/class")
public class ClassController extends BaseController<EduClass, ClassService> {
    @Override
    public AjaxResult list(Integer page, Integer pageSize, EduClass eduClass, HttpServletRequest request) {
        if(eduClass == null)
            eduClass = new EduClass();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser.getUserType().equals(UserTypeEnum.TEACHER.getCode()))
            eduClass.setTeacherId(loginUser.getUser().getId());
        return super.list(page, pageSize, eduClass, request);
    }

    @Override
    public AjaxResult updateById(@PathVariable("id") Integer id, @Validated(ValidateGroup.update.class) @RequestBody EduClass eduClass, HttpServletRequest request) {
        return super.updateById(id, eduClass, request);
    }

    @Override
    public AjaxResult insert(@Validated(ValidateGroup.insert.class) @RequestBody EduClass eduClass, HttpServletRequest request) {
        return super.insert(eduClass, request);
    }

    @Override
    public AjaxResult selectById(@PathVariable("id") Integer id, HttpServletRequest request) {
        return super.selectById(id, request);
    }

    @Override
    public AjaxResult deleteById(@PathVariable("id") Integer id, HttpServletRequest request) {
        return super.deleteById(id, request);
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.CLASS;
    }
}
