package com.ishvatov.validation.cargo;

import com.ishvatov.service.inner.cargo.CargoInnerService;
import com.ishvatov.service.inner.truck.TruckInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link ConstraintValidator} class for
 * the {@link ExistingCargoId} annotation constraint.
 *
 * @author Sergey Khvatov
 */
@Component
public class ExistingCargoIdValidator implements ConstraintValidator<ExistingCargoId, Integer> {

    /**
     * {@link TruckInnerService} autowired instance.
     */
    private CargoInnerService cargoInnerService;

    /**
     * Default class constructor.
     *
     * @param cargoInnerService {@link CargoInnerService} autowired instance.
     */
    @Autowired
    public ExistingCargoIdValidator(CargoInnerService cargoInnerService) {
        this.cargoInnerService = cargoInnerService;
    }

    /**
     * Checks if cargo with such id does not exist.
     *
     * @param id                         id to check.
     * @param constraintValidatorContext {@link ConstraintValidatorContext} instance.
     * @return true, if does not exist, false otherwise.
     */
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        return cargoInnerService.exists(id);
    }
}
