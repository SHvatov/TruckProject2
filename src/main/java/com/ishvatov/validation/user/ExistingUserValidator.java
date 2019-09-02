package com.ishvatov.validation.user;

import com.ishvatov.model.dto.UserDto;
import com.ishvatov.service.inner.user.UserInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link ConstraintValidator} class for
 * the {@link ExistingUser} annotation constraint.
 *
 * @author Sergey Khvatov
 */
@Component
public class ExistingUserValidator implements ConstraintValidator<ExistingUser, UserDto> {

    /**
     * Autowired inner service instance.
     */
    private UserInnerService userInnerService;

    /**
     * Default class constructor used for fields autowiring.
     *
     * @param userInnerService {@link UserInnerService} instance.
     */
    @Autowired
    public ExistingUserValidator(UserInnerService userInnerService) {
        this.userInnerService = userInnerService;
    }

    /**
     * Validates the {@link UserDto} object.
     *
     * @param userDto                    dto object to be validated.
     * @param constraintValidatorContext {@link ConstraintValidatorContext} instance.
     * @return true, if user with such credentials exists, false otherwise.
     * @throws IllegalArgumentException if required dto object fields are null.
     */
    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        if (userInnerService.isValidUserCredentials(userDto.getId(), userDto.getPassword())) {
            return true;
        } else if (userInnerService.exists(userDto.getId())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{user.password.incorrect}")
                .addPropertyNode("password").addConstraintViolation();
            return false;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{user.username.not_exists}")
                .addPropertyNode("id").addConstraintViolation();
            return false;
        }
    }
}
