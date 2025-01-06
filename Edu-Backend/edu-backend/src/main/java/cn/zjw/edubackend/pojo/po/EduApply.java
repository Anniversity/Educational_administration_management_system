package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EduApply extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "课程表编号不能为空")
    private Integer scheduleId;

    @NotNull(groups = ValidateGroup.insert.class, message = "新的课程节次编号不能为空")
    private Integer newSectionId;

//    @NotNull(groups = ValidateGroup.insert.class, message = "原课程节次编号不能为空")
    private Integer rawSectionId;

//    @NotNull(groups = ValidateGroup.insert.class, message = "原教室编号不能为空")
    private Integer rawClassroomId;

    @NotNull(groups = ValidateGroup.insert.class, message = "新的教室编号不能为空")
    private Integer newClassroomId;

    @NotNull(groups = ValidateGroup.insert.class, message = "理由不能为空")
    @Length(min = 2, max = 255, message = "调课理由长度应在2 - 255个字符之间")
    private String reason;

    private Integer status;

    private EduSchedule schedule;
    private EduClassroom rawClassroom;
    private EduClassroom newClassroom;
    private EduSection rawSection;
    private EduSection newSection;
}