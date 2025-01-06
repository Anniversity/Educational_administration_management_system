package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.config.ProjectConfig;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduConfig;
import cn.zjw.edubackend.service.ConfigService;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController extends BaseController<EduConfig, ConfigService> {

    @Autowired
    private ConfigService configService;

    @Override
    public AjaxResult list(Integer page, Integer pageSize, EduConfig eduConfig, HttpServletRequest request) {
        return AjaxResult.success(configService.selectListFuzzy(eduConfig));
    }

    @RequestMapping("/updateMoreByKey")
    public AjaxResult updateMoreByKey(
            @RequestBody Map<String, String> map
            ) {
        configService.updateMoreByKey(map);
        SpringUtil.getBean(ProjectConfig.class).setConfigInRedis();
        return AjaxResult.success();
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.CONFIG;
    }
}
