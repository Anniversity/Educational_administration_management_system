package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.mapper.EduSectionMapper;
import cn.zjw.edubackend.pojo.po.EduSection;
import cn.zjw.edubackend.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/section")
public class SectionController extends BaseController<EduSection, SectionService> {

    @Autowired
    private EduSectionMapper sectionMapper;

    @GetMapping("/sectionList")
    public AjaxResult sectionList() {
        return AjaxResult.success(sectionMapper.selectSectionList());
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.SECTION;
    }
}
