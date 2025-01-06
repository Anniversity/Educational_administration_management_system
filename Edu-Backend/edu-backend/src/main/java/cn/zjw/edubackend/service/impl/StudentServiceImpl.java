package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduClassMapper;
import cn.zjw.edubackend.mapper.EduMajorMapper;
import cn.zjw.edubackend.mapper.EduStudentMapper;
import cn.zjw.edubackend.pojo.po.EduStudent;
import cn.zjw.edubackend.service.StudentService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl extends BaseServiceImpl<EduStudent, EduStudentMapper> implements StudentService {

    @Resource
    private EduStudentMapper studentMapper;
    @Resource
    private EduClassMapper classMapper;
    @Resource
    private EduMajorMapper majorMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean resetPassword(Integer id) {
        EduStudent eduStudent = new EduStudent();
        eduStudent.setId(id);
        eduStudent.setPassword(passwordEncoder.encode(studentMapper.selectByPrimaryKey(id).getStudentNumber().substring(5)));
        return studentMapper.updateByPrimaryKeySelective(eduStudent) > 0;
    }

    @Override
    public String getUniqueStudentNumber(EduStudent student) {
        String studentNumber = null;
        int entryYear = DateUtil.year(student.getEntryDate()) % 100;

        Integer classId = student.getClassId();
        Integer majorId = classMapper.selectByPrimaryKey(classId).getMajorId();
        Integer deptId = majorMapper.selectByPrimaryKey(majorId).getDeptId();

        do {
            studentNumber = entryYear + String.format("%02d", deptId) + String.format("%03d", majorId) + (int)(Math.random() * 10000);
        }while (studentMapper.selectByStudentNumber(studentNumber) != null);
        return studentNumber;
    }

    @Override
    public boolean insertSelective(EduStudent student) {
        EduStudent checkIdcardStudent = studentMapper.selectByIdcard(student.getIdcard());
        if(checkIdcardStudent != null) {
            throw new ServiceException(String.format("该身份证号与学生【%s】学号【%s】重复", checkIdcardStudent.getName(), checkIdcardStudent.getStudentNumber()));
        }

        student.setStudentNumber(getUniqueStudentNumber(student));
        student.createDefaultPassword();
        student.setBirthdayByIdcard();

        return super.insertSelective(student);
    }

    @Override
    public boolean updateByIdSelective(EduStudent student) {
        if(!StrUtil.isBlank(student.getIdcard())) {
            EduStudent checkIdcardStudent = studentMapper.selectByIdcard(student.getIdcard());
            if(checkIdcardStudent != null && !checkIdcardStudent.getId().equals(student.getId())) {
                throw new ServiceException(String.format("该身份证号与学生【%s】学号【%s】重复", checkIdcardStudent.getName(), checkIdcardStudent.getStudentNumber()));
            }
            student.setBirthdayByIdcard();
        }

        return super.updateByIdSelective(student);
    }

//    @Override
//    public List<Map<String, Object>> countStudentsByStudentNumberPrefix() {
//        // 查询所有学生的学号，假设EduStudentMapper中有一个方法可以查询到所有学生
//        List<EduStudent> students = studentMapper.selectAllStudents();
//
//        // 使用一个Map来统计每个学号前4位的学生数量
//        Map<String, Integer> prefixCountMap = new HashMap<>();
//
//        for (EduStudent student : students) {
//            String studentNumber = student.getStudentNumber();
//            if (studentNumber != null && studentNumber.length() >= 4) {
//                // 获取学号的前四位
//                String prefix = studentNumber.substring(0, 4);
//                // 统计每个前缀出现的次数
//                prefixCountMap.put(prefix, prefixCountMap.getOrDefault(prefix, 0) + 1);
//            }
//        }
//        // 将Map转换为List形式，适配前端需要的数据格式
//        List<Map<String, Object>> result = new ArrayList<>();
//        for (Map.Entry<String, Integer> entry : prefixCountMap.entrySet()) {
//            Map<String, Object> entryMap = new HashMap<>();
//            entryMap.put("prefix", entry.getKey());
//            entryMap.put("count", entry.getValue());
//            result.add(entryMap);
//        }
//
//        return result;
//    }

}
