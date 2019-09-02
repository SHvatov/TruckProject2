package com.ishvatov.model.dto;

import com.ishvatov.model.enums.UserRoleType;
import com.ishvatov.validation.user.ExistingUser;
import com.ishvatov.validation.user.ExistingUserChecks;
import com.ishvatov.validation.user.NotExistingUserChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Basic user DTO implementation.
 *
 * @author Sergey Khvatov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExistingUser(groups = ExistingUserChecks.class)
public class UserDto implements AbstractDto {

    /**
     * Unique identificator of the object.
     */
    @NotBlank
    @Size(
        min = 5, max = 20,
        message = "{user.username.incorrect}",
        groups = NotExistingUserChecks.class
    )
    protected String id;

    /**
     * UserEntity login userPassword.
     */
    @NotBlank
    @Size(
        min = 5, max = 20,
        message = "{user.password.incorrect}",
        groups = NotExistingUserChecks.class
    )
    private String password;

    /**
     * Role of the user.
     */
    @NotBlank(
        message = "{user.role.incorrect}",
        groups = NotExistingUserChecks.class
    )
    private UserRoleType authority;
}
