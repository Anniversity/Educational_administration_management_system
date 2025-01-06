package cn.zjw.edubackend.common.enums;

public enum ArrangeStatusEnum implements BaseEnum {
    ARRANGING(1, "排课中", TagTypeEnum.SUCCESS),
    SUCCESS(2, "成功", TagTypeEnum.DANGER),
    FAIL(3, "失败", TagTypeEnum.INFO);

    private Integer code;
    private String name;
    private TagTypeEnum type;

    ArrangeStatusEnum(Integer code, String name, TagTypeEnum type) {
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