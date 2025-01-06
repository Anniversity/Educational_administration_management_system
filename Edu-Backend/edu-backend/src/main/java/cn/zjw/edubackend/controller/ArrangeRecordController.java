package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduArrangeRecord;
import cn.zjw.edubackend.service.ArrangeRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/arrange/record")
public class ArrangeRecordController extends BaseController<EduArrangeRecord, ArrangeRecordService> {
    @Override
    public ServiceEnum getName() {
        return ServiceEnum.ARRANGERECORD;
    }
}
