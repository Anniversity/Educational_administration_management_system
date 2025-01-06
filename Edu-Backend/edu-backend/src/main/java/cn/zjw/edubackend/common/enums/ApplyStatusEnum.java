package cn.zjw.edubackend.common.enums;

public enum ApplyStatusEnum implements BaseEnum {
    AGREE(1, "同意", TagTypeEnum.SUCCESS),
    REFUSE(2, "拒绝", TagTypeEnum.DANGER),
    APPLYING(3, "申请中", TagTypeEnum.INFO);

    private Integer code;
    private String name;
    private TagTypeEnum type;

    ApplyStatusEnum(Integer code, String name, TagTypeEnum type) {
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