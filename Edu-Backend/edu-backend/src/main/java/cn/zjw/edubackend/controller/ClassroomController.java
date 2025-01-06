package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.EduClassroom;
import cn.zjw.edubackend.service.ClassroomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classroom")
public class ClassroomController extends BaseController<EduClassroom, ClassroomService> {
    @Override
    public ServiceEnum getName() {
        return ServiceEnum.CLASSROOM;
    }
}
