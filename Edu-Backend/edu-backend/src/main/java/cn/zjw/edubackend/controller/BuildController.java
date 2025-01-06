package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduBuild;
import cn.zjw.edubackend.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/build")
public class BuildController extends BaseController<EduBuild, BuildService> {

    @Autowired
    BuildService eduBuildService;

    @GetMapping("/simpleList")
    public AjaxResult selectSimpleList() {
        return AjaxResult.success(eduBuildService.selectSimpleList());
    }

    @GetMapping("/classroomTreeList")
    public AjaxResult classroomTreeList() {
        return AjaxResult.success(eduBuildService.selectClassroomTreeList());
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.BUILD;
    }
}
