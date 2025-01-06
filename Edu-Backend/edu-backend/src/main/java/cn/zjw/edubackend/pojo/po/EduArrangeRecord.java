package cn.zjw.edubackend.pojo.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class EduArrangeRecord extends BaseEntity implements Serializable {
    private Integer year;

    private Integer semester;

    private Integer status;

    private String remark;

    private String data;
}