package com.ishvatov.validation.driver;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation constraint, which checks, if
 * driver with such id does not exist.
 *
 * @author Sergey Khvatov
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotExistingDriverIdValidator.class})
@Documented
public @interface NotExistingDriverId {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
