package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduBuild;

import java.util.List;

public interface BuildService extends BaseService<EduBuild> {
    List<EduBuild> selectSimpleList();
    List<EduBuild> selectClassroomTreeList();
}
