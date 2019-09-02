package com.ishvatov.validation.user;

import com.ishvatov.service.inner.user.UserInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of the {@link ConstraintValidator} class for
 * the {@link NotExistingUserId} annotation constraint.
 *
 * @author Sergey Khvatov
 */
@Component
public class NotExistingUserIdValidator implements ConstraintValidator<NotExistingUserId, String> {

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
    public NotExistingUserIdValidator(UserInnerService userInnerService) {
        this.userInnerService = userInnerService;
    }

    /**
     * Validates the user's id.
     *
     * @param id                         id of the user.
     * @param constraintValidatorContext {@link ConstraintValidatorContext} instance.
     * @return true, if user with such id already exists, false otherwise.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        if (userInnerService.exists(id)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{user.username.exists}")
                .addPropertyNode("id").addConstraintViolation();
            return false;
        }
        return true;
    }
}
