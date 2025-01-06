package cn.zjw.edubackend.common.annotation.validate;

import cn.hutool.core.util.IdcardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdcardValidator implements ConstraintValidator<IdcardValidation, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null)
            return true;
        return IdcardUtil.isValidCard((String) value);
    }
}