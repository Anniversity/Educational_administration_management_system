package cn.zjw.edubackend.pojo.bbs;

import lombok.Data;
import java.util.Date;

@Data
public class Post {
    private Long id;
    private String title;
    private String content;
    private Long userId; // 教师或学生的ID
    private Date createTime;
    private Date updateTime;
}
