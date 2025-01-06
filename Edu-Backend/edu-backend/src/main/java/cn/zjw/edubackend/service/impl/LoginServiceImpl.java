package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.CacheConstants;
import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.zjw.edubackend.mapper.EduAdminMapper;
import cn.zjw.edubackend.mapper.EduStudentMapper;
import cn.zjw.edubackend.mapper.EduTeacherMapper;
import cn.zjw.edubackend.pojo.ChangePasswordDTO;
import cn.zjw.edubackend.pojo.UserLoginDTO;
import cn.zjw.edubackend.pojo.po.EduAdmin;
import cn.zjw.edubackend.pojo.po.EduStudent;
import cn.zjw.edubackend.pojo.po.EduTeacher;
import cn.zjw.edubackend.pojo.po.UserBase;
import cn.zjw.edubackend.security.LoginUser;
import cn.zjw.edubackend.service.LoginService;
import cn.zjw.edubackend.util.RedisCache;
import cn.zjw.edubackend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private EduTeacherMapper teacherMapper;
    @Resource
    private EduStudentMapper studentMapper;
    @Resource
    private EduAdminMapper adminMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        // 将用户类型加入到username的最后一位 用于判断
        userLoginDTO.setUsername(userLoginDTO.getUsername() + userLoginDTO.getUserType());

        Authentication authentication = null;

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e) {
            throw new ServiceException(e.getMessage());
        }

        LoginUser details = (LoginUser) authentication.getPrincipal();
        return details.getToken();
    }

    @Override
    public UserBase selectUserByUsernameAndUserType(String username, Integer userType) {
        if (userType.equals(UserTypeEnum.TEACHER.getCode())) {
            return teacherMapper.selectByJobNumber(username);
        } else if (userType.equals(UserTypeEnum.STUDENT.getCode())) {
            return studentMapper.selectByStudentNumber(username);
        } else if (userType.equals(UserTypeEnum.ADMIN.getCode())) {
            return adminMapper.selectByUsername(username);
        }
        return null;
    }

    @Override
    public void logout() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        redisCache.deleteObject(CacheConstants.TOKEN_KEY + loginUser.getUserType() + ":" + loginUser.getUser().getId());
    }

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Integer id = loginUser.getUser().getId();
        String password = loginUser.getUser().getPassword();
        if(!passwordEncoder.matches(changePasswordDTO.getRawPwd(), password))
            throw new ServiceException("原密码不一致");

        String newPassword = passwordEncoder.encode(changePasswordDTO.getNewPwd());

        if(loginUser.getUserType().equals(UserTypeEnum.TEACHER.getCode())) {
            EduTeacher eduTeacher = new EduTeacher();
            eduTeacher.setId(id);
            eduTeacher.setPassword(newPassword);
            return teacherMapper.updateByPrimaryKeySelective(eduTeacher) > 0;
        }else if(loginUser.getUserType().equals(UserTypeEnum.ADMIN.getCode())) {
            EduAdmin eduAdmin = new EduAdmin();
            eduAdmin.setId(id);
            eduAdmin.setPassword(newPassword);
            return adminMapper.updateByPrimaryKeySelective(eduAdmin) > 0;
        }else if(loginUser.getUserType().equals(UserTypeEnum.STUDENT.getCode())) {
            EduStudent eduStudent = new EduStudent();
            eduStudent.setId(id);
            eduStudent.setPassword(newPassword);
            return studentMapper.updateByPrimaryKeySelective(eduStudent) > 0;
        }
        return false;
    }
}
