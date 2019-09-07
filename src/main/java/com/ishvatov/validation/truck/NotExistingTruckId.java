package com.ishvatov.validation.truck;

import com.ishvatov.validation.common.DoubleInRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation constraint, which checks, if
 * truck with such id does not exist.
 *
 * @author Sergey Khvatov
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotExistingTruckIdValidator.class})
@Documented
public @interface NotExistingTruckId {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
