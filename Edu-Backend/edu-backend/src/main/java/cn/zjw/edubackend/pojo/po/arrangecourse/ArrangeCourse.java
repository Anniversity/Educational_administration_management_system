package cn.zjw.edubackend.pojo.po.arrangecourse;

import lombok.Data;

import java.util.List;

@Data
public class ArrangeCourse {
    List<Integer> courses;
    List<Integer> classrooms;
    List<Integer> sections;
    String sectionName;
    String classroomName;
    String courseName;
}
