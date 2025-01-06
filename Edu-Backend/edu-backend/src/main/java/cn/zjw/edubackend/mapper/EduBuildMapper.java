package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduBuild;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduBuildMapper extends BaseMapper<EduBuild> {
    List<EduBuild> selectClassroomTreeList();
    EduBuild selectByName(String name);
}