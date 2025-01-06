package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.annotation.validate.IdcardValidation;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.zjw.edubackend.service.StudentService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class EduStudent extends UserBase implements Serializable {

//    @NotNull(groups = ValidateGroup.insert.class, message = "学号不能为空")
    private String studentNumber;

    @NotNull(groups = ValidateGroup.insert.class, message = "身份证号不能为空")
    @IdcardValidation
    private String idcard;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotNull(groups = ValidateGroup.insert.class, message = "出生日期不能为空")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(groups = ValidateGroup.insert.class, message = "入学日期不能为空")
    private Date entryDate;

    @NotNull(groups = ValidateGroup.insert.class, message = "所属班级不能为空")
    private Integer classId;

    private EduClass eduClass;

    /** 查询条件开始 */
    private Integer teacherId;

    public void setBirthdayByIdcard() {
        if(!StrUtil.isBlank(idcard)) {
            birthday = IdcardUtil.getBirthDate(idcard);
        }
    }

    @Override
    public void createDefaultPassword() {
        if(!StrUtil.isBlank(studentNumber)) {
            BCryptPasswordEncoder passwordEncoder = SpringUtil.getBean(BCryptPasswordEncoder.class);
            setPassword(passwordEncoder.encode(studentNumber.substring(5)));
        }
    }

}