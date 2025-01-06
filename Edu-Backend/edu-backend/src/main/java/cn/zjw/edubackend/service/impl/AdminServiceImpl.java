package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduAdminMapper;
import cn.zjw.edubackend.pojo.po.EduAdmin;
import cn.zjw.edubackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl extends BaseServiceImpl<EduAdmin, EduAdminMapper> implements AdminService {

    @Resource
    private EduAdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean resetPassword(Integer id) {
        EduAdmin eduAdmin = new EduAdmin();
        eduAdmin.setId(id);
        eduAdmin.setPassword(passwordEncoder.encode("123456"));
        return adminMapper.updateByPrimaryKeySelective(eduAdmin) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        if(adminMapper.selectList(null).size() == 1) {
            throw new ServiceException("系统中至少要有一个管理员");
        }
        return super.deleteById(id);
    }
}
