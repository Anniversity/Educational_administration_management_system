package cn.zjw.edubackend.common.enums;

public enum CourseStatusEnum implements BaseEnum {
    NORMAL(1, "正常开设", TagTypeEnum.SUCCESS),
    DISABLED(2, "停止开设", TagTypeEnum.DANGER);

    private Integer code;
    private String name;
    private TagTypeEnum type;

    CourseStatusEnum(Integer code, String name, TagTypeEnum type) {
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
}