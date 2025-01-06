package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.annotation.validate.UsernameValidation;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EduAdmin extends UserBase implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "管理员用户名不能为空")
    @UsernameValidation
    private String username;

    @Override
    public void createDefaultPassword() {
        BCryptPasswordEncoder passwordEncoder = SpringUtil.getBean(BCryptPasswordEncoder.class);
        setPassword(passwordEncoder.encode("123456"));
    }
}