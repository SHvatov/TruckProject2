package com.ishvatov.validation.driver;

import com.ishvatov.service.inner.driver.DriverInnerService;
import com.ishvatov.service.inner.truck.TruckInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Implementation of the {@link ConstraintValidator} class for
 * the {@link NotExistingDriverId} annotation constraint.
 *
 * @author Sergey Khvatov
 */
@Component
public class NotExistingDriverIdValidator implements ConstraintValidator<NotExistingDriverId, String> {

    /**
     * {@link DriverInnerService} autowired instance.
     */
    private DriverInnerService driverInnerService;

    /**
     * Default class constructor.
     *
     * @param driverInnerService {@link DriverInnerService} autowired instance.
     */
    @Autowired
    public NotExistingDriverIdValidator(DriverInnerService driverInnerService) {
        this.driverInnerService = driverInnerService;
    }

    /**
     * Checks if driver with such id does not exist.
     *
     * @param id                         id to check.
     * @param constraintValidatorContext {@link ConstraintValidatorContext} instance.
     * @return true, if does not exist, false otherwise.
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return !driverInnerService.exists(id);
    }
}

