package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class EduBuild extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "楼栋名称不能为空")
    @Length(min = 2, max = 30, message = "楼栋名称应在2 - 30个字符之间")
    private String name;

    private List<EduClassroom> classrooms;
}