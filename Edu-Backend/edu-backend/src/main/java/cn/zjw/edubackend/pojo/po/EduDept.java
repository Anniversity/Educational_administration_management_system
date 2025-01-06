package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class EduDept extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "院系名不能为空")
    @Length(min = 2, max = 30, message = "院系名应在2 - 30个字符之间")
    private String name;

    private List<EduMajor> majors;
    private List<EduTeacher> teachers;
    private List<EduCourse> courses;
}