package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.mapper.EduClassroomMapper;
import cn.zjw.edubackend.pojo.po.EduClassroom;
import cn.zjw.edubackend.service.ClassroomService;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl extends BaseServiceImpl<EduClassroom, EduClassroomMapper> implements ClassroomService {}
