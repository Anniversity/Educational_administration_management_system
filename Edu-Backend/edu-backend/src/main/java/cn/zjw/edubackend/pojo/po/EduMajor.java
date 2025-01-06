package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class EduMajor extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "专业名不能为空")
    @Length(min = 2, max = 40, message = "专业名应在2 - 40个字符之间")
    private String name;

    @NotNull(groups = ValidateGroup.insert.class, message = "院系编号不能为空")
    private Integer deptId;

    @NotNull(groups = ValidateGroup.insert.class, message = "学制不能为空")
    private Integer schoolSystem;

    private String remark;

    private EduDept dept;
    private List<EduClass> classes;
}