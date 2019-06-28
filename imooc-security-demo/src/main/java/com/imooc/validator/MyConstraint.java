package com.imooc.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =MyConstraintValidator.class )
public @interface MyConstraint {
    /**
     * 定义注解必须要定义这三个属性
     * @return
     */
    String message() ;//错误信息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
