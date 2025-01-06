package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EduSelection extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "学生编号不能为空")
    private Integer studentId;

    @NotNull(groups = ValidateGroup.insert.class, message = "课程编号不能为空")
    private Integer courseId;

    @NotNull(groups = ValidateGroup.insert.class, message = "选修类型不能为空")
    private Integer studyType;

    private BigDecimal score;

    private BigDecimal credit;

    private EduStudent student;
    private EduCourse course;

    /** 查询条件 */
    private Integer teacherId;
}