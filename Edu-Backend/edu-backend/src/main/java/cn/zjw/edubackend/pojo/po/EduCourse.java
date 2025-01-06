package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EduCourse extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "课程名称不能为空")
    @Length(min = 2, max = 30, message = "课程名应在2 - 30个字符之间")
    private String name;

    @NotNull(groups = ValidateGroup.insert.class, message = "学时不能为空")
    private Integer hours;

    @NotNull(groups = ValidateGroup.insert.class, message = "课程类型不能为空")
    private Integer type;

//    @NotNull(groups = ValidateGroup.insert.class, message = "总人数不能为空")
    private Integer sumNumber;

//    @NotNull(groups = ValidateGroup.insert.class, message = "开设班数不能为空")
    private Integer sumClass;

    @NotNull(groups = ValidateGroup.insert.class, message = "所属院系不能为空")
    private Integer deptId;

//    @NotNull(groups = ValidateGroup.insert.class, message = "所属班级不能为空")
    private Integer classId;

    @NotNull(groups = ValidateGroup.insert.class, message = "教师编号不能为空")
    private Integer teacherId;

    @NotNull(groups = ValidateGroup.insert.class, message = "课程学分不能为空")
    private BigDecimal credit;

    @NotNull(groups = ValidateGroup.insert.class, message = "所在学年不能为空")
    private Integer year;

    @NotNull(groups = ValidateGroup.insert.class, message = "所在学期不能为空")
    private Integer semester;

    @NotNull(groups = ValidateGroup.insert.class, message = "课程状态不能为空")
    private Integer status;

    private EduDept dept;
    private EduTeacher teacher;
    private EduClass eduClass;
}