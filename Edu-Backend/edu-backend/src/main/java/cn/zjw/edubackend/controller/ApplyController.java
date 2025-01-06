package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduApply;
import cn.zjw.edubackend.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apply")
public class ApplyController extends BaseController<EduApply, ApplyService> {

    @Autowired
    private ApplyService applyService;

    @RequestMapping("/deal/{id}/{type}")
    public AjaxResult dealApply(@PathVariable Integer id, @PathVariable String type) {
        applyService.dealApply(id, type);
        return AjaxResult.success();
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.APPLY;
    }
}
