package cn.zjw.edubackend.common.annotation.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameValidation, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null)
            return true;
        return ((String) value).matches("^[a-zA-Z0-9_-]{5,15}$");
    }
}