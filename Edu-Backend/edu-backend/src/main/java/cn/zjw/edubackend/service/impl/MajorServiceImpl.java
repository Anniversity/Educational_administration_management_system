package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.mapper.EduMajorMapper;
import cn.zjw.edubackend.pojo.po.EduMajor;
import cn.zjw.edubackend.service.MajorService;
import org.springframework.stereotype.Service;

@Service
public class MajorServiceImpl extends BaseServiceImpl<EduMajor, EduMajorMapper> implements MajorService {}
