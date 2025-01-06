package cn.zjw.edubackend.common.enums;

public enum UserTypeEnum implements BaseEnum {
    TEACHER(1, "教师"), STUDENT(2, "学生"), ADMIN(3, "管理员");

    private Integer code;
    private String name;
    private TagTypeEnum type;

    UserTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TagTypeEnum getType() {
        return type;
    }

    public static UserTypeEnum getByCode(Integer code) {
        if (code == null) return null;
        if (code.equals(TEACHER.code)) return TEACHER;
        else if (code.equals(STUDENT.code)) return STUDENT;
        else if (code.equals(ADMIN.code)) return ADMIN;
        return null;
    }
}
