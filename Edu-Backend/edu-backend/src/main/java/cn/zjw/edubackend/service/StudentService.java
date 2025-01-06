package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduStudent;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

//import java.util.List;

public interface StudentService extends BaseService<EduStudent> {

    boolean resetPassword(Integer id);
    String getUniqueStudentNumber(EduStudent student);
//    List<Map<String, Object>> countStudentsByStudentNumberPrefix();
}
