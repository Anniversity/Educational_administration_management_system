package cn.zjw.edubackend.pojo.po;

import cn.zjw.edubackend.common.ValidateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EduConfig extends BaseEntity implements Serializable {
    @NotBlank(groups = ValidateGroup.insert.class, message = "配置项键名不能为空")
    private String key;

    @NotBlank(groups = ValidateGroup.insert.class, message = "配置项键值不能为空")
    private String value;

    @NotBlank(groups = ValidateGroup.insert.class, message = "配置项键说明不能为空")
    private String keyName;

    private String remark;
}