package cn.zjw.edubackend.common.annotation.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { NameValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NameValidation {
    String message() default "姓名长度需在2 - 10个字符之间";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
