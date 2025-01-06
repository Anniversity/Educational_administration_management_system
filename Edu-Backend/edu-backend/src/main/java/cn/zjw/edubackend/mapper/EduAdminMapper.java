package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduAdminMapper extends BaseMapper<EduAdmin> {
    EduAdmin selectByUsername(String username);
}