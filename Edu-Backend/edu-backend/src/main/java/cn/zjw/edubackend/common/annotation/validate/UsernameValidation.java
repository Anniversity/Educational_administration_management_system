package cn.zjw.edubackend.common.annotation.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { UsernameValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameValidation {
    String message() default "用户名长度应在5 - 15位之间且只能由字母、数字、下划线和减号组成";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
