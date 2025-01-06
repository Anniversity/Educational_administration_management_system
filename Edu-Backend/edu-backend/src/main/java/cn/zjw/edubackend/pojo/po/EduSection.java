package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EduSection extends BaseEntity implements Serializable {
    @NotNull(groups = ValidateGroup.insert.class, message = "星期几不能为空")
    private Integer day;

    @NotNull(groups = ValidateGroup.insert.class, message = "学时数不能为空")
    private Integer hours;

    @NotNull(groups = ValidateGroup.insert.class, message = "开始节次不能为空")
    private Integer startNum;

    @NotNull(groups = ValidateGroup.insert.class, message = "结束节次不能为空")
    private Integer endNum;
}