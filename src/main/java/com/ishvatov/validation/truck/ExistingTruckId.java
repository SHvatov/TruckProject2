package com.ishvatov.validation.truck;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation constraint, which checks, if
 * truck with such id does exist.
 *
 * @author Sergey Khvatov
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ExistingTruckIdValidator.class})
@Documented
public @interface ExistingTruckId {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
