package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduClassMapper;
import cn.zjw.edubackend.pojo.po.EduClass;
import cn.zjw.edubackend.service.ClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClassServiceImpl extends BaseServiceImpl<EduClass, EduClassMapper> implements ClassService {
    
    @Resource
    private EduClassMapper classMapper;
    
    @Override
    public boolean updateByIdSelective(EduClass eduClass) {
        EduClass checkNameClass = classMapper.selectByName(eduClass.getName());
        if(checkNameClass != null && !checkNameClass.getId().equals(eduClass.getId())) {
            throw new ServiceException("班级名已存在");
        }
        return super.updateByIdSelective(eduClass);
    }

    @Override
    public boolean insertSelective(EduClass eduClass) {
        if(classMapper.selectByName(eduClass.getName()) != null) {
            throw new ServiceException("班级名已存在");
        }
        return super.insertSelective(eduClass);
    }
}
