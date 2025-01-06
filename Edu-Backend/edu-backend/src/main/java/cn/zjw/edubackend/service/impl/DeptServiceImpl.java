package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduDeptMapper;
import cn.zjw.edubackend.pojo.po.EduDept;
import cn.zjw.edubackend.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl extends BaseServiceImpl<EduDept, EduDeptMapper> implements DeptService {
    @Resource
    EduDeptMapper eduDeptMapper;

    @Override
    public List<EduDept> selectMajorTreeList() {
        return eduDeptMapper.selectMajorTreeList();
    }

    @Override
    public List<EduDept> selectTeacherTreeList() {
        return eduDeptMapper.selectTeacherTreeList();
    }

    @Override
    public List<EduDept> selectClassTreeList() {
        return eduDeptMapper.selectClassTreeList();
    }

    @Override
    public List<EduDept> selectCourseTreeList()  {
        return eduDeptMapper.selectCourseTreeList();
    }

    @Override
    public boolean updateByIdSelective(EduDept eduDept) {
        EduDept checkNameDept = eduDeptMapper.selectByName(eduDept.getName());
        if(checkNameDept != null && !checkNameDept.getId().equals(eduDept.getId())) {
            throw new ServiceException("院系名已存在");
        }
        return super.updateByIdSelective(eduDept);
    }

    @Override
    public boolean insertSelective(EduDept eduDept) {
        if(eduDeptMapper.selectByName(eduDept.getName()) != null) {
            throw new ServiceException("院系名已存在");
        }
        return super.insertSelective(eduDept);
    }
}
