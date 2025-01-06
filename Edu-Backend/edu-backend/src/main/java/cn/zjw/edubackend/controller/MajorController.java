package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduMajor;
import cn.zjw.edubackend.service.MajorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/major")
public class MajorController extends BaseController<EduMajor, MajorService> {
    @Override
    public ServiceEnum getName() {
        return ServiceEnum.MAJOR;
    }
}
