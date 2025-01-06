package cn.zjw.edubackend.util;

import cn.zjw.edubackend.common.enums.BaseEnum;
import cn.zjw.edubackend.common.enums.TagTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnumUtil {
    @Data
    @AllArgsConstructor
    public static class Dict implements Serializable {
        private String label;
        private Integer value;
        private String type;
    }

    /**
     * 将枚举类转为list
     * @param values
     */
    public static <T extends BaseEnum> List<Dict> parseEnumToDictList(T[] values) {
        ArrayList<Dict> list = new ArrayList<>();
        for (T value : values) {
            TagTypeEnum type = value.getType();
            Dict dict = new Dict(value.getName(), value.getCode(), type == null ? null : type.name().toLowerCase());
            list.add(dict);
        }
        return list;
    }
}
