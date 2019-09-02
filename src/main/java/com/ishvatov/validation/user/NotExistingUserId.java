package com.ishvatov.validation.user;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation constraint, which checks, if
 * passed credentials are valid.
 *
 * @author Sergey Khvatov
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ExistingUserValidator.class})
@Documented
public @interface NotExistingUserId {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}