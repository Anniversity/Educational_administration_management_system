package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EduClassroom extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "教室名称不能为空")
    @Length(min = 2, max = 30, message = "教室名称应在2 - 30个字符之间")
    private String name;

    @NotNull(groups = ValidateGroup.insert.class, message = "可容纳人数不能为空")
    private Integer number;

    @NotNull(groups = ValidateGroup.insert.class, message = "所属楼栋不能为空")
    private Integer buildId;

    @NotNull(groups = ValidateGroup.insert.class, message = "当前状态不能为空")
    private Integer status;

    private EduBuild build;
}