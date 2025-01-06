package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduAdmin;
import cn.zjw.edubackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('admin')")
public class AdminController extends BaseController<EduAdmin, AdminService> {

    @Autowired
    private AdminService adminService;

    @Override
    public AjaxResult list(Integer page, Integer pageSize, EduAdmin eduAdmin, HttpServletRequest request) {
        return super.list(page, pageSize, eduAdmin, request);
    }

    @Override
    public AjaxResult updateById(@PathVariable("id") Integer id, @RequestBody EduAdmin eduAdmin, HttpServletRequest request) {
        return super.updateById(id, eduAdmin, request);
    }

    @Override
    public AjaxResult insert(@Validated(ValidateGroup.insert.class) @RequestBody EduAdmin eduAdmin, HttpServletRequest request) {
        eduAdmin.createDefaultPassword();
        return super.insert(eduAdmin, request);
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
        if(!adminService.resetPassword(id)) {
            return AjaxResult.error("重置失败");
        }
        return AjaxResult.success("密码已重置为123456");
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.ADMIN;
    }
}