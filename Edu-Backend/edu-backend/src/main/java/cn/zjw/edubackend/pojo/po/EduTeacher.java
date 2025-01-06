package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.annotation.validate.IdcardValidation;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class EduTeacher extends UserBase implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotNull(groups = ValidateGroup.insert.class, message = "出生日期不能为空")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(groups = ValidateGroup.insert.class, message = "入职日期不能为空")
    private Date entryDate;

    @NotNull(groups = ValidateGroup.insert.class, message = "身份证号不能为空")
    @IdcardValidation
    private String idcard;

    @NotNull(groups = ValidateGroup.insert.class, message = "工号不能为空")
    private String jobNumber;

    @NotNull(groups = ValidateGroup.insert.class, message = "所属院系不能为空")
    private Integer deptId;

    private String descr;

    private EduDept dept;

    @Override
    public void createDefaultPassword() {
        if(!StrUtil.isBlank(jobNumber)) {
            BCryptPasswordEncoder passwordEncoder = SpringUtil.getBean(BCryptPasswordEncoder.class);
            setPassword(passwordEncoder.encode(jobNumber.substring(5)));
        }
    }

    public void setBirthdayByIdcard() {
        if(!StrUtil.isBlank(idcard)) {
            birthday = IdcardUtil.getBirthDate(idcard);
        }
    }
}