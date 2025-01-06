package cn.zjw.edubackend.common.enums;

public enum ClassroomStatusEnum implements BaseEnum {
    INUSE(1, "使用中", TagTypeEnum.SUCCESS),
    INMAINTENANCE(2, "维修中", TagTypeEnum.DANGER);

    private Integer code;
    private String name;
    private TagTypeEnum type;

    ClassroomStatusEnum(Integer code, String name, TagTypeEnum type) {
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
