package cn.zjw.edubackend.common.annotation.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { IdcardValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdcardValidation {
    String message() default "身份证号输入有误";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
