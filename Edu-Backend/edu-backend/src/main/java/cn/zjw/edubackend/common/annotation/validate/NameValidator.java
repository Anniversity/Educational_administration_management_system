package cn.zjw.edubackend.common.annotation.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameValidation, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null)
            return true;
        String valueStr = (String) value;
        return valueStr.length() >= 2 && valueStr.length() <= 10;
    }
}