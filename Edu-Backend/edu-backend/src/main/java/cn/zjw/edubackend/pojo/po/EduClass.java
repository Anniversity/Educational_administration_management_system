package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EduClass extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "班级名称不能为空")
    @Length(min = 2, max = 40, message = "班级名称应在2 - 40个字符之间")
    private String name;

    @NotNull(groups = ValidateGroup.insert.class, message = "教师编号不能为空")
    private Integer teacherId;

    @NotNull(groups = ValidateGroup.insert.class, message = "专业编号不能为空")
    private Integer majorId;

    @NotNull(groups = ValidateGroup.insert.class, message = "年级不能为空")
    private Integer grade;

    private EduMajor major;
    private EduTeacher teacher;

    /** 班级人数 - 通过计算得出 */
    private Integer number;
    /** 毕业年份 - 通过计算得出 */
    private Integer graduateYear;
}