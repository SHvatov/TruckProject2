package com.ishvatov.validation.driver;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation constraint, which checks, if
 * order has been assigned to this driver.
 *
 * @author Sergey Khvatov
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NoDriverOrderAssignedValidator.class})
@Documented
public @interface NoDriverOrderAssigned {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
