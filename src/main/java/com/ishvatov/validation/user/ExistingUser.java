package com.ishvatov.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation constraint, which checks, if user
 * with such credentials exists in the data base.
 *
 * @author Sergey Khvatov
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ExistingUserValidator.class})
@Documented
public @interface ExistingUser {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
