package com.ishvatov.validation.truck;

import com.ishvatov.service.inner.truck.TruckInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link ConstraintValidator} class for
 * the {@link NotExistingTruckId} annotation constraint.
 *
 * @author Sergey Khvatov
 */
@Component
public class NotExistingTruckIdValidator implements ConstraintValidator<NotExistingTruckId, String> {

    /**
     * {@link TruckInnerService} autowired instance.
     */
    private TruckInnerService truckInnerService;

    /**
     * Default class constructor.
     *
     * @param truckInnerService {@link TruckInnerService} autowired instance.
     */
    @Autowired
    public NotExistingTruckIdValidator(TruckInnerService truckInnerService) {
        this.truckInnerService = truckInnerService;
    }

    /**
     * Checks if truck with such id does not exist.
     *
     * @param id                         id to check.
     * @param constraintValidatorContext {@link ConstraintValidatorContext} instance.
     * @return true, if does not exist, false otherwise.
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return !truckInnerService.exists(id);
    }
}
