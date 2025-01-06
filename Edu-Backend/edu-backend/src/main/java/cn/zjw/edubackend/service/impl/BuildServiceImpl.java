package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduBuildMapper;
import cn.zjw.edubackend.pojo.po.EduBuild;
import cn.zjw.edubackend.service.BuildService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuildServiceImpl extends BaseServiceImpl<EduBuild, EduBuildMapper> implements BuildService {
    @Resource
    private EduBuildMapper buildMapper;

    @Override
    public List<EduBuild> selectSimpleList() {
        return buildMapper.selectList(null);
    }
    @Override
    public List<EduBuild> selectClassroomTreeList() {
        return buildMapper.selectClassroomTreeList();
    }

    @Override
    public boolean updateByIdSelective(EduBuild eduBuild) {
        EduBuild checkNameBuild = buildMapper.selectByName(eduBuild.getName());
        if(checkNameBuild != null && !checkNameBuild.getId().equals(eduBuild.getId())) {
            throw new ServiceException("楼栋名已存在");
        }
        return super.updateByIdSelective(eduBuild);
    }

    @Override
    public boolean insertSelective(EduBuild eduBuild) {
        if(buildMapper.selectByName(eduBuild.getName()) != null) {
            throw new ServiceException("楼栋名已存在");
        }
        return super.insertSelective(eduBuild);
    }
}
