package com.ishvatov.validation.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link ConstraintValidator} class for
 * the {@link DoubleInRange} annotation constraint.
 *
 * @author Sergey Khvatov
 */
public class DoubleInRangeValidator implements ConstraintValidator<DoubleInRange, Double> {

    /**
     * Minimum value.
     */
    private double min;

    /**
     * Maximum value.
     */
    private double max;

    /**
     * Initialize method override.
     *
     * @param constraintAnnotation {@link DoubleInRange} instance.
     */
    @Override
    public void initialize(DoubleInRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    /**
     * Validates given value.
     *
     * @param value                      double value to check.
     * @param constraintValidatorContext {@link ConstraintValidatorContext} instance.
     * @return true, if double is in range, false otherwise.
     */
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        return Double.compare(min, value) <= 0 && Double.compare(value, max) <= 0;
    }
}
