package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EduSchedule extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "班号不能为空")
    private Integer classNum;

    @NotNull(groups = ValidateGroup.insert.class, message = "课程编号不能为空")
    private Integer courseId;

    @NotNull(groups = ValidateGroup.insert.class, message = "教室编号不能为空")
    private Integer classroomId;

    @NotNull(groups = ValidateGroup.insert.class, message = "节次编号不能为空")
    private Integer sectionId;

    private EduCourse course;
    private EduClassroom classroom;
    private EduSection section;

    /** 查询条件开始  */
    private Integer classId;
    private Integer studentId;
    private Integer teacherId;
    @NotNull(groups = ValidateGroup.query.class, message = "学年不能为空")
    private Integer year;
    @NotNull(groups = ValidateGroup.query.class, message = "学期不能为空")
    private Integer semester;
}