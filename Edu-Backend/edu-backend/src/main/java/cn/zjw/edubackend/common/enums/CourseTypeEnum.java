package cn.zjw.edubackend.common.enums;

public enum CourseTypeEnum implements BaseEnum {
    REQUIRED(1, "必修", TagTypeEnum.SUCCESS),
    ELECTIVE(2, "选修", TagTypeEnum.WARNING);

    private Integer code;
    private String name;
    private TagTypeEnum type;

    CourseTypeEnum(Integer code, String name, TagTypeEnum type) {
        this.code = code;
        this.name = name;
        this.type = type;
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

    public static CourseTypeEnum getByCode(Integer code) {
        if (code == null) return null;
        if (code.equals(REQUIRED.code)) return REQUIRED;
        else if (code.equals(ELECTIVE.code)) return ELECTIVE;
        return null;
    }
}