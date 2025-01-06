package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduTeacherMapper;
import cn.zjw.edubackend.pojo.po.EduTeacher;
import cn.zjw.edubackend.service.TeacherService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<EduTeacher, EduTeacherMapper> implements TeacherService {

    @Resource
    private EduTeacherMapper teacherMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean resetPassword(Integer id) {
        EduTeacher eduTeacher = new EduTeacher();
        eduTeacher.setId(id);
        eduTeacher.setPassword(passwordEncoder.encode(teacherMapper.selectByPrimaryKey(id).getJobNumber().substring(5)));
        return teacherMapper.updateByPrimaryKeySelective(eduTeacher) > 0;
    }

    @Override
    public String getUniqueJobNumber(EduTeacher teacher) {
        String jobNumber = null;
        do {
            jobNumber = teacher.getEntryDate().getYear() + "" + (int)(Math.random() * 10000000);
        }while (teacherMapper.selectByJobNumber(jobNumber) != null);
        return jobNumber;
    }

    @Override
    public boolean updateByIdSelective(EduTeacher teacher) {
        if(!StrUtil.isBlank(teacher.getIdcard())) {
            EduTeacher checkIdcardStudent = teacherMapper.selectByIdcard(teacher.getIdcard());
            if(checkIdcardStudent != null && !checkIdcardStudent.getId().equals(teacher.getId())) {
                throw new ServiceException(String.format("该身份证号与教师【%s】工号【%s】重复", checkIdcardStudent.getName(), checkIdcardStudent.getJobNumber()));
            }
            teacher.setBirthdayByIdcard();
        }

        return super.updateByIdSelective(teacher);
    }

    @Override
    public boolean insertSelective(EduTeacher teacher) {
        EduTeacher checkIdcardTeacher = teacherMapper.selectByIdcard(teacher.getIdcard());
        if(checkIdcardTeacher != null) {
            throw new ServiceException(String.format("该身份证号与教师【%s】工号【%s】重复", checkIdcardTeacher.getName(), checkIdcardTeacher.getJobNumber()));
        }

        teacher.setJobNumber(getUniqueJobNumber(teacher));
        teacher.createDefaultPassword();
        teacher.setBirthdayByIdcard();
        return super.insertSelective(teacher);
    }
}
