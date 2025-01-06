package cn.zjw.edubackend.pojo.po.arrangecourse;

import cn.zjw.edubackend.pojo.po.EduClassroom;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Random;

/**
 * 染色体
 *
 * @author Gaven
 * @date 2023/12/01 1:33
 */
@Data
@AllArgsConstructor
public class GaSchedule {
    /**
     * GA中首要考虑的是如何表现其问题，即如何对染色体编码，使之适用于GA操作。
     * 在经典的遗传算法中，常采用浮点数或二进制的编码方法，
     * 目前，每条染色体代表每一节课的安排
     * 至于排课结果的优劣，则由适应度函数评估染色体的适应值来决定。
     */
    private String teachtaskid; //哪一个教学任务==》包含了courseid teacherid classid
//    private CourseTypeEnum type;

    private List<Integer> slots;
    private List<EduClassroom> classrooms;

    //下面三个字段是经过ga给的
    private EduClassroom classroom; //在哪一个教室
//    private Integer weekday; //周几上课（1-7）
    private Integer slot; //第几节课（12 34 56 78 910 11）==》（1-6）

    private boolean canEdit = true;


    public GaSchedule() {
    }

    public GaSchedule(String teachtaskid) {
        this.teachtaskid = teachtaskid;
    }

    void random_init() {
        if(!canEdit)
            return;
        //这里随机初始值的时候 还是使用一周五天 一天4节课的正常 课程安排条件
        this.classroom = randClassroom();
//        this.weekday = randWeekDay();
        this.slot = randSlot();
    }

    Integer randWeekDay() {
        return (int) (1 + Math.random() * (5 - 1 + 1));
    }

    EduClassroom randClassroom() {
        if(!canEdit)
            return this.classroom;
        return classrooms.get(new Random().nextInt(classrooms.size()));
    }

    Integer randSlot() {
        if(!canEdit)
            return this.slot;
        /*if(this.type.equals(CourseTypeEnum.ELECTIVE))
            return (int) (5 + Math.random() * 2);
        return (int) (1 + Math.random() * (4 - 1 + 1));*/
        return slots.get(new Random().nextInt(slots.size()));
    }
}
