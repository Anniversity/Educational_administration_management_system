package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.mapper.EduSectionMapper;
import cn.zjw.edubackend.pojo.po.EduSection;
import cn.zjw.edubackend.service.SectionService;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl extends BaseServiceImpl<EduSection, EduSectionMapper> implements SectionService {}
