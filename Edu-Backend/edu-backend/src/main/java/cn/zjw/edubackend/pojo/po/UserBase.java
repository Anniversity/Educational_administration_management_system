package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import cn.zjw.edubackend.common.annotation.validate.MobileValidation;
import cn.zjw.edubackend.common.annotation.validate.NameValidation;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public abstract class UserBase extends BaseEntity {
//    @NotNull(groups = ValidateGroup.insert.class, message = "密码不能为空")
    private String password;

    @NotNull(groups = ValidateGroup.insert.class, message = "姓名不能为空")
    @NameValidation
    private String name;

    @NotNull(groups = ValidateGroup.insert.class, message = "电话号不能为空")
    @MobileValidation
    private String mobile;

    public abstract void createDefaultPassword();
}
