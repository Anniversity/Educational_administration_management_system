package cn.zjw.edubackend.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "原密码不能为空")
    private String rawPwd;
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}
