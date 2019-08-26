package com.ishvatov.model.dto;

import com.ishvatov.model.enums.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Basic user DTO implementation.
 *
 * @author Sergey Khvatov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto<String> {

    /**
     * UserEntity login userPassword.
     */
    private String password;

    /**
     * Role of the user.
     */
    private UserRoleType authority;
}
