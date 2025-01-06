package cn.zjw.edubackend.common.annotation.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<MobileValidation, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null)
            return true;
        String valueStr = (String) value;
        return valueStr.matches("^(?:(?:\\+|00)86)?1[3-9]\\d{9}$");
    }
}