package cn.zjw.edubackend.security;

import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.zjw.edubackend.pojo.po.*;
import cn.hutool.core.map.MapUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
public class LoginUser implements UserDetails {
    private Integer userType;
    private UserBase user;
    private EduTeacher teacher;
    private EduStudent student;
    private EduAdmin admin;
    private String username;
    private String password;
    private String token;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<GrantedAuthority> authorities;

    private Map<String, Object> infoMap = null;

    public LoginUser() {
    }

    public LoginUser(UserBase user) {
        setUser(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUser(UserBase user) {
        if(user == null)
            return;

        infoMap = MapUtil.newHashMap();

        if(user instanceof EduAdmin) {
            this.userType = UserTypeEnum.ADMIN.getCode();
            this.username = ((EduAdmin) user).getUsername();
            this.admin = (EduAdmin) user;
        }else if(user instanceof EduTeacher) {
            this.userType = UserTypeEnum.TEACHER.getCode();
            this.username = ((EduTeacher) user).getJobNumber();
            this.teacher = (EduTeacher) user;
        }else if(user instanceof EduStudent) {
            this.userType = UserTypeEnum.STUDENT.getCode();
            this.username = ((EduStudent) user).getStudentNumber();
            this.student = (EduStudent) user;
        }
        infoMap.put("id", this.getUser().getId());
        infoMap.put("username", this.username);
        infoMap.put("userType", this.userType);
        infoMap.put("mobile", this.getUser().getMobile());
        infoMap.put("name", this.getUser().getName());

        this.password = user.getPassword();

        this.authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + UserTypeEnum.getByCode(this.userType).name().toLowerCase()));
    }

    public void setTeacher(EduTeacher teacher) {
        this.teacher = teacher;
        setUser(teacher);
    }

    public void setStudent(EduStudent student) {
        this.student = student;
        setUser(student);
    }

    public void setAdmin(EduAdmin admin) {
        this.admin = admin;
        setUser(admin);
    }

    public UserBase getUser() {
        if(teacher != null) {
            return teacher;
        }
        if(admin != null) {
            return admin;
        }
        if(student != null) {
            return student;
        }
        return null;
    }
}
